/*package com.myproject.parking.trx.payment;

import java.util.ArrayList;
import java.util.List;

import id.co.veritrans.mdk.v1.exception.RestClientException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

public class ChargeProcess implements BaseQueryLogic {
	private static final Logger LOG = LoggerFactory.getLogger(ChargeProcess.class);
			
	@Autowired
	private CheckUserService checkUserService;
	
	@Autowired
	private VeriTransManagerService veriTransManagerService;
	
	@Autowired
	private AppsTimeService timeServer;
	
	@Override
	public String process(HttpServletRequest request,HttpServletResponse response,String data, ObjectMapper mapper, String pathInfo) {
		LOG.debug("ChargeProcess :"+pathInfo);		
		String result = "";
		ResponseVO responseVO = new ResponseVO();
		try {	
			if(StringUtils.isEmpty(data)){
				data = getDataMockup(data,mapper);
			}
			MidTransVO midTransVO = new MidTransVO(); 
			midTransVO = mapper.readValue(data, MidTransVO.class);
			responseVO = veriTransManagerService.chargeMidtrans(mapper,midTransVO,data);
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
		MidTransVO midTransVO = new MidTransVO(); 
		// mandatory
		TransactionDetails transactionDetails = new TransactionDetails();
		transactionDetails.setOrderId(CommonUtil.generateAlphaNumeric(10));
		transactionDetails.setGrossAmount(25000);
		// optional
		ItemDetail itemDetail = new ItemDetail();
		itemDetail.setId("1");
		itemDetail.setName("Lantai 1 B3");
		itemDetail.setPrice(25000);
		itemDetail.setQuantity(1);
		List<ItemDetail> listItemDetails = new ArrayList<ItemDetail>();
		listItemDetails.add(itemDetail);
		// optional
		CreditCard creditCard = new CreditCard();
		creditCard.setSaveCard(false);
		creditCard.setSecure(false);
		// optional
		CustomerDetails customerDetails = new CustomerDetails();
		customerDetails.setEmail("vincentius.yohanes86@gmail.com");
		customerDetails.setFirstName("YOHANES");
		customerDetails.setPhone("081299004785");
		
		midTransVO.setSessionKey("08129900478576H699JN4AMLBM6IE0EFETX5YE248Z");
		midTransVO.setTransactionDetails(transactionDetails);
		midTransVO.setItemDetails(listItemDetails);
		midTransVO.setCreditCard(creditCard);
		midTransVO.setCustomerDetails(customerDetails);
		data = mapper.writeValueAsString(midTransVO);
		
		return data;
	}
}
*/