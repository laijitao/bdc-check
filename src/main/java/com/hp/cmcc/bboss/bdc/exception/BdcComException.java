package com.hp.cmcc.bboss.bdc.exception;
/**
 * 
 * @ClassName: BdcComException 
 * @Description: 对于校验不通过的记录，包装该记录的相关信息，用于生成记录级校验报告
 * @company HPE  
 * @author laijitao  
 * @date 2018年10月22日 上午10:31:48 
 *
 */
public class BdcComException extends Exception{

	/** 
	 * @Fields serialVersionUID : TODO
	 */ 
	
	private static final long serialVersionUID = 1L;
	private long id;//与入库表相应记录关联的id
	private String tableName;//入库的表名
	private String fileName;
	private long lineNum;
	private long fieldSeq;
	private long errCode;
	private String errDes;
	private String record;
	private String tranId;
	
	
	public BdcComException() {
		super();
	}
	public BdcComException(long id, String tableName, String fileName, long lineNum, long fieldSeq, long errCode,
			String errDes, String record,String tranId) {
		super();
		this.id = id;
		this.tableName = tableName;
		this.fileName = fileName;
		this.lineNum = lineNum;
		this.fieldSeq = fieldSeq;
		this.errCode = errCode;
		this.errDes = errDes;
		this.record = record;
		this.setTranId(tranId);
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
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
	public long getFieldSeq() {
		return fieldSeq;
	}
	public void setFieldSeq(long fieldSeq) {
		this.fieldSeq = fieldSeq;
	}
	public long getErrCode() {
		return errCode;
	}
	public void setErrCode(long errCode) {
		this.errCode = errCode;
	}
	public String getErrDes() {
		return errDes;
	}
	public void setErrDes(String errDes) {
		this.errDes = errDes;
	}
	public String getRecordSql() {
		return record;
	}
	public void setRecordSql(String record) {
		this.record = record;
	}
	public String getTranId() {
		return tranId;
	}
	public void setTranId(String tranId) {
		this.tranId = tranId;
	}
	
	/* (non-Javadoc)
	 * <p>Title: toString</p> 
	 * <p>Description: 重写tostring方法，返回拼接后的SQL 
	 * @return 
	 * @see java.lang.Throwable#toString() 
	 */ 
	@Override
	public String toString() {
		return id + ", '" + tableName + "','" + fileName + "',"
				+ lineNum + "," + fieldSeq + "," + errCode + ",'" 
				+ errDes + "'"+",'" + tranId +"'";
	}
}
