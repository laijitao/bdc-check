package com.hp.cmcc.bboss.bdc.pojo.compare;

import java.util.List;

public class BdcCompareResult {

	private List<String> doneRec;
	private List<String> errRec;
	private List<String> errRecReport;
	private List<String> taskSql;
	
	public BdcCompareResult() {
		super();
	}

	public BdcCompareResult(List<String> doneRec, List<String> errRec,List<String> errRecReport,List<String> taskSql) {
		super();
		this.doneRec = doneRec;
		this.errRec = errRec;
		this.errRecReport = errRecReport;
		this.taskSql = taskSql;
	}

	public List<String> getDoneRec() {
		return doneRec;
	}

	public void setDoneRec(List<String> doneRec) {
		this.doneRec = doneRec;
	}

	public List<String> getErrRec() {
		return errRec;
	}

	public void setErrRec(List<String> errRec) {
		this.errRec = errRec;
	}

	public List<String> getErrRecReport() {
		return errRecReport;
	}

	public void setErrRecReport(List<String> errRecReport) {
		this.errRecReport = errRecReport;
	}

	public List<String> getTaskSql() {
		return taskSql;
	}

	public void setTaskSql(List<String> taskSql) {
		this.taskSql = taskSql;
	}
	
	
}
