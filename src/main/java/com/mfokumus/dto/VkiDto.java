package com.mfokumus.dto;

import java.io.Serializable;
import java.util.Date;

public class VkiDto extends BaseDto implements Serializable {

    public static final Long serialVersionUID=1L;

    private Float boy;
    private Float kilo;
    private Float vucut_kitle_index;
    private Long user_id;

    // Constructor (parametresiz)
    public VkiDto() {
    }

    // Constructor (parametreli)
    public VkiDto(Float boy, Float kilo, Float vucut_kitle_index, Long user_id) {
        this.boy = boy;
        this.kilo = kilo;
        this.vucut_kitle_index = vucut_kitle_index;
        this.user_id = user_id;
    }

    // toString
    @Override
    public String toString() {
        return "VkiDto{" +
                "boy=" + boy +
                ", kilo=" + kilo +
                ", vucut_kitle_index=" + vucut_kitle_index +
                ", user_id=" + user_id +
                "} " + super.toString();
    }

    public Float getBoy() {
        return boy;
    }

    public void setBoy(Float boy) {
        this.boy = boy;
    }

    public Float getKilo() {
        return kilo;
    }

    public void setKilo(Float kilo) {
        this.kilo = kilo;
    }

    public Float getVucut_kitle_index() {
        return vucut_kitle_index;
    }

    public void setVucut_kitle_index(Float vucut_kitle_index) {
        this.vucut_kitle_index = vucut_kitle_index;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    @Override
    public String nowDateAbstract() {
        return null;
    }
}
