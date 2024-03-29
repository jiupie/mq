package com.wl.mq.model.domain;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 南顾北衫
 * @email 17674641491@163.com
 */
@Data
public class Order implements Serializable {
    private Long id;
    private String name;
    private String address;
    private String phone;
    private BigDecimal money;
}
