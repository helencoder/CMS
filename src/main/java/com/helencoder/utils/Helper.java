package com.helencoder.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 工具类
 *
 * Created by zhenghailun on 2018/5/4.
 */
@Component
public class Helper {

    private static final Logger logger = LoggerFactory.getLogger(Helper.class);
    @Value("${ParamFilter}")
    public String paramFilter;


    /**
     * 过滤参数
     *
     * @param text
     * @return
     */
    public String GetParam(String text) {
        try {

            if (text == null || "".equals(text)) {
                return "";
            }
            String[] filter = paramFilter.split("\\|");
            for (int i = 0; i < filter.length; i++) {
                text = text.replace(filter[i], "");
            }
            text = text.replace("|", "");
        } catch (Exception ex) {
            logger.error("Helper.GetParam报错：" + ex);
        }
        return text;
    }

}
