package com.mfokumus.dto;

import java.io.Serializable;
import java.util.Date;

public class VkiDto extends BaseDto implements Serializable {

    // SERILESTIRME
    public static final Long serialVersionUID = 1L;

    private Double boy;
    private Double kilo;
    private Double vucutKitleIndex;
    private Long userId;


    // Constructor (parametresiz)
    public VkiDto() {
    }

    // Constructor (parametreli)
    public VkiDto(Long id, Date systemCreatedDate, Double boy, Double kilo, Double vucutKitleIndex, Long userId) {
        super(id, systemCreatedDate);
        this.boy = boy;
        this.kilo = kilo;
        this.vucutKitleIndex = vucutKitleIndex;
        this.userId = userId;
    }

    // TO STRING
    @Override
    public String toString() {
        return "VkiDto{" +
                "boy=" + boy +
                ", kilo=" + kilo +
                ", vucut_kitle_index=" + vucutKitleIndex +
                ", user_id=" + userId +
                "} " + super.toString();
    }

    @Override
    public String nowDateAbstract() {
        return null;
    }

    // GETTER AND SETTER
    public Double getBoy() {
        return boy;
    }

    public void setBoy(Double boy) {
        this.boy = boy;
    }

    public Double getKilo() {
        return kilo;
    }

    public void setKilo(Double kilo) {
        this.kilo = kilo;
    }

    public Double getVucutKitleIndex() {
        return vucutKitleIndex;
    }

    public void setVucutKitleIndex(Double vucutKitleIndex) {
        this.vucutKitleIndex = vucutKitleIndex;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
