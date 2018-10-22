package com.hp.cmcc.bboss.bdc.check.impl;

import com.hp.cmcc.bboss.bdc.check.BaseCheck;
import com.hp.cmcc.bboss.bdc.exception.CheckException;
import com.hp.cmcc.bboss.bdc.pojo.BbdcTypeCdr;
import com.hp.cmcc.bboss.bdc.utils.Tools;
/**
 * 
 * @ClassName: BaseCheckImpl 
 * @Description: 基础校验类，对单个字段进行校验
 * @company HPE  
 * @author laijitao  
 * @date 2018年10月22日 上午10:35:28 
 *
 */
public class BaseCheckImpl implements BaseCheck {
	private static CommonCheckImpl commonCheckImpl = new CommonCheckImpl();

	@Override
	public void fieldCheck(String field, BbdcTypeCdr cdr) throws CheckException {
		String dataType = cdr.getDataType().trim();
		if(Tools.IsBlank(field)) {
			throw new CheckException("2","数据类型及格式与规范不符");
		}else {
			switch(dataType) {
				case"STRING":
					commonCheckImpl.fieldLengthCheck(field, cdr);
					commonCheckImpl.fieldIFormatCheck(field, cdr);
					break;
				case "FIXED" : 
					commonCheckImpl.dataValueRangeCheck(field, cdr);
					break;
				case "SQL" : 
					commonCheckImpl.fieldLengthCheck(field, cdr);
					commonCheckImpl.fieldIFormatCheck(field, cdr);
//					commonCheckImpl.uniqueCheck(jdbc,field, cdr);
					break;
				case "ENUM" : 
					commonCheckImpl.fieldLengthCheck(field, cdr);
					commonCheckImpl.dataValueRangeCheck(field, cdr);
					break;
				default : break;
			}
			
		}
	}

}
