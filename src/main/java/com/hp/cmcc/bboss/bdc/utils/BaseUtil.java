package com.hp.cmcc.bboss.bdc.utils;

public class BaseUtil {

	public String[] StrToArr(String str,String separator) {
		return str.split(separator,-1);
	}
	
	public String getTaskSql(String sql,String regex,String... values) {
		return Tools.replaceAll(regex, sql, values);
	}
	
}
