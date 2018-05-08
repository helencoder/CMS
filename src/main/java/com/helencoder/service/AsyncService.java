package com.helencoder.service;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.helencoder.utils.Calculation;
import com.helencoder.utils.akka.SpringExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
/**
 * Created by zhenghailun on 2018/5/8.
 */
@Service
public class AsyncService {
    @Autowired
    private ActorSystem actorSystem;

    @Autowired
    private SpringExtension springExtension;

    public CompletableFuture<String> run(long num) {
        CompletableFuture<String> future = new CompletableFuture<>();
        ActorRef workerActor = actorSystem.actorOf(
                springExtension.props("asyncActor", future), "worker");
        System.out.println("计算任务开始时间: " + System.currentTimeMillis());
        workerActor.tell(new Calculation(num), null);
        return future;
    }

}
