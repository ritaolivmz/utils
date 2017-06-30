package com.nnip.aws.rds;

import java.sql.Connection;
import java.sql.DriverManager;

import com.nnip.exceptions.RDSConnectionManagerException;

public class RDSConnectionManager {

    private Connection con;

    /**
     * Method that establishes a connection to a RDS DB.
     * @param dbName
     * @param userName
     * @param password
     * @param hostname
     * @param port
     * @return
     * @throws RDSConnectionManagerException
     */
    public Connection getRemoteConnection(String dbName, String userName, String password,
                                                 String hostname, String port) throws RDSConnectionManagerException {
        try {
            String jdbcUrl = "jdbc:mysql://" + hostname + ":" + port + "/" + dbName + "?user=" + userName + "&password=" + password;
            con = DriverManager.getConnection(jdbcUrl);
            return con;
        }
        catch (Exception e) {
            throw new RDSConnectionManagerException(e.getMessage(), e);
        }
    }

    /**
     * Closes current RDS connection.
     * @throws RDSConnectionManagerException
     */
    public void closeConnection() throws RDSConnectionManagerException {
        try {
            con.close();
        }
        catch (Exception e) {
            throw new RDSConnectionManagerException(e.getMessage(), e);
        }
    }
}
