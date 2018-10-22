package com.hp.cmcc.bboss.bdc.handle;

import java.util.List;
import java.util.concurrent.Callable;

import com.hp.cmcc.bboss.bdc.check.CheckAndCreateSqlStr;
import com.hp.cmcc.bboss.bdc.check.impl.CheckAndCreateSqlStrImpl;
import com.hp.cmcc.bboss.bdc.exception.BdcComException;
import com.hp.cmcc.bboss.bdc.pojo.BbdcTypeCdr;
import com.hp.cmcc.bboss.bdc.pojo.compare.BdcComRecordCheckResult;
/**
 * 
 * @ClassName: HandleThread 
 * @Description: 检验线程，返回校验后的结果
 * @company HPE  
 * @author laijitao  
 * @date 2018年10月22日 上午10:48:50 
 *
 */
public class HandleThread implements Callable<BdcComRecordCheckResult>{
	private static CheckAndCreateSqlStr checkAndCreateSqlStr = new CheckAndCreateSqlStrImpl();
	private String record;
	private List<BbdcTypeCdr> list;
	private String fileName;
	private long lineNum;
	private String tranId;
	private int checkNum;
	private String errInfoTableName;

	@Override
	public BdcComRecordCheckResult call() throws Exception {
		BdcComRecordCheckResult result = new BdcComRecordCheckResult();
		try {
			String s = checkAndCreateSqlStr.checkAndCreateSqlStr(record, list, fileName, lineNum, tranId, checkNum, errInfoTableName);
			result.setDoneStr(s);
		}catch(BdcComException e) {
			result.setErrStr(e.getRecordSql());
			result.setErrRptStr(e.toString());
		}
		return result;
	}

	public HandleThread() {
		super();
	}

	public HandleThread(String record, List<BbdcTypeCdr> list, String fileName, long lineNum, String tranId,
			int checkNum, String errInfoTableName) {
		super();
		this.record = record;
		this.list = list;
		this.fileName = fileName;
		this.lineNum = lineNum;
		this.tranId = tranId;
		this.checkNum = checkNum;
		this.errInfoTableName = errInfoTableName;
	}

	public String getRecord() {
		return record;
	}

	public void setRecord(String record) {
		this.record = record;
	}

	public List<BbdcTypeCdr> getDoneRecs() {
		return list;
	}

	public void setDoneRecs(List<BbdcTypeCdr> list) {
		this.list = list;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public long getLineNum() {
		return lineNum;
	}

	public void setLineNum(long lineNum) {
		this.lineNum = lineNum;
	}

	public String getTranId() {
		return tranId;
	}

	public void setTranId(String tranId) {
		this.tranId = tranId;
	}

	public int getCheckNum() {
		return checkNum;
	}

	public void setCheckNum(int checkNum) {
		this.checkNum = checkNum;
	}

	public String getErrInfoTableName() {
		return errInfoTableName;
	}

	public void setErrInfoTableName(String errInfoTableName) {
		this.errInfoTableName = errInfoTableName;
	}
	
}
