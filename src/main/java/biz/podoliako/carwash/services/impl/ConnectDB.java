package biz.podoliako.carwash.services.impl;


import biz.podoliako.carwash.services.ConnectionDB;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.NoInitialContextException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Component
@Profile("dev")
public class ConnectDB implements ConnectionDB {
    private final String LOGIN = "Bizon4ik";
    private final String PASSWORD = "5700876";

    public static Connection getOneConnection() throws SQLException {
        Properties conProperties = new ConnectDB().getProperties();

        Connection con =
                DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/CarWash_Real?autoReconnect=true&amp;useUnicode=true&amp;characterEncoding=UTF-8", conProperties);
        return con;
    }

    private Properties getProperties() {
        Properties conProperties = new Properties();
        conProperties.setProperty("user", LOGIN);
        conProperties.setProperty("password", PASSWORD);
        return conProperties;
    }

    public static Connection getPoolConnection () throws NamingException, SQLException {
        try {
            InitialContext initContext= new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("java:comp/env/jdbc/CarWash");
            return ds.getConnection();
        }catch (NoInitialContextException exception) {
            System.out.println("WARNING: the TomCat connection pool was not used. You are working only with one connection");
            return getOneConnection();
        }
    }

    @Override
    public Connection getConnection() throws NamingException, SQLException {
        try {
            InitialContext initContext= new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("java:comp/env/jdbc/CarWash");
            return ds.getConnection();
        }catch (NoInitialContextException exception) {
            System.out.println("WARNING: the TomCat connection pool was not used. You are working only with one connection");
            return getOneConnection();
        }


    }
}
