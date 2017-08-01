package com.myproject.parking.lib.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.myproject.parking.lib.entity.TransactionNotifVO;
import com.myproject.parking.lib.entity.TransactionVO;
import com.myproject.parking.lib.log.TransactionLogFacade;
import com.myproject.parking.lib.log.TransactionNotifLogFacade;
import com.myproject.parking.lib.log.TrxLogAsyncAgent;
import com.myproject.parking.lib.log.TrxLogNotifAsyncAgent;
import com.myproject.parking.lib.service.AppsMonitoringService;



public class DatabaseAsyncUtil {
	private static final Logger LOG = LoggerFactory.getLogger(DatabaseAsyncUtil.class);

	private int agentCount = 2;
	private ExecutorService es;
		
	@Autowired
	private TransactionLogFacade transactionLogFacade;
	
	@Autowired
	private TransactionNotifLogFacade transactionNotifLogFacade;
	
	@Autowired
	private AppsMonitoringService monitoringService;
	
	public void start() {
		LOG.info("Starting DatabaseAsyncUtil with {} agent(s)", agentCount);
		es = Executors.newFixedThreadPool(agentCount);
	}
	
	public void stop() {
		LOG.info("Stopping DatabaseAsyncUtil");
		try {
			es.shutdown();
			es.awaitTermination(5, TimeUnit.SECONDS);
		} catch (InterruptedException e) {}
	}
	
	public void logTransaction(TransactionVO task) {
		LOG.debug("[#{}] logTransaction: {}", task.getOrderId(), task);
		TrxLogAsyncAgent agent = new TrxLogAsyncAgent(transactionLogFacade, task);
		es.execute(agent);
	}
	
	public void logTransactionNotif(TransactionNotifVO task) {
		LOG.debug("[#{}] logTransactionNotif: {}", task.getOrderId(), task);
		TrxLogNotifAsyncAgent agent = new TrxLogNotifAsyncAgent(transactionNotifLogFacade, task);
		es.execute(agent);
	}

	public void setAgentCount(int agentCount) {
		this.agentCount = agentCount;
	} 
}
