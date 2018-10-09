package com.hp.cmcc.bboss.bdc.service;

import java.io.UnsupportedEncodingException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hp.cmcc.bboss.bdc.handle.HandleThread;
import com.hp.cmcc.bboss.bdc.pojo.BbdcTypeCdr;
import com.hp.cmcc.bboss.bdc.pojo.compare.BdcComRecordCheckResult;
import com.hp.cmcc.bboss.bdc.pojo.compare.BdcCompareResult;
import com.hp.cmcc.bboss.bdc.utils.BaseUtil;
import com.hp.cmcc.bboss.bdc.utils.PubData;
import com.hp.cmcc.bboss.bdc.utils.Tools;

@Service
public class CorpSmsMain {
	private static Logger L = LoggerFactory.getLogger(CorpSmsMain.class);
	private static BaseUtil baseUtil = new BaseUtil();

	public BdcCompareResult handle(List<String> fileBody, List<BbdcTypeCdr> rule, String fileName, String tranId) {
		BdcCompareResult bcr = new BdcCompareResult();
	    List<String> doneRecs = new LinkedList<String>();
	    List<String> errRecs = new LinkedList<String>();
	    List<String> errRecReport = new LinkedList<String>();
	    List<String> taskSql = new LinkedList<String>();
	    List<BdcComRecordCheckResult> resultList = new LinkedList<>();
	    List<BbdcTypeCdr> doneList = new LinkedList<BbdcTypeCdr>();
		List<BbdcTypeCdr> errList = new LinkedList<BbdcTypeCdr>();
		String errInfoTableName = "";//错误信息表可以在该表中查到此记录；
		int checkNum = 0;
		rule.sort(new Comparator<BbdcTypeCdr>() {
			@Override
			public int compare(BbdcTypeCdr o1, BbdcTypeCdr o2) {
				return o1.getHinderIdx().intValue()-o2.getHinderIdx().intValue();
			}
		});
		
		for(BbdcTypeCdr cdr : rule) {
			if(!"CONTERROR".equals(cdr.getValName().trim())) {
				if("ERR_INFO_TABLE_NAME".equals(cdr.getFieldName())) {
					errInfoTableName = cdr.getDataFiller();
				}else {
					if(cdr.getFormerIdx() != -1) {
						checkNum++;
					}
					doneList.add(cdr);
				}
			}else {
				errList.add(cdr);
			}
		}
		
	    int size = fileBody.size();
	    long lineNum = 1;
	    for(int start = 0;start < size;start ++) {
	    		HandleThread handleThread = new HandleThread(fileBody.get(start), doneList, fileName, lineNum++, tranId, checkNum, errInfoTableName);
	    		try {
					resultList.add(handleThread.call());
				} catch (Exception e) {
					e.printStackTrace();
				}
	    		lineNum++;
	    }
		
	    Iterator<BdcComRecordCheckResult> iterator = resultList.iterator();
		while(iterator.hasNext()) {
			BdcComRecordCheckResult bdcComRecordCheckResult = iterator.next();
			if(Tools.IsEmpty(bdcComRecordCheckResult.getErrStr())) {
				doneRecs.add(bdcComRecordCheckResult.getDoneStr());
			}else {
				errRecs.add(bdcComRecordCheckResult.getErrStr());
				errRecReport.add(bdcComRecordCheckResult.getErrRptStr());
			}
		}
	    String taskStr = "";
	    try {
		    if(errRecs.size() == 0) {
		    	errRecReport.add("null,null,null,null,null,null,null,'"+tranId+"'");
		    	taskStr = new String(baseUtil.getTaskSql(PubData.TASD_DONE_SQL	, "\\?", tranId,Tools.getNowTime(PubData.TMFMT6)).getBytes("GBK"),"GBK");
		    }else {
		    	taskStr = new String(baseUtil.getTaskSql(PubData.TASD_SQL	, "\\?", tranId,Tools.getNowTime(PubData.TMFMT6)).getBytes("GBK"),"GBK");
		    }
	    } catch (UnsupportedEncodingException e) {
	    	L.error("encode switch error!",e);
	    }
	    taskSql.add(taskStr);
	    L.info("TASK_SQL:"+taskSql.get(0));
	    
	    L.info("result sql:"+doneRecs.get(0));
	    
	    bcr.setDoneRec(doneRecs);
	    bcr.setErrRec(errRecs);
	    bcr.setErrRecReport(errRecReport);
	    bcr.setTaskSql(taskSql);
	    
		return bcr;
	}
}
