/**
 * @author 毅
 * 商品类
 * 包含商品id,商品名称,商品价格
 */
public class Commodity {
    private int commodityId;
    private String commodityName;
    private int commodityPrice;

    public Commodity() {
    }

    public Commodity(String commodityName, int commodityPrice) {
        this.commodityName = commodityName;
        this.commodityPrice = commodityPrice;
    }

    public Commodity(int commodityId, String commodityName, int commodityPrice) {
        this.commodityId = commodityId;
        this.commodityName = commodityName;
        this.commodityPrice = commodityPrice;
    }


    public int getCommodityID() {
        return commodityId;
    }

    public void setCommodityID(int commodityID) {
        this.commodityId = commodityID;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public int getCommodityPrice() {
        return commodityPrice;
    }

    public void setCommodityPrice(int commodityPrice) {
        this.commodityPrice = commodityPrice;
    }

    @Override
    public String toString() {
        return "Commodity{" +
                "commodityId=" + commodityId +
                ", commodityName='" + commodityName + '\'' +
                ", commodityPrice=" + commodityPrice +
                '}';
    }
}
