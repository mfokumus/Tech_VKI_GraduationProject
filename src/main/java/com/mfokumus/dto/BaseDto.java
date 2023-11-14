package com.mfokumus.dto;

import java.io.Serializable;
import java.util.Date;

//BaseDto
abstract public class BaseDto implements Serializable {

    //Serileştirme
    public static final Long serialVersionUID=1L; //long ise büyük L yazılır. Bu yapıya cast denir

    //Variable
    private Long id;
    private Date systemCreatedDate;

    //constructor (Parametresiz)
    public BaseDto() {
        //System.out.println("HashCode " + BaseDto.class.hashCode());
        this.systemCreatedDate = new Date(System.currentTimeMillis()); //1 ocak 1970 yılından bu yana geçen milisaniye
    }

    // Constructor (Parametreli)
    public BaseDto(Long id, Date systemCreatedDate) {
        this.id = id;
        this.systemCreatedDate = systemCreatedDate;
    }

    //toString
    @Override
    public String toString() {  //bu class ın class ile ilgili bilgilerini içeren kimlik diyebiliriz
        return "BaseDto{" +
                "id=" + id +
                ", systemCreatedDate=" + systemCreatedDate +
                '}';
    }

    //Metodlar
    public String nowDate(){
        return String.valueOf(this.systemCreatedDate);
    }

    //Gövdesiz metod
    abstract public String nowDateAbstract();

    //Getter and Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getSystemCreatedDate() {
        return systemCreatedDate;
    }

    public void setSystemCreatedDate(Date systemCreatedDate) {
        this.systemCreatedDate = systemCreatedDate;
    }
} //end class BaseDto

