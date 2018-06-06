package com.helencoder.dao;

import com.helencoder.entity.AbstractVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by zhenghailun on 2018/5/14.
 */
@Repository
public class AbstractDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 获取摘要记录数
     *
     * @param sql
     */
    public int getAbstractListCount(String sql) {
        return jdbcTemplate.queryForObject(sql,null, Integer.class);
    }

    /**
     * 获取摘要记录列表
     *
     * @param sql
     */
    public List<AbstractVo> getAbstractList(String sql) {
        return getAbstractListFromDB(jdbcTemplate, sql);

    }

    /**
     * 数据库查询
     *
     * @param jdbcTemplate
     * @param sql
     */
    public List<AbstractVo> getAbstractListFromDB(JdbcTemplate jdbcTemplate, String sql) {
        List<AbstractVo> abstractList = jdbcTemplate.query(sql, new RowMapper<AbstractVo>() {
            @Override
            public AbstractVo mapRow(ResultSet resultSet, int i) throws SQLException {
                AbstractVo abstractVo = new AbstractVo();
                abstractVo.setId(resultSet.getString("id"));
                abstractVo.setContent(resultSet.getString("content"));
                abstractVo.setAbs(resultSet.getString("abs"));
                abstractVo.setResult(resultSet.getString("result"));
                abstractVo.setSimilarity(resultSet.getString("similarity"));
                abstractVo.setFlag(resultSet.getString("flag"));
                abstractVo.setCreateTime(resultSet.getString("createTime"));
                abstractVo.setUpdateTime(resultSet.getString("updateTime"));
                return abstractVo;
            }
        });

        return abstractList;
    }

}
