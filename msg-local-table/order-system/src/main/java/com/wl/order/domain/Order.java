package com.wl.order.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 南顾北衫
 * @description
 * @date 2022/4/21
 */
@Data
public class Order {
    private Long id;
    private BigDecimal total;
    private Date createTime;
}
