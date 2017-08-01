package com.myproject.parking.lib.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.myproject.parking.lib.entity.TransactionVO;

public interface TransactionMapper {
	public void createTransaction(TransactionVO transactionVO);
	public void updateTransactionStatus(@Param("fraudStatus") String fraudStatus,@Param("transactionStatus") String transactionStatus
			,@Param("approvalCode") String approvalCode
			,@Param("transactionId") String transactionId,@Param("signatureKey") String signatureKey,@Param("bank") String bank
			,@Param("paymentType") String paymentType,@Param("orderId") String orderId,@Param("updatedBy") String updatedBy
			,@Param("updatedOn") Date updatedOn);
	public void updateEmailNotification(@Param("emailNotification") int emailNotification,@Param("emailNotificationReason") String emailNotificationReason,
			@Param("orderId") String orderId);
    
}
