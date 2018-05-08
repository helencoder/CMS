package com.helencoder.utils.akka;

import akka.actor.AbstractActor;
import com.helencoder.utils.Calculation;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * AsyncActor
 *
 * Created by zhenghailun on 2018/5/8.
 */
@Component
@Scope("prototype")
public class AsyncActor extends AbstractActor {

    private final CompletableFuture<String> future;

    public AsyncActor(CompletableFuture<String> future) {
        this.future = future;
    }

    @Override
    public void preStart() throws Exception {
        //log.info("Actor Started!");
        System.out.println("Actor Started!");
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                //.match(Calculation.class, Calculation::run)
                .match(Calculation.class, r -> {
                    // 将结果绑定到反馈中输出
                    r.run();
                    future.complete(r.toString());
                    getContext().stop(self());
                })
                .build();
    }

    @Override
    public void postStop() throws Exception {
        //log.info("Actor Stopped!");
        System.out.println("Actor Stopped!");
    }

}
