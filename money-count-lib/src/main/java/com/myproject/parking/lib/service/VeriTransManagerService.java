package com.myproject.parking.lib.service;

import id.co.veritrans.mdk.v1.VtGatewayConfig;
import id.co.veritrans.mdk.v1.VtGatewayConfigBuilder;
import id.co.veritrans.mdk.v1.VtGatewayFactory;
import id.co.veritrans.mdk.v1.config.EnvironmentType;
import id.co.veritrans.mdk.v1.exception.RestClientException;
import id.co.veritrans.mdk.v1.gateway.VtDirect;
import id.co.veritrans.mdk.v1.gateway.model.AbstractVtRequest;
import id.co.veritrans.mdk.v1.gateway.model.Address;
import id.co.veritrans.mdk.v1.gateway.model.CustomerDetails;
import id.co.veritrans.mdk.v1.gateway.model.TransactionDetails;
import id.co.veritrans.mdk.v1.gateway.model.TransactionItem;
import id.co.veritrans.mdk.v1.gateway.model.VtResponse;
import id.co.veritrans.mdk.v1.gateway.model.builder.CreditCardBuilder;
import id.co.veritrans.mdk.v1.gateway.model.vtdirect.CreditCardRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myproject.parking.lib.data.CheckStatusVO;
import com.myproject.parking.lib.data.ConfirmVO;
import com.myproject.parking.lib.data.MessageVO;
import com.myproject.parking.lib.data.PaymentNotifVO;
import com.myproject.parking.lib.data.Product;
import com.myproject.parking.lib.data.ResponseVO;
import com.myproject.parking.lib.data.VeriTransVO;
import com.myproject.parking.lib.entity.Booking;
import com.myproject.parking.lib.entity.MidTransVO;
import com.myproject.parking.lib.entity.TransactionNotifVO;
import com.myproject.parking.lib.entity.TransactionVO;
import com.myproject.parking.lib.entity.UserData;
import com.myproject.parking.lib.mapper.BookingMapper;
import com.myproject.parking.lib.mapper.TransactionDetailMapper;
import com.myproject.parking.lib.mapper.TransactionMapper;
import com.myproject.parking.lib.mapper.UserDataMapper;
import com.myproject.parking.lib.utils.CommonUtil;
import com.myproject.parking.lib.utils.Constants;
import com.myproject.parking.lib.utils.DatabaseAsyncUtil;
import com.myproject.parking.lib.utils.EmailSender;

@Service
public class VeriTransManagerService {

	private static final Logger LOG = LoggerFactory.getLogger(VeriTransManagerService.class);
	
	@Autowired
	private UserDataMapper userDataMapper;
	
	@Autowired
	private TransactionMapper transactionMapper;
	
	@Autowired
	private TransactionDetailMapper transactionDetailMapper;
	
	@Autowired
	private BookingMapper bookingMapper;
	
	@Autowired
	private AppsTimeService timeService;
	
	@Autowired
	private HttpClientService httpClientService;
	
	@Autowired
	private EmailSender emailSender;
	
	/*@Autowired
	private CheckSessionKeyService checkSessionKeyService;
	*/
	@Autowired
	private DatabaseAsyncUtil databaseAsyncUtil;
	
	private String clientKey;
	private String serverKey;
	private String environment;
    private int connectionPoolSize;
    private String differentField;

	public String getClientKey() {
		return clientKey;
	}

	public void setClientKey(String clientKey) {
		this.clientKey = clientKey;
	}

	public String getServerKey() {
		return serverKey;
	}

	public void setServerKey(String serverKey) {
		this.serverKey = serverKey;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public int getConnectionPoolSize() {
		return connectionPoolSize;
	}

	public void setConnectionPoolSize(int connectionPoolSize) {
		this.connectionPoolSize = connectionPoolSize;
	}

	public VtGatewayFactory getVtGatewayFactory() {
		VtGatewayConfigBuilder vtGatewayConfigBuilder = new VtGatewayConfigBuilder();
		vtGatewayConfigBuilder.setServerKey(serverKey);
		vtGatewayConfigBuilder.setClientKey(clientKey);
		vtGatewayConfigBuilder.setEnvironmentType(EnvironmentType.valueOf(environment));
		vtGatewayConfigBuilder.setMaxConnectionPoolSize(connectionPoolSize);
		VtGatewayConfig vtGatewayConfig = vtGatewayConfigBuilder.createVtGatewayConfig();
		return new VtGatewayFactory(vtGatewayConfig);
	}
	
	@Transactional(rollbackFor={Exception.class})
	public TransactionVO charge(String vtToken,TransactionVO transactionVO,VeriTransVO veriTransVO) throws ParkingEngineException,RestClientException {
		/*LOG.debug(" Process charge to veritrans with token: "+vtToken + " email : " + veriTransVO.getEmail());
		LOG.debug(" Parameter VeriTransVO: "+veriTransVO);
		
		if(user == null){
			LOG.error("Can't find User with email : " + veriTransVO.getEmail());
			throw new ParkingEngineException(ParkingEngineException.ENGINE_USER_NOT_FOUND);
		}*/
		/*if(Constants.BLOCKED == user.getStatus()){
			LOG.error("User already blocked");
			throw new ParkingEngineException(ParkingEngineException.ENGINE_USER_BLOCKED);
		}
		if(Constants.PENDING == user.getStatus()){
			LOG.error("User not active");
			throw new ParkingEngineException(ParkingEngineException.ENGINE_USER_NOT_ACTIVE);
		}		*/
		/*if(StringUtils.isEmpty(user.getSessionKey())){
			LOG.error("User Must Login Before make transaction, Parameter email : " + veriTransVO.getEmail());
			throw new ParkingEngineException(ParkingEngineException.ENGINE_USER_NOT_LOGIN);
		}
		if(!user.getSessionKey().equals(veriTransVO.getSessionKey())){
			LOG.error("Wrong Session Key, Parameter email : " + veriTransVO.getEmail());
			throw new ParkingEngineException(ParkingEngineException.ENGINE_SESSION_KEY_DIFFERENT);
		}*/
		//checkSessionKeyService.checkSessionKey(user.getTimeGenSessionKey(), veriTransVO.getEmail());
		try {
			final CreditCardRequest request = createCreditCardRequest(vtToken,veriTransVO);
//			transactionVO = createTransaction(veriTransVO);
			VtDirect vtDirect = getVtGatewayFactory().vtDirect();			
            final VtResponse vtResponse = vtDirect.charge(request);            
//            transactionVO.setPaymentTransactionId(vtResponse.getTransactionId());
//            transactionVO.setPaymentFdsStatus(vtResponse.getFraudStatus() == null ? null : vtResponse.getFraudStatus().name());
//            transactionVO.setPaymentStatus(vtResponse.getTransactionStatus() == null ? null : vtResponse.getTransactionStatus().name());
            
            /*LOG.debug(" Response veritrans : " +vtResponse.getStatusCode() + " Error Message : " + vtResponse.getStatusMessage() 
            		+ " with token: "+vtToken + " email : " + veriTransVO.getEmail());*/
            if (!vtResponse.getStatusCode().equals("200")) {
            	throw new ParkingEngineException(ParkingEngineException.VERITRANS_CHARGE_FAILED,vtResponse.getStatusMessage());
            }        
//            transactionMapper.updateTransactionStatus(transactionVO.getPaymentTransactionId(), 
//            		transactionVO.getPaymentFdsStatus(), transactionVO.getPaymentStatus(), transactionVO.getPaymentOrderId());
           // Booking booking = bookingMapper.findBookingByBookingId(veriTransVO.getBookingId());
            Booking booking = new Booking() ; //not used
//            transactionVO.setBookingCode(booking.getBookingCode());// get booking code from booking id
            booking.setBookingStatus(Constants.STATUS_ALREADY_PAY);
            //bookingMapper.updateBookingStatus(booking);            
		} catch (RestClientException e) {
			throw e;
		}
		LOG.debug(" Parameter TransactionVO: "+transactionVO);
		LOG.debug(" Try send email notification to customer ");
//		try {
//			sendNotificationToCustomerViaEmail(user, transactionVO, veriTransVO);
//			transactionMapper.updateEmailNotification(Constants.EMAIL_NOTIF_SENT,Constants.EMAIL_REASON_SUCCESS, transactionVO.getPaymentOrderId());
//			LOG.debug(" Send email notification success ");
//		} catch (EmailException e) {
//			transactionMapper.updateEmailNotification(Constants.EMAIL_NOTIF_FAILED,e.getMessage(), transactionVO.getPaymentOrderId());
//			LOG.error(" Send email notification failed " + e.getMessage());
//		} catch (IOException e) {
//			transactionMapper.updateEmailNotification(Constants.EMAIL_NOTIF_FAILED,e.getMessage(), transactionVO.getPaymentOrderId());
//			LOG.error(" Send email notification failed " + e.getMessage());
//		} catch (Exception e) {
//			transactionMapper.updateEmailNotification(Constants.EMAIL_NOTIF_FAILED,e.getMessage(), transactionVO.getPaymentOrderId());
//			LOG.error(" Send email notification failed " + e.getMessage());
//		}
		return transactionVO;
	}
	
	public ResponseVO chargeMidtrans(ObjectMapper mapper,MidTransVO midTransVO,String data) throws ParkingEngineException,Exception {
		Date now = new Date();
		ResponseVO responseVO = new ResponseVO(); 
		LOG.debug(" chargeMidtrans: "+midTransVO) ;
		UserData user = userDataMapper.findUserDataByEmail(midTransVO.getCustomerDetails().getEmail());
		if(user == null){
			LOG.error("Can't find User with email : " + midTransVO.getCustomerDetails().getEmail());
			throw new ParkingEngineException(ParkingEngineException.ENGINE_USER_NOT_FOUND);
		}
		if(Constants.BLOCKED == user.getStatus()){
			LOG.error("User already blocked");
			throw new ParkingEngineException(ParkingEngineException.ENGINE_USER_BLOCKED);
		}
		if(Constants.PENDING == user.getStatus()){
			LOG.error("User not active");
			throw new ParkingEngineException(ParkingEngineException.ENGINE_USER_NOT_ACTIVE);
		}		
		if(StringUtils.isEmpty(user.getSessionKey())){
			LOG.error("User Must Login Before make transaction, Parameter email : " + midTransVO.getCustomerDetails().getEmail());
			throw new ParkingEngineException(ParkingEngineException.ENGINE_USER_NOT_LOGIN);
		}
		if(!user.getSessionKey().equals(midTransVO.getSessionKey())){
			LOG.error("Wrong Session Key, Parameter email : " + midTransVO.getCustomerDetails().getEmail());
			throw new ParkingEngineException(ParkingEngineException.ENGINE_SESSION_KEY_DIFFERENT);
		}
		//checkSessionKeyService.checkSessionKey(user.getTimeGenSessionKey(), midTransVO.getCustomerDetails().getEmail());
		// save to database here
		TransactionVO task = new TransactionVO();
		task.setCustomerFirstName(midTransVO.getCustomerDetails().getFirstName());
		task.setOrderId(midTransVO.getTransactionDetails().getOrderId());
		task.setNameItem(midTransVO.getItemDetails().get(0).getName());
		task.setTotalPriceIdr(new Long(midTransVO.getItemDetails().get(0).getQuantity()*midTransVO.getItemDetails().get(0).getPrice()));
		task.setUpdate(false);
		task.setCreatedBy(midTransVO.getCustomerDetails().getFirstName());
		task.setCreatedOn(now);
		task.setUpdatedBy(midTransVO.getCustomerDetails().getFirstName());
		task.setUpdatedOn(now);
		databaseAsyncUtil.logTransaction(task);
		// save to database here
		try {
			String response = httpClientService.chargeMidtransPost(data);				
			responseVO = mapper.readValue(response, ResponseVO.class);
			LOG.debug("responseVO :"+responseVO);
			
		} catch (Exception e) {
			throw e;
		}
		
		return responseVO;
	}
	
	public ResponseVO checkingPaymentNotif(ObjectMapper mapper,PaymentNotifVO paymentNotifVO) throws ParkingEngineException,Exception {
		LOG.debug("checkingPaymentNotif :"+paymentNotifVO);
		Date now = new Date();
		ResponseVO responseVO = new ResponseVO(); 
		CheckStatusVO checkStatusVO = new CheckStatusVO();	
		differentField = "";
		try {
			String response = httpClientService.getTransactionStatus(paymentNotifVO.getOrderId());				
			checkStatusVO = mapper.readValue(response, CheckStatusVO.class);
			LOG.debug("checkStatusVO :"+checkStatusVO);
			if(checkValidTransaction(paymentNotifVO, checkStatusVO)){
	            // create table transaction notif
	            TransactionNotifVO task = new TransactionNotifVO();
	    		task.setFraudStatus(paymentNotifVO.getFraudStatus());
	    		task.setTransactionStatus(paymentNotifVO.getTransactionStatus());
	    		task.setTransactionDescription(paymentNotifVO.getTransactionStatus());
	    		task.setApprovalCode(paymentNotifVO.getApprovalCode());
	    		task.setTransactionId(paymentNotifVO.getTransactionId());
	    		task.setSignatureKey(paymentNotifVO.getSignatureKey());
	    		task.setBank(paymentNotifVO.getBank());
	    		task.setPaymentType(paymentNotifVO.getPaymentType());
	    		task.setOrderId(paymentNotifVO.getOrderId());
	    		task.setUpdatedBy(Constants.SYSTEM);
	    		task.setUpdatedOn(now);
	    		task.setCreatedBy(Constants.SYSTEM);
	    		task.setCreatedOn(now);
	    		LOG.debug("create transaction notif :"+task);
	    		databaseAsyncUtil.logTransactionNotif(task);	         
			}else{
				LOG.warn("transaksi tidak valid order id :"+ paymentNotifVO.getOrderId());
				// create table transaction notif
				TransactionNotifVO task = new TransactionNotifVO();	
				task.setFraudStatus(paymentNotifVO.getFraudStatus());
	    		task.setTransactionStatus(Constants.SUSPECT);
	    		task.setTransactionDescription(differentField);
	    		task.setApprovalCode(paymentNotifVO.getApprovalCode());
	    		task.setTransactionId(paymentNotifVO.getTransactionId());
	    		task.setSignatureKey(paymentNotifVO.getSignatureKey());
	    		task.setBank(paymentNotifVO.getBank());
	    		task.setPaymentType(paymentNotifVO.getPaymentType());
	    		task.setOrderId(paymentNotifVO.getOrderId());
	    		task.setUpdatedBy(Constants.SYSTEM);
	    		task.setUpdatedOn(now);
	    		task.setCreatedBy(Constants.SYSTEM);
	    		task.setCreatedOn(now);
	    		LOG.debug("create transaction notif :"+task);
	    		databaseAsyncUtil.logTransactionNotif(task);
			}
			
		} catch (Exception e) {
			throw e;
		}
		
		return responseVO;
	}
	
	private boolean checkValidTransaction(PaymentNotifVO paymentNotifVO,CheckStatusVO checkStatusVO){
		boolean result = true;
		if(!paymentNotifVO.getOrderId().equals(checkStatusVO.getOrderId())){
			result = false;
			differentField = "order id different";
			return result;
		}
		//Booking booking = bookingMapper.findBookingByBookingId(paymentNotifVO.getOrderId());
		Booking booking = new Booking();
		if(booking == null){
			result = false;
			differentField = "order id not valid";
			return result;
		}
		// fraud status
		if(!paymentNotifVO.getFraudStatus().equals(checkStatusVO.getFraudStatus())){
			result = false;
			differentField = "fraud status different";
			return result;
		}
		if(Constants.FRAUD_STATUS_DENY.equals(paymentNotifVO.getFraudStatus())||Constants.FRAUD_STATUS_CHALLENGE.equals(paymentNotifVO.getFraudStatus())){
			result = false;
			differentField = "fraud status deny or challange";
			return result;
		}
		// fraud status
		
		//transaction status
		if(!paymentNotifVO.getTransactionStatus().equals(checkStatusVO.getTransactionStatus())){
			result = false;
			differentField = "transaction status different";
			return result;
		}
		if(!Constants.TRANSACTION_STATUS_CAPTURE.equals(paymentNotifVO.getTransactionStatus())){
			result = false;
			differentField = "transaction status failed";
			return result;
		}
		//transaction status
		
		if(!paymentNotifVO.getSignatureKey().equals(checkStatusVO.getSignatureKey())){
			result = false;
			differentField = "transaction status signature key different";
			return result;
		}
		return result;
	}
	
	public void confirmTrx(ObjectMapper mapper,ConfirmVO confirmVO) throws ParkingEngineException,Exception {
		LOG.debug("confirmTrx :"+confirmVO);
		Date now = new Date();		
		//UserData user = userDataMapper.findUserDataByEmail(confirmVO.getEmail());
		/*if(user == null){
			LOG.error("Can't find User with email : " + confirmVO.getEmail());
			throw new ParkingEngineException(ParkingEngineException.ENGINE_USER_NOT_FOUND);
		}*/
		/*if(Constants.BLOCKED == user.getStatus()){
			LOG.error("User already blocked");
			throw new ParkingEngineException(ParkingEngineException.ENGINE_USER_BLOCKED);
		}
		if(Constants.PENDING == user.getStatus()){
			LOG.error("User not active");
			throw new ParkingEngineException(ParkingEngineException.ENGINE_USER_NOT_ACTIVE);
		}	*/	
		/*if(StringUtils.isEmpty(user.getSessionKey())){
			LOG.error("User Must Login Before make transaction, Parameter email : " + confirmVO.getEmail());
			throw new ParkingEngineException(ParkingEngineException.ENGINE_USER_NOT_LOGIN);
		}
		if(!user.getSessionKey().equals(confirmVO.getSessionKey())){
			LOG.error("Wrong Session Key, Parameter email : " + confirmVO.getEmail());
			throw new ParkingEngineException(ParkingEngineException.ENGINE_SESSION_KEY_DIFFERENT);
		}*/
		//checkSessionKeyService.checkSessionKey(user.getTimeGenSessionKey(), confirmVO.getEmail());
		try {
			if(checkValidTransaction(confirmVO)){
				// update booking menjadi payed
				//Booking booking = bookingMapper.findBookingByBookingId(confirmVO.getOrderId());
				Booking booking = new Booking();
				updateStatusBooking(booking, Constants.STATUS_ALREADY_PAY);
				
	            // update table transaction
	            TransactionVO task = new TransactionVO();
	    		task.setFraudStatus(confirmVO.getFraudStatus());
	    		task.setTransactionStatus(confirmVO.getTransactionStatus());
	    		task.setApprovalCode(confirmVO.getApprovalCode());
	    		task.setTransactionId(confirmVO.getTransactionId());
	    		task.setSignatureKey(confirmVO.getSignatureKey());
	    		task.setBank(confirmVO.getBank());
	    		task.setPaymentType(confirmVO.getPaymentType());
	    		task.setOrderId(confirmVO.getOrderId());
	    		//task.setUpdatedBy(confirmVO.getName());
	    		task.setUpdatedOn(now);
	    		//task.setCreatedBy(confirmVO.getName());
	    		task.setCreatedOn(now);
	    		task.setUpdate(true);
	    		LOG.debug("update table transaction :"+task);
	    		databaseAsyncUtil.logTransaction(task);	 
			}else{
				// no need action karena belum bayar
			}
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Transactional(rollbackFor={Exception.class})
	private void updateStatusBooking(Booking booking,int status){
		booking.setBookingStatus(status);
		//bookingMapper.updateBookingStatus(booking);
	}
	
	private boolean checkValidTransaction(ConfirmVO confirmVO){
		boolean result = true;
		// fraud status
		if(!Constants.FRAUD_STATUS_ACCEPT.equals(confirmVO.getFraudStatus())){
			result = false;
			differentField = "Fraud status deny or challange";
			return result;
		}
		// fraud status
		
		//transaction status
		if(!Constants.TRANSACTION_STATUS_CAPTURE.equals(confirmVO.getTransactionStatus())){
			result = false;
			differentField = "Transaction status failed";
			return result;
		}
		//transaction status
		return result;
	}
	
	protected void setVtRequestParam(final AbstractVtRequest vtRequest,VeriTransVO veriTransVO) {
        vtRequest.setCustomerDetails(toCustomerDetails(veriTransVO));

        vtRequest.setTransactionDetails(toTransactionDetails(veriTransVO));
        vtRequest.setItemDetails(toTransactionItems(veriTransVO));
    }
	
	protected CustomerDetails toCustomerDetails(VeriTransVO veriTransVO) {
        final CustomerDetails ret = new CustomerDetails();
        ret.setFirstName(veriTransVO.getCustomerDetail().getFirstName());
        ret.setLastName(veriTransVO.getCustomerDetail().getLastName());
        ret.setEmail(veriTransVO.getCustomerDetail().getEmail());
        ret.setPhone(veriTransVO.getCustomerDetail().getPhone());

        ret.setBillingAddress(new Address());
        ret.getBillingAddress().setAddress(veriTransVO.getCustomerDetail().getBillingAddress().getAddress());
        ret.getBillingAddress().setCity(veriTransVO.getCustomerDetail().getBillingAddress().getCity());
        ret.getBillingAddress().setCountryCode("IDN");
        ret.getBillingAddress().setFirstName(veriTransVO.getCustomerDetail().getBillingAddress().getFirstName());
        ret.getBillingAddress().setLastName(veriTransVO.getCustomerDetail().getBillingAddress().getLastName());
        ret.getBillingAddress().setPhone(veriTransVO.getCustomerDetail().getBillingAddress().getPhone());
        ret.getBillingAddress().setPostalCode(veriTransVO.getCustomerDetail().getBillingAddress().getPostalCode());

        ret.setShippingAddress(new Address());
        ret.getShippingAddress().setAddress(veriTransVO.getCustomerDetail().getShippingAddress().getAddress());
        ret.getShippingAddress().setCity(veriTransVO.getCustomerDetail().getShippingAddress().getCity());
        ret.getShippingAddress().setCountryCode("IDN");
        ret.getShippingAddress().setFirstName(veriTransVO.getCustomerDetail().getShippingAddress().getFirstName());
        ret.getShippingAddress().setLastName(veriTransVO.getCustomerDetail().getShippingAddress().getLastName());
        ret.getShippingAddress().setPhone(veriTransVO.getCustomerDetail().getShippingAddress().getPhone());
        ret.getShippingAddress().setPostalCode(veriTransVO.getCustomerDetail().getShippingAddress().getPostalCode());

        return ret;
    }
	
	protected TransactionDetails toTransactionDetails(VeriTransVO veriTransVO) {
        final TransactionDetails ret = new TransactionDetails();

        ret.setGrossAmount(veriTransVO.getTransactionDetails().getGrossAmount()); // bisa ditambah fee kita nantinya
        ret.setOrderId(UUID.randomUUID().toString());

        return ret;
    }
	
	protected List<TransactionItem> toTransactionItems(VeriTransVO veriTransVO) {
        final List<TransactionItem> ret = new ArrayList<TransactionItem>(veriTransVO.getListProducts().size());
        for (final Product product : veriTransVO.getListProducts()) {         
            ret.add(new TransactionItem(
                    product.getId().toString(),
                    product.getShortName(),
                    product.getPriceIdr(),
                    veriTransVO.getListProducts().size()
            ));
        }
        return ret;
    }
	
	protected CreditCardRequest createCreditCardRequest(final String vtToken,VeriTransVO veriTransVO) {
        final CreditCardRequest ret = new CreditCardRequest();
        setVtRequestParam(ret,veriTransVO);
        ret.setCreditCard(new CreditCardBuilder().createCreditCard());
        ret.getCreditCard().setCardToken(vtToken);
        return ret;
    }
	
	private void sendNotificationToCustomerViaEmail(UserData user, TransactionVO transactionVO,VeriTransVO veriTransVO)throws EmailException, IOException {
		String emailTo = user.getEmail();
		String emailSubject = Constants.APP_NAME+" : Informasi Pesanan Anda";
		String txtMessage = "";
		
		StringBuilder sb = new StringBuilder();
		sb.append("Dear Mr/Mrs ").append(user.getName()).append(",");
		sb.append("\n").append("\n");
		sb.append("Regards,");
		sb.append("\n").append("\n");
		sb.append("Administrator");
		txtMessage = sb.toString();

		String html = ""
				+ "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"> "
				+ "<html xmlns=\"http://www.w3.org/1999/xhtml\" style=\"font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"> "
				+ "<head> "
				+ "<meta name=\"viewport\" content=\"width=device-width\" /> "
				+ "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" /> "
				+ "<title>Informasi Pesanan Anda</title> "
				+ " "
				+ " "
				+ "<style type=\"text/css\"> "
				+ "img "
				+ "{max-width: 100%; "
				+ "} "
				+ "body "
				+ "{-webkit-font-smoothing: antialiased; -webkit-text-size-adjust: none; width: 100% !important; height: 100%; line-height: 1.6em; "
				+ "} "
				+ "body "
				+ "{background-color: #f6f6f6; "
				+ "} "
				+ "@media only screen and (max-width: 640px) "
				+ "{  body { "
				+ "    padding: 0 !important; "
				+ "  } "
				+ "  h1 "
				+ "{    font-weight: 800 !important; margin: 20px 0 5px !important; "
				+ "  } "
				+ "  h2 "
				+ "{    font-weight: 800 !important; margin: 20px 0 5px !important; "
				+ "  } "
				+ "  h3 "
				+ "{    font-weight: 800 !important; margin: 20px 0 5px !important; "
				+ "  } "
				+ "  h4 "
				+ "{    font-weight: 800 !important; margin: 20px 0 5px !important; "
				+ "  } "
				+ "  h1 "
				+ "{    font-size: 22px !important; "
				+ "  } "
				+ "  h2 "
				+ "{    font-size: 18px !important; "
				+ "  } "
				+ "  h3 "
				+ "{    font-size: 16px !important; "
				+ "  } "
				+ "  .container "
				+ "{    padding: 0 !important; width: 100% !important; "
				+ "  } "
				+ "  .content "
				+ "{    padding: 0 !important; "
				+ "  } "
				+ "  .content-wrap "
				+ "{    padding: 10px !important; "
				+ "  } "
				+ "  .invoice "
				+ "{    width: 100% !important; "
				+ "  } "
				+ "} "
				+ "</style> "
				+ "</head> "
				+ " "
				+ "<body itemscope itemtype=\"http://schema.org/EmailMessage\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; -webkit-font-smoothing: antialiased; -webkit-text-size-adjust: none; width: 100% !important; height: 100%; line-height: 1.6em; background-color: #f6f6f6; margin: 0;\" bgcolor=\"#f6f6f6\"> "
				+ " "
				+ "<table class=\"body-wrap\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; width: 100%; background-color: #f6f6f6; margin: 0;\" bgcolor=\"#f6f6f6\"><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0;\" valign=\"top\"></td> "
				+ "		<td class=\"container\" width=\"600\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; display: block !important; max-width: 600px !important; clear: both !important; margin: 0 auto;\" valign=\"top\"> "
				+ "			<div class=\"content\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; max-width: 600px; display: block; margin: 0 auto; padding: 20px;\"> "
				+ "				<table class=\"main\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" itemprop=\"action\" itemscope itemtype=\"http://schema.org/ConfirmAction\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; border-radius: 3px; background-color: #fff; margin: 0; border: 1px solid #e9e9e9;\" bgcolor=\"#fff\"><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"content-wrap\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 20px;\" valign=\"top\"> "
				+ "							<meta itemprop=\"name\" content=\"Confirm Email\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\" /><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"content-block\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 0 0 0px;\" valign=\"top\"> "
				+ "										Yth Bapak/Ibu " + user.getName()
				+ "									</td> "
				+ "								</tr><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"content-block\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 0 0 20px;\" valign=\"top\"> "
				+ "										Terima kasih telah menggunakan Premium Parkir Solution untuk memesan valet parkir "
				+ "									</td> "
				+ "								</tr><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"content-block\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 0 0 20px;\" valign=\"top\"> "
				+ "										Berikut adalah informasi pemesanan anda: "
				+ "									</td> "
				+ "								</tr><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"content-block\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 0 0 20px;\" valign=\"top\"> "
				+ "										<b>Nama customer</b> 			: " + user.getName()
				+ "									</td> "
				+ "								</tr><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"content-block\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 0 0 20px;\" valign=\"top\"> "
				+ "										<b>Booking Id</b>				: " + transactionVO.getOrderId()
				+ "									</td> "
				+ "								</tr><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"content-block\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 0 0 20px;\" valign=\"top\"> "
				+ "										<b>Booking Code</b>			: " + transactionVO.getOrderId()
				+ "									</td> "
				+ "								</tr><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"content-block\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 0 0 20px;\" valign=\"top\"> "
				+ "										<b>Mall Name</b>				: " + veriTransVO.getListProducts().get(0).getShortName()
				+ "									</td> "
				+ "								</tr><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"content-block\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 0 0 20px;\" valign=\"top\"> "
				+ "										<b>Area Parkir</b>				: " + veriTransVO.getListProducts().get(0).getLongName()
				+ "									</td> "
				+ "								</tr><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"content-block\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 0 0 20px;\" valign=\"top\"> "
				+ "										<b>Waktu Transaksi</b>			: " + CommonUtil.displayDateTime(transactionVO.getUpdatedOn())
				+ "									</td> "
				+ "								</tr><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"content-block\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 0 0 20px;\" valign=\"top\"> "
				+ "										<b>Jumlah Belanja</b>			: " + transactionVO.getTotalPriceIdr()
				+ "									</td> "
				+ "								</tr><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"content-block\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 0 0 20px;\" valign=\"top\"> "
				+ "										<b>Status Pembayaran</b> 		: Berhasil"
				+ "									</td> "
				+ "								</tr><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"content-block\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 0 0 20px;\" valign=\"top\"> "
				+ "										Jika anda tidak melakukan transaksi di atas, harap melaporkan email ini ke agusdk2011@gmail.com "
				+ "									</td> "
				+ "								</tr></table></td> "
				+ "					</tr></table><div class=\"footer\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; width: 100%; clear: both; color: #999; margin: 0; padding: 20px;\"> "
				+ "					<table width=\"100%\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"aligncenter content-block\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 12px; vertical-align: top; color: #999; text-align: center; margin: 0; padding: 0 0 20px;\" align=\"center\" valign=\"top\">Follow <a href=\"http://google.com\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 12px; color: #999; text-decoration: underline; margin: 0;\">@premium_parking_solution</a> on Twitter.</td> "
				+ "						</tr></table></div></div> "
				+ "		</td> "
				+ "		<td style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0;\" valign=\"top\"></td> "
				+ "	</tr></table></body> "
				+ "</html>";


		LOG.debug(" html Msg: "+ html);
		emailSender.sendHTMLMailNew("", "",
				new String[] { emailTo }, new String[] { user.getName() },
				emailSubject, html, txtMessage, null);

	}

	public String getDifferentField() {
		return differentField;
	}

	public void setDifferentField(String differentField) {
		this.differentField = differentField;
	}

	
	
}
