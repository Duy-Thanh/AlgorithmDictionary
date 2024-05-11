package com.algorithmswiki.apps.backend.backend.Object;

public class JWTTokenObject {
    private String sub;
    private Long exp;
    private Long iat;

    public JWTTokenObject() { }

    public JWTTokenObject(String sub, Long exp, Long iat) {
        this.sub = sub;
        this.exp = exp;
        this.iat = iat;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public Long getExp() {
        return exp;
    }

    public void setExp(Long exp) {
        this.exp = exp;
    }

    public Long getIat() {
        return iat;
    }

    public void setIat(Long iat) {
        this.iat = iat;
    }
}
