package com.mfokumus.roles;

public enum ERoles {
    ADMIN(1L,"admin"), WRITER(2L,"writer"), USER(3L,"user");  // (key, "value")

    //variable
    private final Long key;
    private final String value;

    //Constructor (Parametreli constructor)
    // Constructor a private verirsek;
    //Bu Enum'ın instance(new) oluşturulmasına izin vermiyor.
    private ERoles(Long key, String value) {
        this.key = key;
        this.value = value;
    }

    public Long getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}// end ERoles
