package com.hp.cmcc.bboss.bdc.check.impl;

import java.util.List;

import com.hp.cmcc.bboss.bdc.check.BaseCheck;
import com.hp.cmcc.bboss.bdc.check.CheckAndCreateSqlStr;
import com.hp.cmcc.bboss.bdc.check.CommonCheck;
import com.hp.cmcc.bboss.bdc.common.FieldHandle;
import com.hp.cmcc.bboss.bdc.common.RecordHandle;
import com.hp.cmcc.bboss.bdc.common.impl.FieldHandleImpl;
import com.hp.cmcc.bboss.bdc.common.impl.RecordHandleImpl;
import com.hp.cmcc.bboss.bdc.exception.BdcComException;
import com.hp.cmcc.bboss.bdc.exception.CheckException;
import com.hp.cmcc.bboss.bdc.pojo.BbdcTypeCdr;
import com.hp.cmcc.bboss.bdc.utils.BaseUtil;

public class CheckAndCreateSqlStrImpl implements CheckAndCreateSqlStr {
	
	private static BaseUtil baseUtil = new BaseUtil();
	private static BaseCheck baseCheck = new BaseCheckImpl();
	private static CommonCheck commonCheck = new CommonCheckImpl();
	private static FieldHandle fieldHandle = new FieldHandleImpl();
	private static RecordHandle recordHandle = new RecordHandleImpl();

	@Override
	public String checkAndCreateSqlStr(String record, List<BbdcTypeCdr> list, String fileName, long lineNum,
			String tranId, int checkNum, String tableName) throws BdcComException {
		String separator = list.get(0).getDataSeparator();
		String[] S = baseUtil.StrToArr(record, separator);
		BdcComException bdcComException = new BdcComException();
		StringBuffer recordSql = new StringBuffer();
		boolean mark = true;
		String id = "";
		boolean fieldNumMark = true;
		CheckException fieldNumException = new CheckException();
		try {
			commonCheck.fieldNumCheck(S, checkNum);
		} catch (CheckException e1) {
			fieldNumMark = false;
			fieldNumException.setErrCode(e1.getErrCode());
			fieldNumException.setErrMsg(e1.getErrMsg());
		}
		
		for(BbdcTypeCdr cdr : list) {
			String value = recordHandle.getValue(S, cdr);
			try {
				switch(cdr.getFieldValue().trim()) {
					case "RECORD":
						baseCheck.fieldCheck(value, cdr);
						break;
					case "DATABASE" :
						value = fieldHandle.getValueFromDatabase(value,cdr);
						break;
					case"SYSTEM" :
						value = cdr.getDataFiller().trim();
					default : break;
				}
				if("ID".equals(cdr.getFieldName())) id = value;
				if("TRAN_ID".equals(cdr.getFieldName())) value = tranId;
				if("ERR_MARK".endsWith(cdr.getFieldName())){
					if(mark) {
						value = cdr.getDataFiller();
					}else {
						value = "F";
					}
				}
				recordSql.append(fieldHandle.getSqlStr(value, cdr)+",");
			} catch (CheckException e) {
				//校验有错
				recordSql.append(fieldHandle.getSqlStr(value, cdr)+",");
				if(!fieldNumMark) {
					bdcComException.setErrCode(Long.parseLong(fieldNumException.getErrCode()));
					bdcComException.setErrDes(fieldNumException.getErrMsg());
					bdcComException.setFieldSeq(-1);
				}else {
					bdcComException.setErrCode(Long.parseLong(e.getErrCode()));
					bdcComException.setErrDes(e.getErrMsg());
					bdcComException.setFieldSeq(cdr.getFormerIdx()+1);
				}
				bdcComException.setFileName(fileName);
				bdcComException.setId(Long.parseLong(id));
				bdcComException.setLineNum(lineNum);
				bdcComException.setTableName(tableName);
				bdcComException.setTranId(tranId);
				mark = false;
			}
		}
		
		String recordStr = "";
		if(!fieldNumMark) {
			recordStr = id+",null,null,null,null,null,null,null,null,null,null,null,null,null,null,'"
					+ tranId+"','F',null,null,null,null";
		}else {
			recordStr = recordSql.toString().substring(0, recordSql.toString().length()-1);
		}
		
		if(!mark) {//校验有错则抛出带有校验结束后拼的SQL语句的异常
			bdcComException.setRecordSql(recordStr);
			throw bdcComException;
		}else {
			return recordStr;
		}
	}

}
