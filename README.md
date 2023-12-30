# 项目结构

```
src
├─main
│  ├─java
│  │  └─ordersystem
│  │      ├─dao
│  │      │      OrderDAO.java			//订单DAO类
│  │      │      ProductDAO.java		//商品DAO类
│  │      │
│  │      ├─model
│  │      │      Order.java					//订单模型类
│  │      │      Product.java				//商品模型类
│  │      │
│  │      └─util
│  │              JDBCUtils.java			//JDBC工具类
│  │
│  └─resources
│          db.properties						//数据库配置文件
│          schema.sql						//数据库初始化文件
│
└─test
    └─java
            test.java							//项目测试类
```



# 数据库结构

- orders表

  - order_id——自增主键
  - order_time——创建订单时间 	

  - order_price——订单总价

  - order_state——订单状态默认1/已删除0

- product表
  - product_id——自增主键
  - product_name——商品名
  - product_price——商品价格
  - product_state——商品状态默认1/已删除0
- order_product关联表
  - order_id——联合主键
  - product_id——联合主键
  - quantity——商品数量
  - state——记录的状态默认1/已删除0



### 一些问题

- 如何解决sql注入问题

  - 使用预编译的SQL语句和参数化的查询PreparedStatement，避免直接拼接参数
  - 输入验证/过滤(没写..原本打算写一个验证工具类对参数进行验证，后面用了上面这个应该就不用了⑧)

- 事务管理

  - 就简单的在添加失败什么的情况回滚了一下，没有另外写了

- 异常处理

  - 也没有再写什么自定义异常类了，直接输出了错误信息

- 验证数据完整性

  - 写了isProductExists和isOrderExists两个方法判断

- 删除商品/订单

  - 都没有真正删除，只是在数据库中把状态清零，对于清零的记录都无法被查询到

  

  

  //测试类都是自己写代码的过程中测试的，比较乱orz

  //对于bonus里的要求除了简单的看了一下Maven其他就没有多看了

  //各框架都还没有学习

  //orz考试全堆在一块了实在折磨orz//

  
