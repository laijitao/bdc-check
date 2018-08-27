package com.hp.cmcc.bboss.bdc.common.impl;

import org.springframework.jdbc.core.JdbcTemplate;

import com.hp.cmcc.bboss.bdc.common.FieldHandle;
import com.hp.cmcc.bboss.bdc.config.BdcBeanFactory;
import com.hp.cmcc.bboss.bdc.dao.BaseDao;
import com.hp.cmcc.bboss.bdc.dao.impl.BaseDaoImpl;
import com.hp.cmcc.bboss.bdc.pojo.BbdcTypeCdr;
import com.hp.cmcc.bboss.bdc.utils.Tools;

public class FieldHandleImpl implements FieldHandle {
	private static BaseDao baseDao = new BaseDaoImpl();
	private static JdbcTemplate jdbc = BdcBeanFactory.getBean("bdcCompareTemplate", JdbcTemplate.class);
	

	@Override
	public String getSqlStr(String field, BbdcTypeCdr cdr) {
		if(Tools.IsBlank(field)) {
			return getValue(cdr);
		}else {
			switch(cdr.getDataType()) {
				case"DATE":
					return getDateSqlStr(field,cdr);
				case"NUMBER":
					return field;
				case"ENUM":
					return "'"+field+"'";
				default :
					return "'"+field+"'";
			}
		}
	}

	@Override
	public String getValueFromDatabase(String field, BbdcTypeCdr cdr) {
		String sql = cdr.getDataFiller();
		String result = baseDao.execuSql(sql, jdbc)+"";
		return result;
	}

	@Override
	public String getDateSqlStr(String date, BbdcTypeCdr cdr) {
		if(Tools.dateFormatCheck(date, cdr.getValidateRegex())) {
			return "to_date('"+date+"','"+cdr.getDataPattern()+"')";
		}else {
			return null;
		}
	}

	@Override
	public String getValue(BbdcTypeCdr cdr) {
		String value = "";
		if(cdr.getFormerIdx() == -1) {
			switch(cdr.getDataType()) {
				case"FIXED":
					value = cdr.getDataFiller();
					break;
				default :
					break;
			}
		}
		return Tools.IsBlank(value) ? null : "'"+value+"'";
	}

}
