package com.myproject.parking.lib.service;

import java.text.SimpleDateFormat;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppsMonitoringService {
	private static final Logger LOG = LoggerFactory.getLogger(AppsMonitoringService.class);
	
	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	
	@Autowired
	private AppsTimeService timeService;
	
//	@Autowired
//	private SettingService settingService;
	
	private AtomicInteger counterActive = new AtomicInteger(0);
	
	private AtomicInteger counterTotal = new AtomicInteger(0);
	
	public int addActiveTrx() {
		counterTotal.incrementAndGet();
		return counterActive.incrementAndGet();
	}
	public int removeActiveTrx() {
		return counterActive.decrementAndGet();
	}
	public int currentActiveTrx() {
		return counterActive.get();
	}
	
	/**
	 * This function reports when saving or creating log is failed
	 * @param task
	 */
//	public void reportFailedLog(TransactionVO task, String errorMessage) {
//		LOG.debug("[#{}] reportFailedLog: {}", task.getMsgLogNo(), task);
//		Date errorDate = timeService.getCurrentTime();
//		String subject = "[ENGINE] Log Error on Trx MsgLogNo #" + task.getMsgLogNo();
//		String message = "Failed Log Trx \n<br />" +
//				"MsgLogNo    : " + task.getMsgLogNo() + "\n<br />" +
//				"PhoneNo     : " + task.getPhoneNo() + "\n<br />" +
//				"TrxCode     : " + task.getTrxCode() + "\n<br />" +
//				"DateTime    : " + sdf.format(errorDate) + "\n<br />" +
//				"ErrorMsg    : " + errorMessage + "\n<br />" +
//				"Transaction : " + task.toString() + "\n<br />";
////		String dest = settingService.getSettingDefaultAsString(
////				SettingService.SETTING_EMAIL_FOR_WARNING);
//		String dest = "agusdk2011@gmail.com,vincentius.yohanes86@gmail.com";
//		String[] listDest = dest.split(",");
//		emailUtil.sendEmail(listDest, subject, message);
//	}
	
	/**
	 * This function check TransactionTO task if its process time exceeds threshold
	 * (or has slow response)
	 * @param task
	 */
//	public void checkTrxProcessTime(TransactionVO task) {
//		int threshold = settingService.getSettingDefaultAsInt(
//				SettingService.SETTING_PROCESS_TIME_THRESHOLD);
//		long elapsed = task.getSentTime() - task.getReceivedTime();
//		if (elapsed > threshold) {
//			LOG.warn("[#{}] Received on {}, Sent on {}, Elapsed {}ms > Threshold {}ms",
//					task.getMsgLogNo(), sdf.format(task.getReceivedTime()),
//					sdf.format(task.getSentTime()), elapsed, threshold);
//			Date currentDate = timeService.getCurrentTime();
//			String subject = "[ENGINE] Slow Response on Trx MsgLogNo #" + task.getMsgLogNo();
//			String message = "Transaction has slow response \n<br />" +
//					"MsgLogNo    : " + task.getMsgLogNo() + "\n<br />" +
//					"PhoneNo     : " + task.getPhoneNo() + "\n<br />" +
//					"TrxCode     : " + task.getTrxCode() + "\n<br />" +
//					"Reported    : " + sdf.format(currentDate) + "\n<br />" +
//					"Received    : " + sdf.format(task.getReceivedTime()) + "\n<br />" +
//					"Sent        : " + sdf.format(task.getSentTime()) + "\n<br />";
//			if (task.getBtiArrivedTime() > 0) {
//				Date d = new Date(task.getBtiArrivedTime());
//				message = message + 
//					"BTI entried : " + sdf.format(d) + "\n<br />" +
//					"BTI RC      : " + task.getBtiRc() + "\n<br />" +
//					"BTI process : " + task.getBtiProcess()+ "\n<br />";
//			} else {
//				message = message + 
//					"BTI process : N/A" + "\n<br />";
//			}
//			message = message + "Transaction : " + task.toString() + "\n<br />";
//			String dest = settingService.getSettingDefaultAsString(
//					SettingService.SETTING_EMAIL_FOR_WARNING);
//			String[] listDest = dest.split(",");
//			emailUtil.sendEmail(listDest, subject, message);
//		}  // end if elapsed > threshold
//	}
	
	public AtomicInteger getCounterActive() {
		return counterActive;
	}
	
	public int getUpdateCounterTotal() {
		return counterTotal.getAndSet(0);
	}
	
}
