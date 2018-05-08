package com.helencoder.controller;

import com.google.gson.Gson;
import com.helencoder.entity.UserVo;
import com.helencoder.service.AsyncService;
import com.helencoder.service.UserService;
import com.helencoder.utils.Helper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户控制器
 *
 * Created by zhenghailun on 2018/5/4.
 */
@Controller
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private Helper helper;
    @Autowired
    private UserService userService;

    @RequestMapping("/userList")
    public String userList() {
        return "user";
    }

    @RequestMapping("/getUserList")
    @ResponseBody
    public String userList(HttpServletRequest request) {
        try {
            String limit = helper.GetParam(request.getParameter("limit"));
            String offset = helper.GetParam(request.getParameter("offset"));
            String name = helper.GetParam(request.getParameter("name"));
            String phone = helper.GetParam(request.getParameter("phone"));

            Gson gson = new Gson();
            Map<String, Object> resultPkg = new HashMap<String, Object>();
            List<UserVo> userList = userService.getUserList(limit, offset, name, phone);
            int total = userService.getUserCount(name, phone);
            resultPkg.put("total", total);
            resultPkg.put("rows", userList);
            return gson.toJson(resultPkg);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception ex) {
            logger.error("getUserList报错：" + ex);
            return null;
        }
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    @ResponseBody
    public String updateUser(@RequestParam String param) {
        try {
            String jsonStr = helper.GetParam(param);
            int result = 0;
            String resultStr = "success";
            JSONObject jsonObject = new JSONObject(jsonStr);
            UserVo userVo = new UserVo();
            userVo.setId(jsonObject.getString("id"));
            userVo.setName(jsonObject.getString("name"));
            userVo.setAge(jsonObject.getString("age"));
            userVo.setPhone(jsonObject.getString("phone"));
            userVo.setEmail(jsonObject.getString("email"));
            userVo.setAddress(jsonObject.getString("address"));
            userVo.setCompany(jsonObject.getString("company"));
            userVo.setRemark(jsonObject.getString("remark"));

            if (userVo.getId() != null && !userVo.getId().equals("")) {
                result = userService.updateUser(userVo);
            } else {
                result = userService.addUser(userVo);
            }
            if (result < 1) {
                resultStr = "error";
            }

            return resultStr;
        } catch (Exception ex) {
            logger.error("updateUser报错：" + ex);
            return null;
        }
    }

    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    @ResponseBody
    public String deleteUser(@RequestParam String param) {
        try {
            String jsonStr = helper.GetParam(param);
            int result = 0;
            String resultStr = "success";

            JSONArray jsonArray = new JSONArray(jsonStr);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                String id = jsonObject.getString("id");
                result = userService.deleteUser(id);
                if (result < 1) {
                    resultStr = "error";
                }
            }

            return resultStr;
        } catch (Exception ex) {
            logger.error("deleteUser报错：" + ex);
            return null;
        }
    }

}
