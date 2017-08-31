package com.chengjs.cjsssmsweb.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 */
public class DateUtil {

  public static final String PATTERN = "yyyy-MM-dd HH:mm:dd";

  /**
	 * 日期对象转字符串
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatDate(Date date,String format){
		String result="";
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		if(date!=null){
			result=sdf.format(date);
		}
		return result;
	}

	/**
	 * 字符串转日期对象
	 * @param str
	 * @param format
	 * @return
	 * @throws Exception
	 */
	public static Date formatString(String str,String format) throws Exception{
		if(StringUtil.isEmpty(str)){
			return null;
		}
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		return sdf.parse(str);
	}

	public static String getCurrentDateStr(){
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat(PATTERN);
		return sdf.format(date);
	}
}
