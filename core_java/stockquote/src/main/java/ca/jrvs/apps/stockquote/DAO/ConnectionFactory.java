package ca.jrvs.apps.stockquote.DAO;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.log4j.pattern.PropertiesPatternConverter;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    private static HikariConfig sqConfig = new HikariConfig();
    private static HikariDataSource bpDs;
    private ConnectionFactory() {

    }

   static {
       Properties configuration = new Properties();
       InputStream inputStream = PropertiesPatternConverter.class
               .getClassLoader()
               .getResourceAsStream("application.properties");
       try {
           configuration.load(inputStream);
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
       try {
           inputStream.close();
       } catch (IOException e) {
           throw new RuntimeException(e);
       }

       sqConfig.setJdbcUrl(configuration.getProperty("db.url"));
        sqConfig.setUsername(configuration.getProperty("db.username"));
        sqConfig.setPassword(configuration.getProperty("db.password"));
        sqConfig.addDataSourceProperty( "cachePrepStmts" , "true" );
        sqConfig.addDataSourceProperty( "prepStmtCacheSize" , "20" );
        sqConfig.addDataSourceProperty( "prepStmtCacheSqlLimit" , "64" );

        sqConfig.setAllowPoolSuspension(false);
        sqConfig.setMaximumPoolSize(5);
        sqConfig.setConnectionTimeout(30000);
        sqConfig.setIdleTimeout(10000);
        sqConfig.setLeakDetectionThreshold(30000);
        sqConfig.setMaxLifetime(60000);
        sqConfig.setMinimumIdle(0);
        sqConfig.setPoolName("BP-DB-POOL");

        bpDs = new HikariDataSource(  sqConfig);

    }

    public static Connection getConnection() throws SQLException {

        return bpDs.getConnection();
    }
}
