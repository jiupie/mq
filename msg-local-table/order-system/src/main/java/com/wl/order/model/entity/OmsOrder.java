package com.wl.order.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * @TableName oms_order
 */
@Data
public class OmsOrder implements Serializable {
    /**
     * 订单id
     */
    private Long id;

    /**
     * 会员id
     */
    private Long memberId;

    /**
     * 订单总金额
     */
    private BigDecimal totalAmount;

    /**
     * 收货人姓名
     */
    private String receiverName;

    /**
     * 收货人电话
     */
    private String receiverPhone;

    /**
     * 详细地址
     */
    private String receiverDetailAddress;

    private static final long serialVersionUID = 1L;
}