package com.hp.cmcc.bboss.bdc.common;

import com.hp.cmcc.bboss.bdc.pojo.BbdcTypeCdr;

public interface FieldHandle {

	String getSqlStr(String field,BbdcTypeCdr cdr);
	
	String getValueFromDatabase(String field,BbdcTypeCdr cdr);
	
	String getDateSqlStr(String date, BbdcTypeCdr cdr);
	
	String getValue(BbdcTypeCdr cdr);
}
