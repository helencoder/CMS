package com.helencoder.utils;

import akka.actor.UntypedActor;
import com.helencoder.entity.Message;
import com.helencoder.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * Created by zhenghailun on 2018/5/8.
 */
@Component
@Scope("prototype")
public class AsyncActor extends UntypedActor  {
    @Autowired
    private AsyncService businessService;

    final private CompletableFuture<Message> future;

    public AsyncActor(CompletableFuture<Message> future) {
        this.future = future;
    }

    @Override
    public void onReceive(Object message) throws Exception {
        businessService.perform(this + " " + message);

        if (message instanceof Message) {
            future.complete((Message) message);
        } else {
            unhandled(message);
        }

        getContext().stop(self());
    }
}
