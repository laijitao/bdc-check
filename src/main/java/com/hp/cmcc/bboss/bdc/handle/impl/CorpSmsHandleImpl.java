package com.hp.cmcc.bboss.bdc.handle.impl;

import java.util.List;

import com.hp.cmcc.bboss.bdc.check.CheckAndCreateSqlStr;
import com.hp.cmcc.bboss.bdc.check.impl.CheckAndCreateSqlStrImpl;
import com.hp.cmcc.bboss.bdc.exception.BdcComException;
import com.hp.cmcc.bboss.bdc.handle.CorpSmsHandle;
import com.hp.cmcc.bboss.bdc.pojo.BbdcTypeCdr;

public class CorpSmsHandleImpl implements CorpSmsHandle {
	private static CheckAndCreateSqlStr checkAndCreateSqlStr = new CheckAndCreateSqlStrImpl();

	@Override
	public String createSqlStr(String record, List<BbdcTypeCdr> list, String fileName, long lineNum, String tranId,
			int checkNum,String tableName) throws BdcComException {
		String result = checkAndCreateSqlStr.checkAndCreateSqlStr(record, list, fileName, lineNum, tranId, checkNum, tableName);
		return result;
	}

}
