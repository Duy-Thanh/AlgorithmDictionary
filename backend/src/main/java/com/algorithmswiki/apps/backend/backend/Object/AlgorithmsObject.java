package com.algorithmswiki.apps.backend.backend.Object;

public class AlgorithmsObject {
    private Long Id;
    private String title;
    private String algorithm_definition;
    private String algorithm_howtowork;
    private String algorithm_application;
    private String algorithm_example;
    private String date_added;
    private Long user_id;

    public AlgorithmsObject() { }

    public AlgorithmsObject(Long Id, String title,
                            String algorithm_definition, String algorithm_howtowork,
                            String algorithm_application, String algorithm_example, 
                            String date_added, Long user_id) {
        this.Id = Id;
        this.title = title;
        this.algorithm_definition = algorithm_definition;
        this.algorithm_application = algorithm_application;
        this.algorithm_howtowork = algorithm_howtowork;
        this.algorithm_example = algorithm_example;
        this.date_added = date_added;
        this.user_id = user_id;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        this.Id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlgorithmDefinition() {
        return algorithm_definition;
    }

    public void setAlgroithmDefinition(String algorithm_definition) {
        this.algorithm_definition = algorithm_definition;
    }

    public String getAlgorithmHowToWork() {
        return algorithm_howtowork;
    }

    public void setAlgorithmHowToWork(String algorithm_howtowork) {
        this.algorithm_howtowork = algorithm_howtowork;
    }

    public String getAlgorithmApplication() {
        return algorithm_application;
    }

    public void setAlgorithmApplication(String algorithm_application) {
        this.algorithm_application = algorithm_application;
    }

    public String getAlgorithmExample() {
        return algorithm_example;
    }

    public void setAlgorithmExample(String algorithm_example) {
        this.algorithm_example = algorithm_example;
    }
}

