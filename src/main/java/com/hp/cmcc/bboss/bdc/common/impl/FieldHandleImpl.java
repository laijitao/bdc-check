package com.hp.cmcc.bboss.bdc.common.impl;

import org.springframework.jdbc.core.JdbcTemplate;

import com.hp.cmcc.bboss.bdc.common.FieldHandle;
import com.hp.cmcc.bboss.bdc.config.BdcBeanFactory;
import com.hp.cmcc.bboss.bdc.dao.BaseDao;
import com.hp.cmcc.bboss.bdc.dao.impl.BaseDaoImpl;
import com.hp.cmcc.bboss.bdc.pojo.BbdcTypeCdr;
import com.hp.cmcc.bboss.bdc.utils.Tools;
/**
 * 
 * @ClassName: FieldHandleImpl 
 * @Description: 对单个字段的操作
 * @company HPE  
 * @author laijitao  
 * @date 2018年10月22日 上午10:39:29 
 *
 */
public class FieldHandleImpl implements FieldHandle {
	private static BaseDao baseDao = new BaseDaoImpl();
	private static JdbcTemplate jdbc = BdcBeanFactory.getBean("bdcCompareTemplate", JdbcTemplate.class);
	

	/* (non-Javadoc)
	 * <p>Title: getSqlStr</p> 
	 * <p>Description: 单个字段进行SQL拼接 
	 * @param field
	 * @param cdr
	 * @return 
	 * @see com.hp.cmcc.bboss.bdc.common.FieldHandle#getSqlStr(java.lang.String, com.hp.cmcc.bboss.bdc.pojo.BbdcTypeCdr) 
	 */ 
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

	/* (non-Javadoc)
	 * <p>Title: getValueFromDatabase</p> 
	 * <p>Description: 从数据库里面获取值 
	 * @param field
	 * @param cdr
	 * @return 
	 * @see com.hp.cmcc.bboss.bdc.common.FieldHandle#getValueFromDatabase(java.lang.String, com.hp.cmcc.bboss.bdc.pojo.BbdcTypeCdr) 
	 */ 
	@Override
	public String getValueFromDatabase(String field, BbdcTypeCdr cdr) {
		String sql = cdr.getDataFiller();
		String result = baseDao.execuSql(sql, jdbc)+"";
		return result;
	}

	/* (non-Javadoc)
	 * <p>Title: getDateSqlStr</p> 
	 * <p>Description: 将字符串的日期转化为某种格式的数据库日期格式 
	 * @param date
	 * @param cdr
	 * @return 
	 * @see com.hp.cmcc.bboss.bdc.common.FieldHandle#getDateSqlStr(java.lang.String, com.hp.cmcc.bboss.bdc.pojo.BbdcTypeCdr) 
	 */ 
	@Override
	public String getDateSqlStr(String date, BbdcTypeCdr cdr) {
		if(Tools.dateFormatCheck(date, cdr.getValidateRegex())) {
			return "to_date('"+date+"','"+cdr.getDataPattern()+"')";
		}else {
			return null;
		}
	}

	/* (non-Javadoc)
	 * <p>Title: getValue</p> 
	 * <p>Description: 单个字段根据规则生成拼接SQL的格式 
	 * @param cdr
	 * @return 
	 * @see com.hp.cmcc.bboss.bdc.common.FieldHandle#getValue(com.hp.cmcc.bboss.bdc.pojo.BbdcTypeCdr) 
	 */ 
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
