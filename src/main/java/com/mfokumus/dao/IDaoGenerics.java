package com.mfokumus.dao;


import com.mfokumus.database.DatabaseConnection;
import com.mfokumus.dto.RegisterDto;

import java.sql.Connection;
import java.util.ArrayList;

//INTERFACE
public interface IDaoGenerics <T> {

    public String speedData(Long id);
    public String allDelete();

    //////////////////////////////////////////
    //Counter
    /////////////////////////////////////////

    // C R U D
    // CREATE
    public T create(T t);  // kullanıcıdan alınınan nesne create dönüyor

    // FIND BY ID
    public T findById(Long id); // kullanıcıdan alınan id burda kullanıcam
    public T findByEmail(String email);

    //LIST
    public ArrayList<T> list(); // kullanıcıdan herhangi bir parametre almamıza gerek yok

    // UPDATE
    public T update(Long id, T t); // update için kullanıcıdan bir id ve objenin kendisini alalım.

    // UPDATE (REMAING NUMBER)
    public T updateRemaing(Long id, T t);

    // DELETE
    public T deleteById(T t); //

    // *) Buradaki amaç bütün classlarda bu CRUD yapısını implement ettiğim zaman çalışsın!!
    // *) Interface yapıları görüldüğü gibi gövdesiz metotlardır!!
    ////////////////////////////////////////////////////////////////////////
    // interface bir class'ta gövdeli metot kullanmak için başına default getirilmelidir!
    default Connection getInterfaceConnection(){
        return DatabaseConnection.getInstance().getConnection();
    }// end body interface
} // end intarface IDaoGenerics
