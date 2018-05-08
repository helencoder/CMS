package com.helencoder.controller;

import com.helencoder.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;
/**
 * 异步控制器
 *
 * Created by zhenghailun on 2018/5/8.
 */
@RestController
public class AsyncController {
    private static final Long DEFERRED_RESULT_TIMEOUT = 1000L;

    private AtomicLong id = new AtomicLong(0);

    @Autowired
    private AsyncService asyncService;

    @RequestMapping("/asyncService")
    public String asyncService() {
        /**
         * 异步任务执行计算
         */
        DeferredResult<String> deferred = new DeferredResult<>(DEFERRED_RESULT_TIMEOUT);
        CompletableFuture<String> future = asyncService.run(1000);
        future.whenComplete((result, error) -> {
            if (error != null) {
                deferred.setErrorResult(error);
            } else {
                deferred.setResult(result);
                System.out.println("当前最终输出结果为: " + result);
            }
        });

        System.out.println("接口已返回！");

        return "success";
    }

    /**
     * 异步接口返回
     */
    @RequestMapping("/async")
    public DeferredResult<String> getAsyncNonBlocking() {
        DeferredResult<String> deferred = new DeferredResult<>(DEFERRED_RESULT_TIMEOUT);
        CompletableFuture<String> future = asyncService.run(1000);
        future.whenComplete((result, error) -> {
            if (error != null) {
                deferred.setErrorResult(error);
            } else {
                deferred.setResult(result);
            }
        });
        return deferred;
    }
}
