package com.werner.pojo;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bill {
	
	private Integer id;
	private String billCode;
	private String productName;
	private String productDesc;
	private String productUnit;
	private BigDecimal productCount;
	private BigDecimal totalPrice;
	private Integer isPayment;
	private Integer providerld;	
	private Integer createdBy;
	private Date creationDate;
	private Integer modifyBy;
	private Date modifyDate;
	
}
