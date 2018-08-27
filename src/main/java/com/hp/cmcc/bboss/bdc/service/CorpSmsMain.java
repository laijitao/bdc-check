package com.hp.cmcc.bboss.bdc.service;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hp.cmcc.bboss.bdc.exception.BdcComException;
import com.hp.cmcc.bboss.bdc.handle.CorpSmsHandle;
import com.hp.cmcc.bboss.bdc.handle.impl.CorpSmsHandleImpl;
import com.hp.cmcc.bboss.bdc.pojo.BbdcTypeCdr;
import com.hp.cmcc.bboss.bdc.pojo.compare.BdcCompareResult;

@Service
public class CorpSmsMain {
	private static Logger L = LoggerFactory.getLogger(CorpSmsMain.class);
	private static CorpSmsHandle corpSmsHandle = new CorpSmsHandleImpl();

	public BdcCompareResult handle(List<String> fileBody, List<BbdcTypeCdr> rule, String fileName, String tranId) {
		long time = System.currentTimeMillis();
		BdcCompareResult bcr = new BdcCompareResult();
	    List<String> doneRecs = new LinkedList<String>();
	    List<String> errRecs = new LinkedList<String>();
	    List<String> errRecReport = new LinkedList<String>();
	    List<BbdcTypeCdr> doneList = new LinkedList<BbdcTypeCdr>();
		List<BbdcTypeCdr> errList = new LinkedList<BbdcTypeCdr>();
		String errInfoTableName = "";
		int checkNum = 0;
		//安生成SQL的顺序排序（HinderIdx）
		rule.sort(new Comparator<BbdcTypeCdr>() {

			@Override
			public int compare(BbdcTypeCdr o1, BbdcTypeCdr o2) {
				return o1.getHinderIdx().intValue()-o2.getHinderIdx().intValue();
			}
		});
		//分离错误报告规则和原始文件规则
		for(BbdcTypeCdr cdr : rule) {
			if(cdr.getFieldName().startsWith("ERR_INFO_")) {
				if("ERR_INFO_TABLE_NAME".equals(cdr.getFieldName())) {
					errInfoTableName = cdr.getDataFiller();
				}
				errList.add(cdr);
			}else {
				if(cdr.getFormerIdx() != -1) checkNum++;
				doneList.add(cdr);
			}
		}
		//处理文件体
		for(int i = 0; i < fileBody.size();i++) {
			try {
				doneRecs.add(corpSmsHandle.createSqlStr(fileBody.get(i), doneList, fileName, i+1, tranId, checkNum, errInfoTableName));
			} catch (BdcComException e) {
				errRecs.add(e.getRecordSql());
				errRecReport.add(e.toString());
			}
		}
		bcr.setDoneRec(doneRecs);
		bcr.setErrRec(errRecs);
		bcr.setErrRecReport(errRecReport);
		L.warn("time:"+(System.currentTimeMillis()-time)+"ms");
		return bcr;
	}

}
