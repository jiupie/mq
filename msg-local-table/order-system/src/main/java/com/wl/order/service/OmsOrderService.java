package com.wl.order.service;

import com.wl.order.model.entity.OmsOrder;

/**
 * @author yangy
 * @description 针对表【oms_order】的数据库操作Service
 * @createDate 2023-08-04 11:29:27
 */
public interface OmsOrderService {
    void createOrder(OmsOrder omsOrder);
}
