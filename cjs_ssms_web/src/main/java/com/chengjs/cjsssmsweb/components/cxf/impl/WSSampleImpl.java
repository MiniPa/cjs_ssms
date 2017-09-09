package com.chengjs.cjsssmsweb.components.cxf.impl;

import com.chengjs.cjsssmsweb.components.cxf.WSSample;
import org.springframework.stereotype.Component;

import javax.jws.WebService;

/**
 * WSSampleImpl:
 * @author: <a href="mailto:chengjs_minipa@outlook.com">chengjs</a>, version:1.0.0, 14:57
 */
@Component("wSSample")
@WebService
public class WSSampleImpl implements WSSample {

	public String say(String str) {
		return "你好这是wSSample/say返回值："+str;
	}

}
