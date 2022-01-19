package com.ydahar.sbk.domain;

public enum Dance {
    SAC("Salsa cubaine"),
    BAT("Bachata"),
    KIZ("Kizomba"),
    SAP("Salsa porto / On1 / On2"),
    ROC("Rock / WCS");

    String value;

    Dance(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
