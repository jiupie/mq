package com.wl.order.web;

import com.wl.order.model.entity.OmsOrder;
import com.wl.order.service.OmsOrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Random;

/**
 * @author 南顾北衫
 * @email 17674641491@163.com
 * @date 2023/8/4 11:03
 */
@RequestMapping("/order")
@RestController
public class OrderController {
    @Resource
    private OmsOrderService omsOrderService;

    @PostMapping
    public void orderProcess() {
        OmsOrder omsOrder = new OmsOrder();
        Random random = new Random();
        omsOrder.setId(System.currentTimeMillis() + random.nextLong());
        omsOrder.setMemberId(random.nextLong());
        omsOrder.setTotalAmount(BigDecimal.valueOf(random.nextDouble()));
        omsOrder.setReceiverPhone(String.valueOf(random.nextLong()));
        omsOrder.setReceiverName(String.valueOf(random.nextLong()));
        omsOrder.setReceiverDetailAddress(String.valueOf(random.nextInt()));
        omsOrderService.createOrder(omsOrder);
    }
}
