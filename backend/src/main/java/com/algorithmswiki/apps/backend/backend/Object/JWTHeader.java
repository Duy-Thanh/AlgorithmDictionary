package com.algorithmswiki.apps.backend.backend.Object;

public class JWTHeader {
    private String typ;
    private String alg;

    public JWTHeader() { }

    public JWTHeader(String typ, String alg) {
        this.typ = typ;
        this.alg = alg;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public String getAlg() {
        return alg;
    }

    public void setAlg(String alg) {
        this.alg = alg;
    }
}
