package com.mfokumus.database;

// Database Information
abstract public class DatabaseInformation {

    // Variable
    protected String url;
    protected String user;
    protected String password;
    protected String forNameData;

    // Constructor (Parametresiz)
    public DatabaseInformation() {
        // Default Mysql
        user = "root";
        password = "root";
        url = "jdbc:mysql://localhost:3306/cars";
        forNameData = "com.mysql.cj.jdbc.Driver"; //my sql driver kısmı
    }

    // Constructor (Parametreli)
    public DatabaseInformation(String url, String user, String password, String forNameData) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.forNameData = forNameData;
    }

    //GETTER AND SETTER
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getForNameData() {
        return forNameData;
    }

    public void setForNameData(String forNameData) {
        this.forNameData = forNameData;
    }
}// end class DatabaseInformation
