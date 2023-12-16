import java.awt.image.ComponentColorModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author 毅
 * 测试类
 */
public class Test {
    public static void main(String[] args) {
//        Commodity newCommodity=new Commodity("k",77);
//        OrderSystem.creatCommodity(newCommodity);
//        Commodity oldCommodity=new Commodity(12,"j",700);
//        OrderSystem.creatCommodity(commodity);
//        OrderSystem.printCommodity("");
//        OrderSystem.modifyCommodity(oldCommodity,newCommodity);
//        OrderSystem.destructCommodity(commodity);
//        OrderSystem.printCommodity("");
        Commodity commodityb=new Commodity(2,"b",200);
        Commodity commodityc=new Commodity(3,"c",300);
        OrderList orderList=new OrderList();
        orderList.addCommodity(commodityb,10);
        orderList.addCommodity(commodityc,5);
        orderList.printOrderList();
    }
}
