package ordersystem.dao;

import ordersystem.model.Product;
import ordersystem.util.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 毅
 */
public class ProductDAO {

    @SuppressWarnings("AlibabaConstantFieldShouldBeUpperCase")
    private static final Connection conn = JDBCUtils.getConnection();
    private static final String SELECT_PRODUCT_BY_ID = "SELECT product_id FROM product WHERE product_id = ?";
    private static final String SELECT_PRODUCTS = "SELECT product_id, product_name, product_price FROM product where product_state = 1";
    private static final String DELETE_PRODUCT = "UPDATE product SET product_state = 0 WHERE product_id = ?";
    private static final String INSERT_PRODUCT = "INSERT INTO product (product_name, product_price) VALUES (?, ?)";
    private static final String CHECK_PRODUCT_EXISTENCE = "SELECT COUNT(*) AS count, product_state FROM product WHERE product_id = ?";
    private static final String UPDATE_PRODUCT = "UPDATE product SET product_name = ?, product_price = ? WHERE product_id = ?";
    private static final String GET_PRODUCT_PRICE = "SELECT product_price FROM product WHERE product_id = ?";

    // 检查商品是否存在
    public static boolean isProductExists(int productId) {
        try {
            synchronized (conn) {
                try (PreparedStatement pstmt = conn.prepareStatement(CHECK_PRODUCT_EXISTENCE)) {
                    pstmt.setInt(1, productId);
                    ResultSet rs = pstmt.executeQuery();
                    if (rs.next()) {
                        int count = rs.getInt("count");
                        int productState = rs.getInt("product_state");
                        return count > 0 && productState == 1;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static int getProductPrice(int productId) {
        try (
                Connection conn = JDBCUtils.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(GET_PRODUCT_PRICE)
        ) {
            pstmt.setInt(1, productId);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("product_price");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // 返回负数表示商品不存在或查询失败
    }
    // 插入新商品
    public boolean insertProduct(Product product) {

        try (
                Connection conn = JDBCUtils.getConnection();
                PreparedStatement pstmtSelect = conn.prepareStatement(SELECT_PRODUCT_BY_ID);
                PreparedStatement pstmtInsert = conn.prepareStatement(INSERT_PRODUCT, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            // 检查商品是否已存在
            if (isProductExists(product.getProductId())) {
                System.out.println("商品已存在，无法重复添加。");
                return false;
            }

            // 插入新商品
            pstmtInsert.setString(1, product.getProductName());
            pstmtInsert.setDouble(2, product.getProductPrice());

            int rowsAffected = pstmtInsert.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet generatedKeys = pstmtInsert.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    product.setProductId(generatedId); // 设置Product对象的ID
                    return true; // 成功插入并设置ID后返回 true
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    // 删除商品
    public boolean deleteProduct(int productId) {
        // 首先检查商品是否存在
        if (!isProductExists(productId)) {
            System.out.println("商品不存在，无法修改。");
            return false;
        }

        try (
                Connection conn = JDBCUtils.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(DELETE_PRODUCT)
        ) {
            pstmt.setInt(1, productId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    // 修改商品信息
    public boolean updateProduct(Product product) {
        // 首先检查商品是否存在
        if (!isProductExists(product.getProductId())) {
            System.out.println("商品不存在，无法修改。");
            return false;
        }

        try (
                Connection conn = JDBCUtils.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(UPDATE_PRODUCT)
        ) {
            pstmt.setString(1, product.getProductName());
            pstmt.setDouble(2, product.getProductPrice());
            pstmt.setInt(3, product.getProductId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    // 获取所有商品
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        try (
                Connection conn = JDBCUtils.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(SELECT_PRODUCTS);
                ResultSet rs = pstmt.executeQuery()
        ) {
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("product_id"));
                product.setProductName(rs.getString("product_name"));
                product.setProductPrice(rs.getDouble("product_price"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

}
