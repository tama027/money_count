/*package com.myproject.parking.trx.payment;

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
import com.myproject.parking.lib.data.ConfirmVO;
import com.myproject.parking.lib.data.MessageVO;
import com.myproject.parking.lib.data.PaymentNotifVO;
import com.myproject.parking.lib.data.ResponseVO;
import com.myproject.parking.lib.service.AppsTimeService;
import com.myproject.parking.lib.service.CheckUserService;
import com.myproject.parking.lib.service.ParkingEngineException;
import com.myproject.parking.lib.service.VeriTransManagerService;
import com.myproject.parking.lib.utils.MessageUtils;
import com.myproject.parking.trx.logic.BaseQueryLogic;

public class ConfirmProcess implements BaseQueryLogic {
	private static final Logger LOG = LoggerFactory.getLogger(ConfirmProcess.class);
			
	@Autowired
	private CheckUserService checkUserService;
	
	@Autowired
	private VeriTransManagerService veriTransManagerService;
	
	@Autowired
	private AppsTimeService timeServer;
	
	@Override
	public String process(HttpServletRequest request,HttpServletResponse response,String data, ObjectMapper mapper, String pathInfo) {
		LOG.debug("ConfirmProcess :"+pathInfo + " data : " + data);		
		String result = "";
		try {		
			if(StringUtils.isEmpty(data)){
				data = getDataMockup(data,mapper);
			}
			ConfirmVO confirmVO = new ConfirmVO(); 
			confirmVO = mapper.readValue(data, ConfirmVO.class);
			veriTransManagerService.confirmTrx(mapper,confirmVO);			
			result = MessageUtils.handleSuccess("Confirmation transaction success", mapper);
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
		ConfirmVO confirmVO = new ConfirmVO(); 
		confirmVO.setEmail("agusdk2011@gmail.com");
		confirmVO.setMaskedCard("481111-1114");
		confirmVO.setApprovalCode("1497348404195");
		confirmVO.setBank("mandiri");
		confirmVO.setTransactionTime("2017-06-13 17:06:43");
		confirmVO.setGrossAmount("20000.00");
		confirmVO.setOrderId("1497512977873");
		confirmVO.setPaymentType("credit_card");
		confirmVO.setSignatureKey("8dc5240d30fd83a091fa5a574db6aae9ef5657c3ae54ab7afc743635efd3e06730c1b9c3315073bbeacab1a4c75e2879d7359f622d9effd5b042c240fe629156");
		confirmVO.setStatusCode("200");
		confirmVO.setTransactionId("5730e26f-bfb9-422a-8f38-2136c6e31ec3");
		confirmVO.setTransactionStatus("capture");
		confirmVO.setFraudStatus("accept");
		confirmVO.setSessionKey("08562323598V2LNM0OQ1G7WZ6B5GZ6EVGN3JHBIMX");
		confirmVO.setName("AGUS DARMA KUSUMA");
		confirmVO.setStatusMessage("Veritrans payment notification");
		data = mapper.writeValueAsString(confirmVO);
		
		return data;
	}
	
}
*/