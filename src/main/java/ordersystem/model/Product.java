package ordersystem.model;



/**
 * @author 毅
 */
public class Product {
    private int productId;
    private String productName;
    private double productPrice;
    private int productState;

    public Product() {

    }

    public Product(int productId, String productName, double productPrice, int productState) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productState = productState;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                '}';
    }

    public void setProductState(int productStare) {
        this.productState = productStare;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }
}
