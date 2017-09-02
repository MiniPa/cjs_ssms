package com.chengjs.cjsssmsweb.cxf;

import javax.jws.WebService;

/**
 * WSSample: WebService 开放接口 案例
 * @author: <a href="mailto:chengjs_minipa@outlook.com">chengjs</a>, version:1.0.0, 14:56
 */
@WebService
public interface WSSample {

	public String say(String str);

}
