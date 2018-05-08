package com.helencoder.service;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.helencoder.entity.Message;
import com.helencoder.utils.SpringExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
/**
 * Created by zhenghailun on 2018/5/8.
 */
@Service
public class AsyncFutureService {
    @Autowired
    private ActorSystem actorSystem;

    @Autowired
    private SpringExtension springExtension;

    public CompletableFuture<Message> get(String payload, Long id) {
        CompletableFuture<Message> future = new CompletableFuture<>();
        ActorRef workerActor = actorSystem.actorOf(springExtension.props("asyncActor", future), "worker-actor");
        workerActor.tell(new Message(payload, id), null);
        return future;
    }
}
