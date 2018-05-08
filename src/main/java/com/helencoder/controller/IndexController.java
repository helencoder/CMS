package com.helencoder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 管理系统主控制器
 *
 * Created by zhenghailun on 2018/5/4.
 */
@Controller
public class IndexController {

    @RequestMapping("/")
    public String index() {
        return "home";
    }

}
