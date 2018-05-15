package com.helencoder.controller;

import com.google.gson.Gson;
import com.helencoder.entity.AbstractVo;
import com.helencoder.service.AbstractService;
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
import java.util.List;
import java.util.Map;

/**
 * 摘要控制类
 *
 * Created by zhenghailun on 2018/5/14.
 */
@Controller
public class AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(AbstractController.class);
    @Autowired
    private Helper helper;

    @Autowired
    private AbstractService abstractService;

    @RequestMapping("/abstractList")
    public String userList() {
        return "abstract";
    }

    @RequestMapping("/getAbstractList")
    @ResponseBody
    public String userList(HttpServletRequest request) {
        try {
            String limit = helper.GetParam(request.getParameter("limit"));
            String offset = helper.GetParam(request.getParameter("offset"));
            String content = helper.GetParam(request.getParameter("content"));
            String abs = helper.GetParam(request.getParameter("abs"));

            Gson gson = new Gson();
            Map<String, Object> resultPkg = new HashMap<String, Object>();
            List<AbstractVo> abstractList = abstractService.getAbstractList(content, abs, limit, offset);
            int total = abstractService.getAbstractCount(content, abs);
            resultPkg.put("total", total);
            resultPkg.put("rows", abstractList);
            return gson.toJson(resultPkg);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception ex) {
            logger.error("getAbstractList报错：" + ex);
            return null;
        }
    }

}
