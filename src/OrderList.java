import java.util.HashMap;
import java.util.Map;

/**
 * @author 毅
 * 购物车
 * 在购物车内添加商品m
 * 包含商品种类及个数,商品总价,商品总数
 */
public class OrderList {

    private Map<Integer, Integer> commodityMap = new HashMap<>();
    private int totalPrice;
    private int totalNumber;

    /**无法通过id直接选择物品
     而是通过构造commodity类来添加物品
     */
    public void addCommodity(Commodity commodity, int number)
    {
        commodityMap.put(commodity.getCommodityID(),number);
        totalPrice+=commodity.getCommodityPrice()*number;
        totalNumber+=number;
    }
    public void deleteCommodity(Commodity commodity,int number)
    {
        if(!commodityMap.containsKey(commodity.getCommodityID()))
        {
            System.out.println("购物车中不存在该物品");
            return;
        }
        int sum=commodityMap.get(commodity.getCommodityID());
        if(sum<number)
        {
            System.out.println("购物车中只有"+sum+"个该物品");
            return;
        }
        commodityMap.put(commodity.getCommodityID(),sum-number);
    }
    public Map<Integer, Integer> getCommodityMap() {
        return commodityMap;
    }

    public void setCommodityMap(Map<Integer, Integer> commodityMap) {
        this.commodityMap = commodityMap;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
    }
    public void printOrderList()
    {
        String ans="购物车中有"+this.totalNumber+"个物品\n";
        //只显示id和个数
        //没有通过id查找详细商品资料
        for(Integer key:commodityMap.keySet())
        {
            ans+="id: "+key+" 个数: "+commodityMap.get(key)+"\n";
        }
        ans+="总价： "+this.totalPrice+"\n";
        System.out.println(ans);
    }

}
