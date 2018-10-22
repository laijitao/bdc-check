package com.hp.cmcc.bboss.bdc.common.impl;

import com.hp.cmcc.bboss.bdc.common.RecordHandle;
import com.hp.cmcc.bboss.bdc.pojo.BbdcTypeCdr;
/**
 * 
 * @ClassName: RecordHandleImpl 
 * @Description: 文件里面的记录的操作
 * @company HPE  
 * @author laijitao  
 * @date 2018年10月22日 上午10:44:12 
 *
 */
public class RecordHandleImpl implements RecordHandle {

	/* (non-Javadoc)
	 * <p>Title: getValue</p> 
	 * <p>Description: 根据规则从文件记录里面获取字段值 
	 * @param S
	 * @param cdr
	 * @return 
	 * @see com.hp.cmcc.bboss.bdc.common.RecordHandle#getValue(java.lang.String[], com.hp.cmcc.bboss.bdc.pojo.BbdcTypeCdr) 
	 */ 
	@Override
	public String getValue(String[] S, BbdcTypeCdr cdr) {
		int index = cdr.getFormerIdx().intValue();
		return index < S.length && index >= 0 ? S[index].trim() : "";
	}

}
