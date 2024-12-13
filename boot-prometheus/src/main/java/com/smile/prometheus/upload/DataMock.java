package com.smile.prometheus.upload;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Description
 * @ClassName DataMock
 * @Author smile
 * @date 2024.03.02 00:08
 */
@Component
public class DataMock {

    @Autowired
    private CollectorRegistry collectorRegistry;

    @PostConstruct
    public void mock(){
        Counter counter = Counter.build().name("prometheus_counter").help("prometheus counter test")
                .register(collectorRegistry);
        new Thread(()-> {
            while (true){
                counter.inc();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
