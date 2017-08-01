package com.myproject.parking.lib.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.myproject.parking.lib.data.AppMasterVO;
import com.myproject.parking.lib.data.BankMasterVO;
import com.myproject.parking.lib.data.MessageIssueVO;
import com.myproject.parking.lib.data.SubmitDataVO;

public interface GetMessageMapper {
	
	public SubmitDataVO findIssueDataAndMessageByIssueNumber(@Param("issueNumber") String issueNumber);
	public void submitMessage(MessageIssueVO messageIssueVO);
	public ArrayList<AppMasterVO> findAllAppList();
	public ArrayList<BankMasterVO> findAllBankList();
	public BankMasterVO findBankById(@Param("bankId") String bankId);
}
