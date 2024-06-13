package com.unip.aps_3_sem_lpoo.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    public Connection getConnection() throws SQLException {
       return DriverManager.getConnection("jdbc:mysql://localhost:3306/casos_hemofilia_sp", "root", "admin");

    }
}
