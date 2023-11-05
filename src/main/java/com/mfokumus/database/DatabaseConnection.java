package com.mfokumus.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection extends DatabaseInformation{

    //Variable
    private String url = super.url;
    private String user = super.user;
    private String password = super.password;
    private String forNameData = super.forNameData;

    //JDBC Connection
    private Connection connection; //java.sql.Connection

    // Singleton Design Pattern (Variable) 1.adım
    private static DatabaseConnection instance; //Singleton Design Pattern da classımızın ilk ismi

    // Singleton Design Pattern (Constructor) private çünkü instance yapısı oluşmasın diye
    private DatabaseConnection(){
        try {
            Class.forName(this.forNameData);
            System.out.println("Driver basariyla yuklendi.");
            connection = DriverManager.getConnection(url,user,password);
            System.out.println("Database baglantisi basarili.");
        } catch (Exception exception){
            exception.printStackTrace();
        }
    } // end constructor

    // Singleton Design Pattern (Method)
    public static DatabaseConnection getInstance(){
        try {
            //Eğer connection kapalı veya null ise oluştur
            //Eğer bağlantı varsa o bağlantıyı kullan
            if(instance==null || instance.connection.isClosed()){
                instance = new DatabaseConnection();
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return instance;
    }// end instance

    //GETTER AND SETTER
    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}// end Class DatabaseConnection
