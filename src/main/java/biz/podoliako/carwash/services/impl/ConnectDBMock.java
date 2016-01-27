package biz.podoliako.carwash.services.impl;

import biz.podoliako.carwash.services.ConnectionDB;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectDBMock implements ConnectionDB {
    @Override
    public Connection getConnection() throws NamingException, SQLException {
        return null;
    }
}
