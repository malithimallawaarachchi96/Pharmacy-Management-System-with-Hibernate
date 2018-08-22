package lk.ijse.pharmacy.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static DBConnection dbConnection;
    private Connection connection;

    private DBConnection() throws ClassNotFoundException, SQLException, FileNotFoundException, IOException {
        Class.forName("com.mysql.jdbc.Driver");

        File dbPropertiesFile = new File("resources/application.properties");
        FileReader fileReader = new FileReader(dbPropertiesFile);

        Properties dbProperties = new Properties();
        dbProperties.load(fileReader);

        String ip = dbProperties.getProperty("ip");
        String port = dbProperties.getProperty("port");
        String database = dbProperties.getProperty("database");
        String username = dbProperties.getProperty("username");
        String password = dbProperties.getProperty("password");

        String url = "jdbc:mysql://" + ip + ":" + port + "/" + database;

        connection = DriverManager.getConnection(url,username,password);
    }

    public static DBConnection getInstance() throws Exception{
        if (dbConnection == null){
            dbConnection = new DBConnection();
        }
        return dbConnection;
    }

    public Connection getConnection(){
        return connection;
    }
}
