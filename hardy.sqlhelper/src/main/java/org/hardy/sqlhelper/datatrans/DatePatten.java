package org.hardy.sqlhelper.datatrans;

import java.text.ParseException;
import java.util.Date;

/**
 * 专门转化时间的接口
 * @author songs
 *
 */
public interface DatePatten {
	/**
	 * 转化时间
	 * @param dateString
	 * @return
	 * @throws ParseException
	 */
	public Date convertir(String dateString) throws ParseException;
	
}
