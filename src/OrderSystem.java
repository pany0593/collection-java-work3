import java.net.SocketTimeoutException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

/**
 * @author 毅
 * 订单系统类
 * 实现商品和订单信息的 增 删 改 查 更新 价格排序 下单时间排序
 */
public class OrderSystem {

    /**
     * 添加一个市面上的商品
     */
    public static void creatCommodity(Commodity commodity) {
        //创建结果没有判断
        String sql = "INSERT INTO `ordersystem`.`commodity` (`commodityID`, `name`, `price`) VALUES (NULL, '"
                + commodity.getCommodityName() + "', '"
                + commodity.getCommodityPrice() + "')";
        JdbcUtils.executeUpdate(sql);
    }

    /**
     * 销毁一个市面上存在的商品
     * 注意订单中已经存在的商品 同样也要销毁
     */
    public static void destructCommodity(Commodity commodity) {
        //没有判断被销毁物品是否存在
        //销毁是否成功没有判断
        String sql = "UPDATE `commodity` SET statement = '已销毁' WHERE NAME = '"
                + commodity.getCommodityName() + "'";
        JdbcUtils.executeUpdate(sql);
    }

    /**
     * 修改一个市面上存在的商品
     * 注意判断该商品是否存在
     */
    public static void modifyCommodity(Commodity oldCommodity, Commodity newCommodity) {
        //没有判断旧物品是否存在 通过id唯一确定
        //没有判断修改是否成功
        String sql = "UPDATE `commodity` SET NAME = '"
                + newCommodity.getCommodityName() + "',price ="
                + newCommodity.getCommodityPrice() + " WHERE commodityID = "
                + oldCommodity.getCommodityID();
        JdbcUtils.executeUpdate(sql);
    }

    /**
     * 打印市面上存在的所有商品
     * sortBy:
     * price desc价格降序
     * price asc价格升序
     */
    public static void printCommodity(String sortBy) {
        //排序规则判断
        if (Objects.equals(sortBy, "")) {
            sortBy = "commodityID";
        }
        String sql = "SELECT commodityID,NAME,price FROM commodity WHERE statement = '已创建' ORDER BY "
                + sortBy;
        System.out.println(JdbcUtils.executeQuery(sql));
    }

    /**
     * 添加一个订单
     */
    public void creatOrder(Order order) {

    }

    /**
     * 销毁一个订单
     */
    public void destructOrder(Order order) {

    }

    /**
     * 修改一个订单
     */
    public void modifyOrder(Order oldOrder, Order newOrder) {

    }

    /**
     * 打印所有订单
     * sortBy:
     * price desc总价降序
     * price asc总价升序
     * time desc下单时间降序
     * time asc下单时间升序
     */
    public String printOrder(String sortBy) {
        return "";
    }
}
