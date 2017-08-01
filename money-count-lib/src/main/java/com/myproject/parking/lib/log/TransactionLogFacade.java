package com.myproject.parking.lib.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.transaction.annotation.Transactional;

import com.myproject.parking.lib.entity.TransactionVO;
import com.myproject.parking.lib.mapper.TransactionMapper;

public class TransactionLogFacade {
	private static final Logger LOG = LoggerFactory.getLogger(TransactionLogFacade.class);

	@Autowired
	private TransactionMapper transactionMapper;
	
	@Autowired
	private MessageSource messageSource;
	
	@Transactional(rollbackFor=Exception.class)
	public void createLog(TransactionVO task) throws Exception {
		LOG.info("[#{}] createLog: {}", task.getOrderId(), task);
		transactionMapper.createTransaction(task);
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void updateLog(TransactionVO task) throws Exception {
		LOG.info("[#{}] updateLog: {}", task.getOrderId(), task);
		transactionMapper.updateTransactionStatus(task.getFraudStatus(),task.getTransactionStatus(), task.getApprovalCode(), task.getTransactionId()
				, task.getSignatureKey(),task.getBank(),task.getPaymentType(),task.getOrderId(),task.getUpdatedBy(),task.getUpdatedOn());
	}
	
}
