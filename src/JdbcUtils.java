import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @author 毅
 * JDBC工具类
 */
public class JdbcUtils {
    private static String driver = null;
    private static String url = null;
    private static String password = null;
    private static String username = null;

    static {
        InputStream inputStream = JdbcUtils.class.getClassLoader().getResourceAsStream("db.properties");
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        driver = properties.getProperty("driver");
        url = properties.getProperty("url");
        password = properties.getProperty("password");
        username = properties.getProperty("username");
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public static void executeUpdate(String sql)
    {
        Connection connection=null;
        Statement statement=null;
        try {
            connection=JdbcUtils.getConnection();
            statement=connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JdbcUtils.release(connection,statement,null);
    }
    public static String executeQuery(String sql)
    {
        Connection connection=null;
        Statement statement=null;
        ResultSet resultSet=null;
        String resultstring="\n打印商品信息：\n";
        int rowCount=0;
        try {
            connection=JdbcUtils.getConnection();
            statement=connection.createStatement();
            resultSet=statement.getResultSet();
            resultSet=statement.executeQuery(sql);
            while(resultSet.next())
            {
                rowCount++;
                resultstring+="ID: "+resultSet.getInt("commodityID")
                        +" name: "+resultSet.getString("name")
                        +" price: "+resultSet.getInt("price")+'\n';
            }
            resultstring+="共有 "+rowCount+" 个商品\n";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JdbcUtils.release(connection,statement,null);
        return resultstring;
    }
    public static void release(Connection connection, Statement statement, ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
