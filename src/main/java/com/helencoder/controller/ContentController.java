package com.helencoder.controller;

import com.google.gson.Gson;
import com.helencoder.entity.UserVo;
import com.helencoder.service.ContentService;
import com.helencoder.utils.Helper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 内容控制器
 *
 * Created by zhenghailun on 2018/5/4.
 */
@Controller
public class ContentController {
    private static final Logger logger = LoggerFactory.getLogger(ContentController.class);
    @Autowired
    private Helper helper;
    @Autowired
    private ContentService contentService;

    @RequestMapping("/contentList")
    public String contentList() {
        return "content";
    }

    @RequestMapping("/getContentList")
    @ResponseBody
    public String userList(HttpServletRequest request) {
        try {
            String limit = helper.GetParam(request.getParameter("limit"));
            String offset = helper.GetParam(request.getParameter("offset"));
            String data = helper.GetParam(request.getParameter("data"));
            String flag = helper.GetParam(request.getParameter("flag"));

            Gson gson = new Gson();
            Map<String, Object> resultPkg = new HashMap<String, Object>();
//            List<UserVo> userList = userService.getUserList(limit, offset, name, phone);
//            int total = userService.getUserCount(name, phone);
//            resultPkg.put("total", total);
//            resultPkg.put("rows", userList);
            return gson.toJson(resultPkg);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception ex) {
            logger.error("getContentList报错：" + ex);
            return null;
        }
    }

}
