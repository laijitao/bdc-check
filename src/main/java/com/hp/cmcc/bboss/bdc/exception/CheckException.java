package com.hp.cmcc.bboss.bdc.exception;

public class CheckException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long fieldSeq;
	private String errCode;
	private String errMsg;
	
	public CheckException() {
		super();
	}

	public CheckException(String errCode, String errMsg) {
		super();
		this.errCode = errCode;
		this.errMsg = errMsg;
	}
	
	public CheckException(long fieldSeq,String errCode, String errMsg) {
		super();
		this.fieldSeq = fieldSeq;
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String toString() {
		return  "{errCode:" +errCode+"," +"errMsg:"+ errMsg +"}";
	}

	public long getFieldSeq() {
		return fieldSeq;
	}

	public void setFieldSeq(long fieldSeq) {
		this.fieldSeq = fieldSeq;
	}
	
}
