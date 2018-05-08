package com.helencoder.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
/**
 * Created by zhenghailun on 2018/5/8.
 */
@Service
public class AsyncService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void perform(Object o) {
        logger.info("Perform: {}", o);
    }
}
