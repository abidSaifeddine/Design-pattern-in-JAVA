package Sigleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbSingleton {
    private static volatile DbSingleton instance = null;
    private static volatile Connection conn = null;
    private DbSingleton(){
        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(conn!=null){
            throw new RuntimeException("use getConnection() method ");
        }
        if(instance!=null){
            throw new RuntimeException("use get Instance() method ");
        }
    }

    public static DbSingleton getInstance(){

        if(instance==null)
        {
            synchronized (DbSingleton.class)
            {
                if(instance==null){
                    instance = new DbSingleton();
                }
            }
        }

        return instance;
    }

    public Connection getConnection(){
        if(conn==null){
            synchronized (DbSingleton.class){
                if(conn==null){
                    String dbUrl = "jdbc:derby:memory:codejava/webdb;create=true";
                    try {
                        conn = DriverManager.getConnection(dbUrl);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }return conn;
    }


}