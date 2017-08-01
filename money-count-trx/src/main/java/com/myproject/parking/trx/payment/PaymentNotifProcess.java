/*package com.myproject.parking.trx.payment;

import java.util.ArrayList;
import java.util.List;

import id.co.veritrans.mdk.v1.exception.RestClientException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myproject.parking.lib.data.PaymentNotifVO;
import com.myproject.parking.lib.data.ResponseVO;
import com.myproject.parking.lib.entity.CreditCard;
import com.myproject.parking.lib.entity.CustomerDetails;
import com.myproject.parking.lib.entity.ItemDetail;
import com.myproject.parking.lib.entity.MidTransVO;
import com.myproject.parking.lib.entity.TransactionDetails;
import com.myproject.parking.lib.service.AppsTimeService;
import com.myproject.parking.lib.service.CheckUserService;
import com.myproject.parking.lib.service.ParkingEngineException;
import com.myproject.parking.lib.service.VeriTransManagerService;
import com.myproject.parking.lib.utils.CommonUtil;
import com.myproject.parking.lib.utils.MessageUtils;
import com.myproject.parking.trx.logic.BaseQueryLogic;

public class PaymentNotifProcess implements BaseQueryLogic {
	private static final Logger LOG = LoggerFactory.getLogger(PaymentNotifProcess.class);
			
	@Autowired
	private CheckUserService checkUserService;
	
	@Autowired
	private VeriTransManagerService veriTransManagerService;
	
	@Autowired
	private AppsTimeService timeServer;
	
	@Override
	public String process(HttpServletRequest request,HttpServletResponse response,String data, ObjectMapper mapper, String pathInfo) {
		data = StringEscapeUtils.unescapeJava(data);
//		data = data.substring(1, data.length()-1);
		LOG.debug("PaymentNotifProcess :"+pathInfo + " data : " + data);		
		String result = "";
		ResponseVO responseVO = new ResponseVO();
		try {		
			if(StringUtils.isEmpty(data)){
				data = getDataMockup(data,mapper);
			}
			PaymentNotifVO paymentNotifVO = new PaymentNotifVO(); 
			paymentNotifVO = mapper.readValue(data, PaymentNotifVO.class);
			responseVO = veriTransManagerService.checkingPaymentNotif(mapper,paymentNotifVO);
			result = mapper.writeValueAsString(responseVO);
		} catch (ParkingEngineException e) {
			result = MessageUtils.handleException(e, "", mapper);
			LOG.error("ParkingEngineException when processing " + pathInfo + " Error Message " + result);
		} catch (RestClientException e) {			
			result = MessageUtils.handleException(e, "RestClientException when processing "+ e.getMessage(), mapper);
			LOG.error("RestClientException when processing " + pathInfo + " Error Message " + result);
		} catch (Exception e) {			
			result = MessageUtils.handleException(e, "Unexpected exception when processing "+ e.getMessage(), mapper);
			LOG.error("Unexpected exception when processing " + pathInfo + " Error Message " + result);
		}
		return result;
	}
	
	private String getDataMockup(String data,ObjectMapper mapper) throws JsonProcessingException{
		PaymentNotifVO paymentNotifVO = new PaymentNotifVO(); 
		paymentNotifVO.setMaskedCard("481111-1114");
		paymentNotifVO.setApprovalCode("1497348404195");
		paymentNotifVO.setBank("mandiri");
		paymentNotifVO.setTransactionTime("2017-06-13 17:06:43");
		paymentNotifVO.setGrossAmount("20000.00");
		paymentNotifVO.setOrderId("1497348346935");
		paymentNotifVO.setPaymentType("credit_card");
		paymentNotifVO.setSignatureKey("8dc5240d30fd83a091fa5a574db6aae9ef5657c3ae54ab7afc743635efd3e06730c1b9c3315073bbeacab1a4c75e2879d7359f622d9effd5b042c240fe629156");
		paymentNotifVO.setStatusCode("200");
		paymentNotifVO.setTransactionId("5730e26f-bfb9-422a-8f38-2136c6e31ec3");
		paymentNotifVO.setTransactionStatus("capture");
		paymentNotifVO.setFraudStatus("accept");
		paymentNotifVO.setStatusMessage("Veritrans payment notification");
		{
		 * ini kiriman dari payment notif
			  "masked_card": "481111-1114",
			  "approval_code": "1497348404195",
			  "bank": "mandiri",
			  "transaction_time": "2017-06-13 17:06:43",
			  "gross_amount": "20000.00",
			  "order_id": "1497348346935",
			  "payment_type": "credit_card",
			  "signature_key": "8dc5240d30fd83a091fa5a574db6aae9ef5657c3ae54ab7afc743635efd3e06730c1b9c3315073bbeacab1a4c75e2879d7359f622d9effd5b042c240fe629156",
			  "status_code": "200",
			  "transaction_id": "5730e26f-bfb9-422a-8f38-2136c6e31ec3",
			  "transaction_status": "capture",
			  "fraud_status": "accept",
			  "status_message": "Veritrans payment notification"
			}
		
		{
			  "status_code" : "200",
			  "status_message" : "Success, transaction found",
			  "transaction_id" : "249fc620-6017-4540-af7c-5a1c25788f46",
			  "masked_card" : "481111-1114",
			  "order_id" : "example-1424936368",
			  "payment_type" : "credit_card",
			  "transaction_time" : "2015-02-26 14:39:33",
			  "transaction_status" : "capture",
			  "fraud_status" : "accept",
			  "approval_code" : "1424936374393",
			  "signature_key" : "2802a264cb978fbc59f631c68d120cbda8dc853f5dfdc52301c615cf4f14e7a0b09aa...",
			  "bank" : "bni",
			  "gross_amount" : "30000.00"
			}
		data = mapper.writeValueAsString(paymentNotifVO);
		
		return data;
	}
	
}
*/