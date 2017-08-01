package com.myproject.parking.lib.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.myproject.parking.lib.data.AppMasterVO;
import com.myproject.parking.lib.data.ListMemberVO;
import com.myproject.parking.lib.data.SubmitDataVO;
import com.myproject.parking.lib.data.TaskInfoVO;
import com.myproject.parking.lib.data.UserRefVO;

public interface SubmitDataMapper {
	public void insertToDb(SubmitDataVO submitDataVO);

	public SubmitDataVO findIssueByIssueNumber(@Param("issueNumber") String issueNumber);
	
	public ArrayList<SubmitDataVO> findIssueByAppId(@Param("appId") String appId);
	
	public AppMasterVO findAppById(@Param("appId") String appId);
	
	public ArrayList<UserRefVO> findUsrRefByAppId(@Param("appId") String appId);
	
	public TaskInfoVO findTaskInfoByAppUd(@Param("appId") String appId);
	
	public ArrayList<ListMemberVO> findUsrRefAllByAppId(@Param("appId") String appId);
	
	public ArrayList<UserRefVO> findUsrRefByUserId(@Param("userId") String userId);
	
	public int countUsrRefByUserIdAndJobId(SubmitDataVO submitDataVO);
	
	public void updateStatus(@Param("status") int status
						,@Param("issueNumber") String issueNumber);
	
	
}
