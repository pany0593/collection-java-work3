import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 毅
 * 订单类
 * 包含 购物车信息,订单创建时间,订单ID
 */
public class Order {
    private int orderId;
    private Date time;
    private OrderList orderList;

    public Order() {
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }


    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }


    public OrderList getOrderList() {
        return orderList;
    }

    public void setOrderList(OrderList orderList) {
        this.orderList = orderList;
    }
}
