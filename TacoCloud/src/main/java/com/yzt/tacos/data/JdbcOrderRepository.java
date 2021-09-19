package com.yzt.tacos.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yzt.tacos.Order;
import com.yzt.tacos.Taco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcOrderRepository implements OrderRepository{

    private SimpleJdbcInsert orderInserter;
    private SimpleJdbcInsert orderTacoInserter;
    private ObjectMapper objectMapper;

    @Autowired
    /* 通过构造器将jdbcTemplate注入进来;
    * 这里没有将jdbc赋给实例变量, 而是使用它创建了两个SimpleJdbcInsert实例;
    * */
    public JdbcOrderRepository(JdbcTemplate jdbc){
        this.orderInserter = new SimpleJdbcInsert(jdbc)
                .withTableName("Taco_Order")
                .usingGeneratedKeyColumns("id");
        // orderInserter实例变量, 配置为与Taco_Order表协作, 并且假定id属性将会由数据库提供或生成

        this.orderTacoInserter = new SimpleJdbcInsert(jdbc)
                .withTableName("Taco_Order_Tacos");
        // orderTacoInserter实例变量, 配置为与Taco_Order_Tacos表协作, 没有声明id如何生成

        this.objectMapper = new ObjectMapper();
        // 创建Jackson中ObjectMapper类的一个实例, 将其赋值给一个实例变量
        /* com.fasterxml.jackson.databind.ObjectMapper
        * Jackson初衷用于进行JSON处理, 这里我们用来保存订单和关联的taco
        * */
    }

    @Override
    /* save()实际上并没有保存如何内容,
    * save()只是定义了保存Order及关联的Taco对象的流程,
    * 实际的持久化任务委托给了saveOrderDetails()和saveTacoToOrder()
    * */
    public Order save(Order order) {
        order.setPlaceAt(new Date());
        long orderId = saveOrderDetails(order);
        order.setId(orderId);
        List<Taco> tacos = order.getTacos();
        for(Taco taco : tacos){
            saveTacoToOrder(taco, orderId);
        }

        return order;
    }

    private long saveOrderDetails(Order order){
        @SuppressWarnings("unchecked")
        Map<String, Object> values = objectMapper.convertValue(order, Map.class);
        // 将Order转换为Map
        values.put("placeAt", order.getPlaceAt());
        // 将Map中的placeAt条目的值设置为Order中placeAt属性的值
        // (因为ObjectMapper会把Date属性转换成long, 导致类型不兼容)

        long orderId = orderInserter
                .executeAndReturnKey(values)
                .longValue();
        // 将values(即order)保存到Taco_Order表中,
        // 并以Number对象的形式返回数据库生成的ID, 继而调用longValue()转换成long类型

        return orderId;
    }

    private void saveTacoToOrder(Taco taco, long orderId){
        Map<String, Object> values = new HashMap<>();
        values.put("tacoOrder", orderId);
        values.put("taco", taco.getId());
        // Map的key与表中列名对应
        orderTacoInserter.execute(values);
    }
}
