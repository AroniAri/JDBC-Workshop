package se.lexicon.JDBC;

import java.sql.*;

public class App {
    public static void main(String[] args) {
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/world","root", "Aroni123");
            PreparedStatement preparedStatement = null;
            ResultSet resultSet= null;
        }catch(SQLException e){
            System.out.println("SQL Exeption: ");
            e.printStackTrace();

        }
    }
}
