package com.wl.mq.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 南顾北衫
 * @email 17674641491@163.com
 */
@Data
public class OrderDTO implements Serializable {
    private Long id;
    private BigDecimal money;
    private Integer total;
}
