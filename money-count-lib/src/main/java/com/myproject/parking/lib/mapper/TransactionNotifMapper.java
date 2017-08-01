package com.myproject.parking.lib.mapper;

import org.apache.ibatis.annotations.Param;

import com.myproject.parking.lib.entity.TransactionNotifVO;

public interface TransactionNotifMapper {
	public void createTransactionNotif(TransactionNotifVO transactionNotifVO);
	public String findTransactionNotifByOrderId(@Param("orderId") String orderId);
}
