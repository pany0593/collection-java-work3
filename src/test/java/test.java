import ordersystem.util.JDBCUtils;
import ordersystem.model.Product;
import ordersystem.model.Order;
import ordersystem.dao.ProductDAO;
import ordersystem.dao.OrderDAO;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class test {
    public static void main(String[] args) {

        // 创建 ProductDAO 实例
        ProductDAO productDAO = new ProductDAO();
        // 创建OrderDAO实例
        OrderDAO orderDAO = new OrderDAO();

//        // 成功增加商品 并输出ID
//        // 创建一个新的商品对象
//        Product newProduct = new Product();
//        newProduct.setProductName("New Product");
//        newProduct.setProductPrice(99.99);
//
//        // 调用 insertProduct 方法添加商品
//        boolean isProductInserted = productDAO.insertProduct(newProduct);
//
//        if (isProductInserted) {
//            System.out.println("商品成功添加到数据库。");
//            System.out.println("商品ID：" + newProduct.getProductId()); // 打印插入后的商品ID
//        } else {
//            System.out.println("添加商品失败。");
//        }
//
//        // 增加商品失败 商品已经存在
//        // 创建一个新的商品对象
//        Product newProduct = new Product();
//        newProduct.setProductName("New Product");
//        newProduct.setProductPrice(99.99);
//
//        // 调用 insertProduct 方法添加商品
//        productDAO.insertProduct(newProduct);
//        boolean isProductInserted = productDAO.insertProduct(newProduct);
//
//        if (isProductInserted) {
//            System.out.println("商品成功添加到数据库。");
//            System.out.println("商品ID：" + newProduct.getProductId()); // 打印插入后的商品ID
//        } else {
//            System.out.println("添加商品失败。");
//        }
//
//
//        //   成功修改
//        // 创建一个要修改的商品对象
//        Product updatedProduct = new Product();
//        updatedProduct.setProductId(15); // 假设要修改的商品ID为15
//        updatedProduct.setProductName("Updated Product");
//        updatedProduct.setProductPrice(129.99);
//
//        // 调用 updateProduct 方法更新商品信息
//        boolean isProductUpdated = productDAO.updateProduct(updatedProduct);
//
//        if (isProductUpdated) {
//            System.out.println("商品信息成功更新。");
//        } else {
//            System.out.println("更新商品信息失败。");
//        }
//
//        // 修改失败 商品不存在
//        // 创建一个要修改的商品对象
//        Product updatedProduct = new Product();
//        updatedProduct.setProductId(100); // 假设要修改的商品ID为100
//        updatedProduct.setProductName("Updated Product");
//        updatedProduct.setProductPrice(129.99);
//
//        // 调用 updateProduct 方法更新商品信息
//        boolean isProductUpdated = productDAO.updateProduct(updatedProduct);
//
//        if (isProductUpdated) {
//            System.out.println("商品信息成功更新。");
//        } else {
//            System.out.println("更新商品信息失败。");
//        }
//
//        // 成功删除商品
//        int productIdToDelete = 14; // 假设要删除的商品ID为15
//
//        // 调用 deleteProduct 方法删除商品
//        boolean isProductDeleted = productDAO.deleteProduct(productIdToDelete);
//
//        if (isProductDeleted) {
//            System.out.println("商品成功从数据库中删除。");
//        } else {
//            System.out.println("删除商品失败或商品不存在。");
//        }
//
//         删除商品失败 商品不存在
//        int productIdToDelete = 100; // 假设要删除的商品ID为100
//
//        // 调用 deleteProduct 方法删除商品
//        boolean isProductDeleted = productDAO.deleteProduct(productIdToDelete);
//
//        if (isProductDeleted) {
//            System.out.println("商品成功从数据库中删除。");
//        } else {
//            System.out.println("删除商品失败或商品不存在。");
//        }
//
//        // 查询所有存在的商品
//        // 调用 getAllProducts 方法获取所有商品信息
//        List<Product> allProducts = productDAO.getAllProducts();
//
//        // 打印所有商品信息
//        if (allProducts.isEmpty()) {
//            System.out.println("数据库中没有商品信息。");
//        } else {
//            System.out.println("所有商品信息：");
//            for (Product product : allProducts) {
//                System.out.println("商品ID: " + product.getProductId());
//                System.out.println("商品名称: " + product.getProductName());
//                System.out.println("商品价格: " + product.getProductPrice());
//                System.out.println("------------------------------------");
//            }
//        }
//
//        // 成功创建订单
//        // 创建一个新的订单对象
//        Order newOrder = new Order();
//        newOrder.setOrderTime(new Date()); // 设置订单时间为当前时间
//
//        // 假设创建一个包含多个产品及其数量的订单
//        Map<Product, Integer> productQuantityMap = new HashMap<>();
//
//        Product product1 = new Product();
//        product1.setProductId(16); // 假设添加产品1的ID为16
//        product1.setProductPrice(ProductDAO.getProductPrice(product1.getProductId()));
//        productQuantityMap.put(product1, 3); // 数量为3
//
//        Product product2 = new Product();
//        product2.setProductId(17); // 假设添加产品2的ID为17
//        product2.setProductPrice(ProductDAO.getProductPrice(product2.getProductId()));
//        productQuantityMap.put(product2, 2); // 数量为2
//        newOrder.setProducts(productQuantityMap);
//
//        // 调用 insertOrder 方法插入订单
//        boolean isOrderInserted = orderDAO.insertOrder(newOrder);
//
//        if (isOrderInserted) {
//            System.out.println("订单成功添加到数据库。");
//            // 获取并输出生成的订单ID
//            System.out.println("生成的订单ID：" + newOrder.getOrderId());
//            // 打印订单的总价值
//            System.out.println("订单总价值：" + newOrder.calculateOrderPrice());
//        } else {
//            System.out.println("添加订单失败。");
//        }
//
//        // 创建订单失败 订单内不含商品
//        // 创建一个新的订单对象
//        Order newOrder = new Order();
//        newOrder.setOrderTime(new Date()); // 设置订单时间为当前时间
//
//        // 调用 insertOrder 方法插入订单
//        boolean isOrderInserted = orderDAO.insertOrder(newOrder);
//
//        if (isOrderInserted) {
//            System.out.println("订单成功添加到数据库。");
//            // 获取并输出生成的订单ID
//            System.out.println("生成的订单ID：" + newOrder.getOrderId());
//            // 打印订单的总价值
//            System.out.println("订单总价值：" + newOrder.calculateOrderPrice());
//        } else {
//            System.out.println("添加订单失败。");
//        }
//
//
//        // 删除订单成功
//        int orderIdToDelete = 2; // 假设要删除的订单 ID 为 2
//        // 调用删除订单方法
//        boolean isOrderDeleted = orderDAO.deleteOrder(orderIdToDelete);
//        if (isOrderDeleted) {
//            System.out.println("订单删除成功。");
//        } else {
//            System.out.println("订单删除失败。");
//        }
//
//        // 删除订单失败 订单ID不存在
//        int orderIdToDelete = -2; // 假设要删除的订单 ID 为 -2
//        // 调用删除订单方法
//        boolean isOrderDeleted = orderDAO.deleteOrder(orderIdToDelete);
//        if (isOrderDeleted) {
//            System.out.println("订单删除成功。");
//        } else {
//            System.out.println("订单删除失败。");
//        }
//
//        // 修改订单成功
//        int oldOrderId = 15; // 假设要修改的旧订单 ID 为 15
//        Order newOrder = new Order();
//        // 设置新订单的信息，包括产品信息
//        Map<Product, Integer> productQuantityMap = new HashMap<>();
//
//        Product product1 = new Product();
//        product1.setProductId(18); // 假设添加产品1的ID为18
//        product1.setProductPrice(ProductDAO.getProductPrice(product1.getProductId()));
//        productQuantityMap.put(product1, 10); // 数量为3
//        newOrder.setProducts(productQuantityMap);
//
//        // 调用修改订单方法
//        boolean isOrderUpdated = orderDAO.updateOrder(oldOrderId, newOrder);
//        if (isOrderUpdated) {
//            System.out.println("订单修改成功。");
//        } else {
//            System.out.println("订单修改失败。");
//        }
//
//        // 修改订单失败 订单ID不存在
//        int oldOrderId = 17; // 假设要修改的旧订单 ID 为 17
//        Order newOrder = new Order();
//        // 设置新订单的信息，包括产品信息
//        Map<Product, Integer> productQuantityMap = new HashMap<>();
//
//        Product product1 = new Product();
//        product1.setProductId(18); // 假设添加产品1的ID为18
//        product1.setProductPrice(ProductDAO.getProductPrice(product1.getProductId()));
//        productQuantityMap.put(product1, 10); // 数量为3
//        newOrder.setProducts(productQuantityMap);
//
//        // 调用修改订单方法
//        boolean isOrderUpdated = orderDAO.updateOrder(oldOrderId, newOrder);
//        if (isOrderUpdated) {
//            System.out.println("订单修改成功。");
//        } else {
//            System.out.println("订单修改失败。");
//        }
//
//        // 修改订单失败 新订单中不包含任何商品
//        int oldOrderId = 15; // 假设要修改的旧订单 ID 为 15
//        Order newOrder = new Order();
//
//        // 调用修改订单方法
//        boolean isOrderUpdated = orderDAO.updateOrder(oldOrderId, newOrder);
//        if (isOrderUpdated) {
//            System.out.println("订单修改成功。");
//        } else {
//            System.out.println("订单修改失败。");
//        }
//
//
//        // 测试按订单ID升序打印订单
//        orderDAO.printSortedOrders(0);
//
//        // 测试按价格升序打印订单
//        orderDAO.printSortedOrders(1);
//
//        // 测试按订单时间升序打印订单
//        orderDAO.printSortedOrders(2);
//
//
//        // 查询指定ID订单
//        // 测试打印指定订单 ID 的商品信息
//        int orderId = 15; // 指定订单 ID 15
//        orderDAO.printOrderById(orderId);
//
//        // 查询指定ID商品存在于哪些订单中
//        // 成功查询指定ID商品存在于哪些订单中
//        orderDAO.printOrderByProduct(18);
//        // 查找指定ID商品不存在
//        orderDAO.printOrderByProduct(1);
    }
}
