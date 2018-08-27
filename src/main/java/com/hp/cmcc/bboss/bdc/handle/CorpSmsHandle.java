package com.hp.cmcc.bboss.bdc.handle;

import java.util.List;

import com.hp.cmcc.bboss.bdc.exception.BdcComException;
import com.hp.cmcc.bboss.bdc.pojo.BbdcTypeCdr;


public interface CorpSmsHandle {

	String createSqlStr(String record,List<BbdcTypeCdr> list,String filename,
			long lineNum,String tranId, int checkNum,String tableName) throws BdcComException;
}
