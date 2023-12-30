package ordersystem.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Order {
    private int orderId;
    private Date orderTime;
    private Map<Product, Integer> products; // 使用 Map 存储产品和其数量的关系

    public Order() {}

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Product, Integer> products) {
        this.products = products;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public double calculateOrderPrice() {
        double totalPrice = 0.0;
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            totalPrice += product.getProductPrice() * quantity;
        }
        return totalPrice;
    }
}
