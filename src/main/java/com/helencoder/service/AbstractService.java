package com.helencoder.service;

import com.helencoder.dao.AbstractDao;
import com.helencoder.entity.AbstractVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhenghailun on 2018/5/14.
 */
@Service
public class AbstractService {
    @Autowired
    private AbstractDao abstractDao;

    /**
     * 获取记录数
     *
     * @param content 正文
     * @param abs 摘要
     */
    public int getAbstractCount(String content, String abs) {
        String sql = "SELECT COUNT(*) FROM Abstract WHERE content LIKE '%" + content + "%'" +
                " AND abs LIKE '%" + abs + "%'";
        return abstractDao.getAbstractListCount(sql);
    }

    /**
     * 获取记录列表
     * @param content 正文
     * @param abs 摘要
     * @param limit 分页标识
     * @param offset 分页标识
     */
    public List<AbstractVo> getAbstractList(String content, String abs, String limit, String offset) {
        String sql = "SELECT * FROM Abstract WHERE content LIKE '%" + content + "%'" +
                " AND abs LIKE '%" + abs + "%' LIMIT " + limit + " OFFSET " + offset;
        return abstractDao.getAbstractList(sql);
    }



}
