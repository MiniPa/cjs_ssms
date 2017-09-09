package com.chengjs.cjsssmsweb.service.master;


import com.chengjs.cjsssmsweb.mybatis.pojo.master.SocketContent;

import java.util.List;

/**
 * ISocketContentService:
 * author: <a href="mailto:chengjs_minipa@outlook.com">chengjs</a>, version:1.0.0, 21:08
 */
public interface ISocketContentService {

    List<SocketContent> findSocketContentList();

    int insertSelective(SocketContent socketContent);

}





