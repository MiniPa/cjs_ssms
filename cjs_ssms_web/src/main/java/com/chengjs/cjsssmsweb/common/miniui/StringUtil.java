package com.chengjs.cjsssmsweb.common.miniui;

import java.util.Collection;
import java.util.Iterator;

public class StringUtil {
	public static boolean isNullOrEmpty(Object obj) {	
		return obj == null || "".equals(obj.toString());
	}
	public static String toString(Object obj){
		if(obj == null) return "null";
		return obj.toString();
	}
	public static String join(Collection s, String delimiter) {
        StringBuffer buffer = new StringBuffer();
        Iterator iter = s.iterator();
        while (iter.hasNext()) {
            buffer.append(iter.next());
            if (iter.hasNext()) {
                buffer.append(delimiter);
            }
        }
        return buffer.toString();
    }
}
