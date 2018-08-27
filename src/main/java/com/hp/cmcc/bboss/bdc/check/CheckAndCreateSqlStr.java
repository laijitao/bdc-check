package com.hp.cmcc.bboss.bdc.check;

import java.util.List;

import com.hp.cmcc.bboss.bdc.exception.BdcComException;
import com.hp.cmcc.bboss.bdc.pojo.BbdcTypeCdr;

public interface CheckAndCreateSqlStr {

	public String checkAndCreateSqlStr(String record, List<BbdcTypeCdr> list, 
			String fileName, long lineNum, String tranId, int checkNum, String tableName) throws BdcComException;
}
