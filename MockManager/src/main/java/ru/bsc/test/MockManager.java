package ru.bsc.test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by sdoroshin on 11.05.2017.
 *
 */
@SuppressWarnings("unused")
public class MockManager {

    public static void main(String[] args) throws Exception {
        MockManager.getResponse("GM", "RESTMockService2", "request content", "-");
    }

    @SuppressWarnings("WeakerAccess")
    public static String getResponse(String projectCode, String serviceName, String requestContent, String sessionUid) throws SQLException, IOException, ClassNotFoundException {
        try (Connection connection = getConnection()) {
            try (PreparedStatement psResponse = connection.prepareCall("SELECT * FROM (SELECT * FROM AT_SERVICE_RESPONSE WHERE SERVICE_NAME = ? AND SESSION_UID = ? AND PROJECT_CODE = ? AND IS_CALLED = 0 ORDER BY SORT) WHERE rownum = 1")) {
                psResponse.setString(1, serviceName);
                psResponse.setString(2, sessionUid);
                psResponse.setString(3, projectCode);
                ResultSet resultSet = psResponse.executeQuery();
                if (resultSet.next()) {

                    try (PreparedStatement psUpdate = connection.prepareStatement("UPDATE AT_SERVICE_RESPONSE SET ACTUAL_REQUEST = ?, IS_CALLED = 1 WHERE ID = ?")) {
                        psUpdate.setString(1, requestContent);
                        psUpdate.setLong(2, resultSet.getLong("ID"));
                        psUpdate.executeUpdate();
                    }

                    return resultSet.getString("RESPONSE");
                } else {
                    return "";
                }
            }
        }
    }

    private static Connection getConnection() throws IOException, SQLException, ClassNotFoundException {
        Properties properties = new Properties();
        try (InputStream input = MockManager.class.getClassLoader().getResourceAsStream("database.properties")) {
            properties.load(input);
            Class.forName(properties.getProperty("db.driverClassName"));
            return DriverManager.getConnection(properties.getProperty("db.url"), properties.getProperty("db.user"), properties.getProperty("db.password"));
        }
    }
}
