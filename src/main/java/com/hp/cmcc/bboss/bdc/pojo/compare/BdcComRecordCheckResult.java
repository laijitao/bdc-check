package com.hp.cmcc.bboss.bdc.pojo.compare;
/**
 * 
 * @ClassName: BdcComRecordCheckResult 
 * @Description: 校验结果类，封装校验通过后拼接的SQL，校验失败后拼接的SQL以及记录级的错误报告
 * @company HPE  
 * @author laijitao  
 * @date 2018年10月22日 上午10:49:53 
 *
 */
public class BdcComRecordCheckResult {

	private String doneStr;
	private String errStr;
	private String errRptStr;
	
	public BdcComRecordCheckResult(String doneStr, String errStr, String errRptStr) {
		super();
		this.doneStr = doneStr;
		this.errStr = errStr;
		this.errRptStr = errRptStr;
	}
	public BdcComRecordCheckResult() {
		super();
	}
	public String getDoneStr() {
		return doneStr;
	}
	public void setDoneStr(String doneStr) {
		this.doneStr = doneStr;
	}
	public String getErrStr() {
		return errStr;
	}
	public void setErrStr(String errStr) {
		this.errStr = errStr;
	}
	public String getErrRptStr() {
		return errRptStr;
	}
	public void setErrRptStr(String errRptStr) {
		this.errRptStr = errRptStr;
	}
	
}
