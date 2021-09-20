package com.yzt.tacos.data;

import com.yzt.tacos.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/* Domain-Specific Language, DSL(领域特定语言):
* 当创建repository实现的时候,Spring Data会检查repository接口的所有方法,
* 解析方法的名称, 并基于被持久化的对象来试图推测方法的目的,
* 持久化的细节通过repository的方法的签名来描述;
*
* Spring Data能够知道这个方法是查找Order的,
* 因为使用Order对CrudRepository进行了参数化,
* findByDeliveryZip()根据deliveryZip属性相匹配来查找Order,
* deliverZip的值作为参数传递到方法中来.
* */
public interface OrderRepository extends CrudRepository<Order, Long> {

    // 获取投递到指定邮编Zip
    List<Order> findByDeliveryZip(String deliveryZip);
}
