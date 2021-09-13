package com.yzt.tacos.data;

import com.yzt.tacos.Order;

public interface OrderRepository {

    Order save(Order order);
}
