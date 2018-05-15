package com.helencoder.service;

import com.helencoder.dao.UserDao;
import com.helencoder.entity.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户服务
 *
 * Created by zhenghailun on 2018/5/4.
 */
@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    /**
     * 获取用户数量
     *
     * @param name 姓名
     * @param phone 手机号
     */
    public int getUserCount(String name, String phone) {
        String sql;
        if (phone == null || phone.isEmpty()) {
            sql = "SELECT COUNT(*) FROM User WHERE name LIKE '%" + name + "%'";
        } else {
            sql = "SELECT * FROM User WHERE name LIKE '%" + name + "%' AND phone=" + phone;
        }
        int count = userDao.getUserListCount(sql);
        return count;
    }

    /**
     * 获取用户列表
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

        List<UserVo> userList = userDao.getUserList(sql);
        return userList;
    }


    /**
     * 用户信息更新
     */
    public int updateUser(UserVo userVo) {
        return userDao.updateUser(userVo);
    }

    /**
     * 新增用户
     */
    public int addUser(UserVo userVo) {
        return userDao.addUser(userVo);
    }

    /**
     * 删除用户
     */
    public int deleteUser(String id) {
        return userDao.deleteUser(id);
    }

}
