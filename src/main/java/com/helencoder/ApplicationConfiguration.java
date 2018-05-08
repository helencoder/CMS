package com.helencoder;

import akka.actor.ActorSystem;
import com.helencoder.utils.akka.SpringExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * Created by zhenghailun on 2018/5/8.
 */
@Configuration
public class ApplicationConfiguration {
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private SpringExtension springExtension;

    @Bean
    public ActorSystem actorSystem() {
        ActorSystem actorSystem = ActorSystem.create("ActorSystem", akkaConfiguration());
        springExtension.initialize(applicationContext);
        return actorSystem;
    }

    @Bean
    public Config akkaConfiguration() {
        return ConfigFactory.load();
    }
}
