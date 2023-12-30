package ordersystem.util;


import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JDBCUtils {

    // 加载数据库配置信息
    private static Properties loadProperties() throws IOException {
        Properties prop = new Properties();
        try (InputStream input = JDBCUtils.class.getClassLoader().getResourceAsStream("db.properties")) {
            prop.load(input);
        }
        return prop;
    }

    // 获取数据库连接
    public static Connection getConnection() {
        Connection connection = null;
        try {
            Properties prop = loadProperties();
            String driver = prop.getProperty("driver");
            String url = prop.getProperty("url");
            String user = prop.getProperty("user");
            String password = prop.getProperty("password");
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return connection;
    }

    // 关闭资源
    public static void closeResources(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 创建 PreparedStatement 对象
    public static PreparedStatement createPreparedStatement(Connection conn, String sql) {
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pstmt;
    }
}
