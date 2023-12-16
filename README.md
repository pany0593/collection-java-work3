# collection-java

## work3 - 062100249 潘毅

### 数据库结构

数据库 Work3

- 商品表单Commodity
  - 商品自增编号 commodityID(primary key)
  - 商品名称 name
    - 商品价格 price
- 订单表单Order
  - 订单自增编号 orderID(primary key)
  - 订单购买商品信息（通过中间表查询）
  - 下单时间 Time
  - 订单总价 totalPrice
  - 订单包含该商品数量number
- 订单-商品中间表Order_Commodity
  - primary key（orderID,commodityID）
  - 订单编号orderID
  - 订单包含的商品编号commodityID

### 代码结构

- ##### 测试类 Test

- ##### JDBC工具类 JDBCUtils

  - 