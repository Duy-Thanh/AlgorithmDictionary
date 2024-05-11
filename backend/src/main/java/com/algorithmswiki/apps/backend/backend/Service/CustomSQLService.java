package com.algorithmswiki.apps.backend.backend.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.algorithmswiki.apps.backend.backend.JSONHelper;
import com.algorithmswiki.apps.backend.backend.Object.AlgorithmsObject;
import com.algorithmswiki.apps.backend.backend.Object.ErrorObject;

@Service
public class CustomSQLService {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CustomSQLService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean validateUser(String username, String password) {
        String sql = "SELECT COUNT(*) FROM UserAccount WHERE username = ? AND userpass = ?";
        try {
            int count = jdbcTemplate.queryForObject(sql, new Object[]{username, password}, Integer.class);
            return count > 0;
        } catch (IncorrectResultSizeDataAccessException e) {
            return false;
        }
    }

    public boolean checkUserIfExisted(String email, String username) {
        String sql = "SELECT COUNT(*) FROM UserAccount WHERE username = ? OR email = ?";
        try {
            int count = jdbcTemplate.queryForObject(sql, new Object[]{username, email}, Integer.class);
            return count > 0;
        } catch (IncorrectResultSizeDataAccessException e) {
            return true;
        }
    }

    public boolean registerUser(String username, String password, 
                                String fullName, String email, 
                                String permission) {
        try {
            if (!checkUserIfExisted(email, username)) {
                String reg_sql = "INSERT INTO UserAccount(username, userpass, fullName, email, permission, isDeletedOrDeactivated) VALUES (?, ?, ?, ?, ?, ?)";

                int rowAffected = jdbcTemplate.update(reg_sql, username, password, fullName, email, permission, "false");
        
                return rowAffected > 0;
            } else {
                // User is duplicated
                return false;
            }
        } catch (IncorrectResultSizeDataAccessException e) {
            return false;
        }
    }

    public Integer getUserIdByUsername(String username) {
        String query = "SELECT ID FROM UserAccount WHERE username = ?";
        try {
            Integer userId = jdbcTemplate.queryForObject(query, new Object[]{username}, Integer.class);
        
            return userId;    
        } catch (IncorrectResultSizeDataAccessException e) {
            return 0;
        }
    }

    public String getFullNameByUsername(String username) {
        try {
            String query = "SELECT fullName FROM UserAccount WHERE username = ?";
            String fullName = jdbcTemplate.queryForObject(query, new Object[]{username}, String.class);

            return fullName;
        } catch (IncorrectResultSizeDataAccessException e) {
            return "";
        }
    }

    public String getEmailByUsername(String username) {
        try {
            String query = "SELECT email FROM UserAccount WHERE username = ?";
            String email = jdbcTemplate.queryForObject(query, new Object[]{username}, String.class);

            return email;
        } catch (IncorrectResultSizeDataAccessException e) {
            return "";
        }
    }

    public String getPermissionByUsername(String username) {
        try {
            String query = "SELECT permission FROM UserAccount WHERE username = ?";
            String permission = jdbcTemplate.queryForObject(query, new Object[]{username}, String.class);

            return permission;
        } catch (IncorrectResultSizeDataAccessException e) {
            return "";
        }
    }

    public String getDeletedOrDeactivatedStateByUsername(String username) {
        try {
            String query = "SELECT isDeletedOrDeactivated FROM UserAccount WHERE username = ?";
            String status = jdbcTemplate.queryForObject(query, new Object[]{username}, String.class);

            return status;
        } catch (IncorrectResultSizeDataAccessException e) {
            return "";
        }
    }

    public List<AlgorithmsObject> getListAlgorithms() {
        String query = "SELECT id, title, algorithm_definition, algorithm_howtowork, algorithm_application, algorithm_example FROM Algorithms";
        
        RowMapper<AlgorithmsObject> rows = new RowMapper<AlgorithmsObject>() {
            @Override
            public AlgorithmsObject mapRow(ResultSet rs, int rowNum) throws SQLException {
                AlgorithmsObject algorithm = new AlgorithmsObject();
                algorithm.setId(rs.getLong("id"));
                algorithm.setTitle(rs.getString("title"));
                algorithm.setAlgorithmApplication(rs.getString("algorithm_application"));
                algorithm.setAlgroithmDefinition(rs.getString("algorithm_definition"));
                algorithm.setAlgorithmHowToWork(rs.getString("algorithm_howtowork"));
                algorithm.setAlgorithmExample(rs.getString("algorithm_example"));
                return algorithm;
            }
        };

        return jdbcTemplate.query(query, rows);
    }

    public List<AlgorithmsObject> getListAlgorithmsBySearchTerm(String searchTerm) {
        String query = "SELECT id, title, algorithm_definition, algorithm_howtowork, algorithm_application, algorithm_example FROM Algorithms WHERE LOWER(title) LIKE LOWER(?)";
        
        RowMapper<AlgorithmsObject> rows = new RowMapper<AlgorithmsObject>() {
            @Override
            public AlgorithmsObject mapRow(ResultSet rs, int rowNum) throws SQLException {
                AlgorithmsObject algorithm = new AlgorithmsObject();
                algorithm.setId(rs.getLong("id"));
                algorithm.setTitle(rs.getString("title"));
                algorithm.setAlgorithmApplication(rs.getString("algorithm_application"));
                algorithm.setAlgroithmDefinition(rs.getString("algorithm_definition"));
                algorithm.setAlgorithmHowToWork(rs.getString("algorithm_howtowork"));
                algorithm.setAlgorithmExample(rs.getString("algorithm_example"));
                return algorithm;
            }
        };

        return jdbcTemplate.query(query, new Object[]{"%" + searchTerm + "%"}, rows);
    }
    
    public String getAlgorithmsSearchResultAsJSON(String searchTerm) throws Exception {
        List<AlgorithmsObject> algorithms = getListAlgorithmsBySearchTerm(searchTerm);

        try {
            return JSONHelper.toJSON(algorithms).toString();
        } catch (Exception e) {
            e.printStackTrace();
            ErrorObject error = new ErrorObject(500, "Failed to get algorithms list");

            return JSONHelper.toJSON(error).toString();
        }
    }

    public String getAlgorithmsAsJSON() throws Exception {
        List<AlgorithmsObject> algorithms = getListAlgorithms();

        try {
            return JSONHelper.toJSON(algorithms).toString();
        } catch (Exception e) {
            e.printStackTrace();
            ErrorObject error = new ErrorObject(500, "Failed to get algorithms list");

            return JSONHelper.toJSON(error).toString();
        }
    }

    public boolean updateUser(Long id, String username, String password, String fullname, String email) {
        try {
            String query = "UPDATE UserAccount SET username = ?, userpass = ?, fullName = ?, email = ? WHERE ID = ?";

            int rowAffected = jdbcTemplate.update(query, username, password, fullname, email, id);

            return rowAffected > 0;
        } catch (Exception e) {
            return false;
        }
    }
}
