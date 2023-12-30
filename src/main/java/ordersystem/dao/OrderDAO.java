package ordersystem.dao;

import ordersystem.model.Order;
import ordersystem.model.Product;
import ordersystem.util.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Date;

public class OrderDAO {

    private static final Connection conn = JDBCUtils.getConnection();
    private static final String CHECK_ORDER_EXISTENCE = "SELECT COUNT(*) AS count, order_state FROM orders WHERE order_id = ?";
    private static final String INSERT_ORDER = "INSERT INTO orders (order_time, order_price) VALUES (?, ?)";
    private static final String INSERT_ORDER_PRODUCT = "INSERT INTO order_product (order_id, product_id, quantity) VALUES (?, ?, ?)";
    private static final String UPDATE_ORDER_STATE = "UPDATE orders SET order_state = 0 WHERE order_id = ?";
    private static final String UPDATE_ORDER_PRODUCT_STATE = "UPDATE order_product SET state = 0 WHERE order_id = ?";
    private static final String UPDATE_ORDER_PRICE ="UPDATE orders SET order_price = ? WHERE order_id = ?";
    private static final String SELECT_ORDERS = "SELECT order_id, order_time, order_price FROM orders WHERE order_state = 1 ORDER BY ";
    private static final String SELECT_ORDER_PRODUCTS = "SELECT product_id, quantity FROM order_product WHERE order_id = ?";
    private static final String SELECT_ORDER_BY_ID = "SELECT order_id, order_time, order_price FROM orders WHERE order_id = ? AND order_state = 1";
    private static final String SELECT_ORDER_PRODUCTS_BY_ORDER_ID = "SELECT p.product_id, p.product_name, op.quantity FROM product p JOIN order_product op ON p.product_id = op.product_id WHERE op.order_id = ? and op.state = 1";
    private static final String SELECT_ORDERS_BY_PRODUCT = "SELECT DISTINCT op.order_id FROM order_product op WHERE op.product_id = ? and op.state = 1";
//    private static final String SELECT_ALL_ORDERS = "SELECT order_id, order_name, order_price FROM orders";
//    private static final String UPDATE_ORDER = "UPDATE orders SET order_price = ?, order_time = ? WHERE order_id = ?";
//    private static final String UPDATE_ORDER_PRODUCT = "UPDATE order_product SET quantity = ? WHERE order_id = ? AND product_id = ?";
//    private static final String SELECT_ID_ORDERS = "SELECT order_id, order_name, order_price FROM orders where order_id = ?";

    public boolean updateOrder(int oldOrderId, Order newOrder) {
        synchronized (conn) {
            try {
                Map<Product, Integer> newProducts = newOrder.getProducts();

                if (newProducts != null && !newProducts.isEmpty()) {
                    if (!isOrderExists(oldOrderId)) {
                        System.out.println("旧订单不存在，无法修改订单。");
                        return false;
                    }

                    double newOrderPrice = newOrder.calculateOrderPrice();

                    conn.setAutoCommit(false);

                    try (PreparedStatement pstmtOrderProduct = conn.prepareStatement(UPDATE_ORDER_PRODUCT_STATE);
                         PreparedStatement pstmtInsertOrderProduct = conn.prepareStatement(INSERT_ORDER_PRODUCT);
                         PreparedStatement pstmtUpdateOrderPrice = conn.prepareStatement(UPDATE_ORDER_PRICE)
                    ) {
                        pstmtOrderProduct.setInt(1, oldOrderId);
                        int rowsAffectedOrderProduct = pstmtOrderProduct.executeUpdate();

                        if (rowsAffectedOrderProduct > 0) {
                            for (Map.Entry<Product, Integer> entry : newProducts.entrySet()) {
                                Product product = entry.getKey();
                                int quantity = entry.getValue();

                                int productId = product.getProductId();
                                int productPrice = ProductDAO.getProductPrice(productId); // 获取产品价格

                                if (productPrice != -1) { // 确保产品存在于数据库中
                                    pstmtInsertOrderProduct.setInt(1, oldOrderId);
                                    pstmtInsertOrderProduct.setInt(2, productId);
                                    pstmtInsertOrderProduct.setInt(3, quantity);
                                    pstmtInsertOrderProduct.addBatch();
                                } else {
                                    System.out.println("订单包含了不存在的产品ID：" + productId);
                                    conn.rollback();
                                    return false; // 如果存在无效的产品ID，则回滚事务，返回失败
                                }
                            }

                            int[] batchResult = pstmtInsertOrderProduct.executeBatch();
                            for (int result : batchResult) {
                                if (result <= 0) {
                                    conn.rollback();
                                    throw new SQLException("插入订单-产品关联信息失败。");
                                }
                            }

                            pstmtUpdateOrderPrice.setDouble(1, newOrderPrice);
                            pstmtUpdateOrderPrice.setInt(2, oldOrderId);
                            int rowsAffectedUpdateOrderPrice = pstmtUpdateOrderPrice.executeUpdate();

                            if (rowsAffectedUpdateOrderPrice > 0) {
                                conn.commit();
                                return true;
                            } else {
                                conn.rollback();
                                return false;
                            }
                        } else {
                            conn.rollback();
                            return false;
                        }
                    } catch (SQLException e) {
                        conn.rollback();
                        e.printStackTrace();
                        return false;
                    }
                } else {
                    System.out.println("新订单中不存在任何产品，无法修改订单。");
                    return false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    private boolean isOrderExists(int orderId) {
        try {
            synchronized (conn) {
                try (PreparedStatement pstmt = conn.prepareStatement(CHECK_ORDER_EXISTENCE)) {
                    pstmt.setInt(1, orderId);
                    ResultSet rs = pstmt.executeQuery();
                    if (rs.next()) {
                        int count = rs.getInt("count");
                        int orderState = rs.getInt("order_state");
                        return count > 0 && orderState == 1;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean insertOrder(Order order) {
        Map<Product, Integer> products = order.getProducts();
        if (products == null || products.isEmpty()) {
            System.out.println("订单不包含任何产品");
            return false;
        }
        try (
                Connection conn = JDBCUtils.getConnection();
                PreparedStatement pstmtOrder = conn.prepareStatement(INSERT_ORDER, PreparedStatement.RETURN_GENERATED_KEYS);
                PreparedStatement pstmtOrderProduct = conn.prepareStatement(INSERT_ORDER_PRODUCT)
        ) {
            double orderPrice = order.calculateOrderPrice();

            pstmtOrder.setTimestamp(1, new java.sql.Timestamp(order.getOrderTime().getTime()));
            pstmtOrder.setDouble(2, orderPrice);

            int rowsAffected = pstmtOrder.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = pstmtOrder.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int orderId = generatedKeys.getInt(1);
                        order.setOrderId(orderId);

                        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
                            Product product = entry.getKey();
                            int quantity = entry.getValue();

                            int productId = product.getProductId();
                            int productPrice = ProductDAO.getProductPrice(productId); // 获取产品价格

                            if (productPrice != -1) { // 确保产品存在于数据库中
                                pstmtOrderProduct.setInt(1, orderId);
                                pstmtOrderProduct.setInt(2, productId);
                                pstmtOrderProduct.setInt(3, quantity);
                                pstmtOrderProduct.addBatch();
                            } else {
                                System.out.println("订单包含了不存在的产品ID：" + productId);
                                return false; // 如果存在无效的产品ID，则返回失败
                            }
                        }

                        int[] batchResult = pstmtOrderProduct.executeBatch();
                        for (int result : batchResult) {
                            if (result <= 0) {
                                throw new SQLException("插入订单-产品关联信息失败。");
                            }
                        }
                        return true;
                    } else {
                        throw new SQLException("插入订单失败，未获取到生成的订单ID。");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteOrder(int orderId) {
        try (Connection conn = JDBCUtils.getConnection()) {
            if (!isOrderExists(orderId)) {
                System.out.println("订单不存在，无法删除。");
                return false;
            }

            try (PreparedStatement pstmtOrder = conn.prepareStatement(UPDATE_ORDER_STATE);
                 PreparedStatement pstmtOrderProduct = conn.prepareStatement(UPDATE_ORDER_PRODUCT_STATE)
            ) {
                conn.setAutoCommit(false);

                pstmtOrder.setInt(1, orderId);
                int rowsAffectedOrder = pstmtOrder.executeUpdate();

                pstmtOrderProduct.setInt(1, orderId);
                int rowsAffectedOrderProduct = pstmtOrderProduct.executeUpdate();

                if (rowsAffectedOrder > 0 && rowsAffectedOrderProduct > 0) {
                    conn.commit();
                    return true;
                } else {
                    conn.rollback();
                    return false;
                }
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void printSortedOrders(int parameter) {
        String sortBy = "";
        switch (parameter) {
            case 0:
                sortBy = "order_id ASC";
                System.out.println("按照订单ID升序排序的订单列表：");
                break;
            case 1:
                sortBy = "order_price ASC";
                System.out.println("按照订单价格升序排序的订单列表：");
                break;
            case 2:
                sortBy = "order_time ASC";
                System.out.println("按照订单生成时间升序排序的订单列表：");
                break;
            default:
                System.out.println("参数无效。");
                return;
        }

        try (
                Connection conn = JDBCUtils.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(SELECT_ORDERS+ sortBy);
                ResultSet rs = pstmt.executeQuery()
        ) {
            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                Date orderTime = rs.getTimestamp("order_time");
                double orderPrice = rs.getDouble("order_price");

                System.out.println("订单ID: " + orderId + ", 订单时间: " + orderTime + ", 订单价格: " + orderPrice);

                try (PreparedStatement pstmtOrderProducts = conn.prepareStatement(SELECT_ORDER_PRODUCTS)) {
                    pstmtOrderProducts.setInt(1, orderId);
                    ResultSet rsOrderProducts = pstmtOrderProducts.executeQuery();

                    System.out.println("包含的商品及数量：");
                    while (rsOrderProducts.next()) {
                        int productId = rsOrderProducts.getInt("product_id");
                        int quantity = rsOrderProducts.getInt("quantity");

                        System.out.println("商品ID: " + productId + ", 数量: " + quantity);
                    }
                    System.out.println();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printOrderById(int orderId) {

        try (
                Connection conn = JDBCUtils.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(SELECT_ORDER_BY_ID);
        ) {
            pstmt.setInt(1, orderId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int foundOrderId = rs.getInt("order_id");
                Date orderTime = rs.getTimestamp("order_time");
                double orderPrice = rs.getDouble("order_price");

                System.out.println("订单ID: " + foundOrderId + ", 订单时间: " + orderTime + ", 订单价格: " + orderPrice);

                try (PreparedStatement pstmtOrderProducts = conn.prepareStatement(SELECT_ORDER_PRODUCTS_BY_ORDER_ID)) {
                    pstmtOrderProducts.setInt(1, foundOrderId);
                    ResultSet rsOrderProducts = pstmtOrderProducts.executeQuery();

                    System.out.println("包含的商品及数量：");
                    while (rsOrderProducts.next()) {
                        int productId = rsOrderProducts.getInt("product_id");
                        String productName = rsOrderProducts.getString("product_name");
                        int quantity = rsOrderProducts.getInt("quantity");

                        System.out.println("商品ID: " + productId + ", 商品名称: " + productName + ", 数量: " + quantity);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printOrderByProduct(int productId) {
        // 先检查商品是否存在
        if (!ProductDAO.isProductExists(productId)) {
            System.out.println("商品不存在。");
            return;
        }

        try (
                Connection conn = JDBCUtils.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(SELECT_ORDERS_BY_PRODUCT);
        ) {
            pstmt.setInt(1, productId);
            ResultSet rs = pstmt.executeQuery();

            System.out.println("包含商品ID为 " + productId + " 的订单ID列表：");
            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                System.out.println("订单ID: " + orderId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
