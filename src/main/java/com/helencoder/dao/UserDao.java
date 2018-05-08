package com.helencoder.dao;

import com.helencoder.entity.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by zhenghailun on 2018/5/4.
 */
@Repository
public class UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 获取用户记录数
     *
     * @param name 姓名
     * @param phone 手机号
     */
    public int getUserListCount(String name, String phone) {
        String sql;
        if (phone == null || phone.isEmpty()) {
            sql = "SELECT COUNT(*) FROM User WHERE name LIKE '%" + name + "%'";
        } else {
            sql = "SELECT * FROM User WHERE name LIKE '%" + name + "%' AND phone=" + phone;
        }
        int count =  jdbcTemplate.queryForObject(sql,null, Integer.class);
        return count;
    }

    /**
     * 获取用户记录列表
     *
     * @param limit 分页标识
     * @param offset 分页标识
     * @param name 姓名
     * @param phone 手机号
     */
    public List<UserVo> getUserList(String limit, String offset, String name, String phone) {
        String sql;
        if (phone == null || phone.isEmpty()) {
            sql = "SELECT * FROM User WHERE name LIKE '%" + name +
                    "%' LIMIT " + limit + " OFFSET " + offset;
        } else {
            sql = "SELECT * FROM User WHERE name LIKE '%" + name + "%' AND phone=" + phone +
                    " LIMIT " + limit + " OFFSET " + offset;
        }
        List<UserVo> userList = getUserListFromDB(jdbcTemplate, sql);

        return userList;
    }

    /**
     * 数据库查询
     */
    public List<UserVo> getUserListFromDB(JdbcTemplate jdbcTemplate, String sql) {
        List<UserVo> userList = jdbcTemplate.query(sql, new RowMapper<UserVo>() {
            @Override
            public UserVo mapRow(ResultSet resultSet, int i) throws SQLException {
                UserVo userVo = new UserVo();
                userVo.setId(resultSet.getString("id"));
                userVo.setName(resultSet.getString("name"));
                userVo.setAge(resultSet.getString("age"));
                userVo.setPhone(resultSet.getString("phone"));
                userVo.setEmail(resultSet.getString("email"));
                userVo.setAddress(resultSet.getString("address"));
                userVo.setCompany(resultSet.getString("company"));
                userVo.setRemark(resultSet.getString("remark"));
                return userVo;
            }
        });

        return userList;
    }

    /**
     * 增加用户
     */
    public int addUser(UserVo userVo) {
        int res = jdbcTemplate.update("INSERT INTO User values (NULL,?,?,?,?,?,?,?)", new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, userVo.getName());
                ps.setString(2, userVo.getAge());
                ps.setString(3, userVo.getPhone());
                ps.setString(4, userVo.getEmail());
                ps.setString(5, userVo.getAddress());
                ps.setString(6, userVo.getCompany());
                ps.setString(7, userVo.getRemark());
            }
        });
        return res;
    }

    /**
     * 删除用户
     */
    public int deleteUser(String id) {
        String sql = "delete from User where id = ?";
        Object args[] = new Object[]{id};
        return jdbcTemplate.update(sql,args);
    }

    /**
     * 用户信息更新
     */
    public int updateUser(UserVo userVo) {
        String sql = "update User set name=?,age=?,phone=?,email=?,address=?,company=?,remark=? where id = ?";
        Object args[] = new Object[]{userVo.getName(),userVo.getAge(),userVo.getPhone(),userVo.getEmail(),userVo.getAddress(),
                userVo.getCompany(),userVo.getRemark(),userVo.getId()};
        return jdbcTemplate.update(sql,args);
    }

}
