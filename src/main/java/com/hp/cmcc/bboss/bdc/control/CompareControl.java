package com.hp.cmcc.bboss.bdc.control;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hp.cmcc.bboss.bdc.pojo.BbdcTypeCdr;
import com.hp.cmcc.bboss.bdc.pojo.RequestParamter;
import com.hp.cmcc.bboss.bdc.pojo.compare.BdcCompareResult;
import com.hp.cmcc.bboss.bdc.service.CorpSmsMain;
import com.hp.cmcc.bboss.bdc.service.MainThread;
import com.hp.cmcc.bboss.bdc.utils.Tools;

@RestController
public class CompareControl {
	@Autowired
	CorpSmsMain corpSmsMain;
	@Autowired
	MainThread threads;
	private static Logger L = LoggerFactory.getLogger(CompareControl.class);

	@RequestMapping(value = "/bdc/corpSMS/compare", method = RequestMethod.POST,consumes = "application/json")
	public BdcCompareResult threadHandle(@RequestBody RequestParamter requestParamter) {
		List<String> fileBody = requestParamter.getFileBody();
		for(String rec : fileBody) {
			L.info(rec);
		}
		List<BbdcTypeCdr> rule = requestParamter.getRule();
		String fileName = requestParamter.getFileName();
		L.info("file name:"+fileName+",record count :"+fileBody.size());
		String tranId = requestParamter.getTranId();
		if(Tools.IsBlank(fileName)) {
			L.error("the file name is empty, pls check it!");
			return null ;
		}
		if(Tools.IsBlank(tranId)) {
			L.error("the tranId is empty, pls check it!");
			return null ;
		}
		if(fileBody.size() == 0) {
			L.error("the fileBody is empty, pls check it!");
			return null ;
		}
		if(rule.size() == 0) {
			L.error("the check rule is empty, pls check it!");
			return null ;
		}
		return threads.handle(fileBody, rule, fileName, tranId);
	}
}
