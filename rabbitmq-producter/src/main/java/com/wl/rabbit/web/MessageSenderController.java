package com.wl.rabbit.web;


import com.wl.mq.model.domain.Order;
import com.wl.rabbit.service.WorkQueue;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 南顾北衫
 * @email 17674641491@163.com
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/mq/rabbit")
public class MessageSenderController {
    private final WorkQueue workQueue;

    @PostMapping("/workQueue")
    public ResponseEntity<String> workQueue(Order order, String queueName) {
        workQueue.send(order, queueName);
        return ResponseEntity.ok("success");
    }

    //fanout
    @PostMapping("/publish")
    public ResponseEntity<String> publish(Order order, String queueName) {
        workQueue.publish(order, queueName);
        return ResponseEntity.ok("success");
    }

    /**
     * direct
     * @param order /
     * @param routeKey /
     * @return /
     */
    @PostMapping("/direct")
    public ResponseEntity<String> direct(Order order, String routeKey) {
        workQueue.direct(order, routeKey);
        return ResponseEntity.ok("success");
    }

    /**
     * topic
     */
    @PostMapping("/topic")
    public ResponseEntity<String> topic(Order order, String routeKey) {
        workQueue.topic(order, routeKey);
        return ResponseEntity.ok("success");
    }



    /**
     * 死信交换机和队列
     */
    @PostMapping("/dead")
    public ResponseEntity<String> dead(Order order, String routeKey) {
        workQueue.dead(order, routeKey);
        return ResponseEntity.ok("success");
    }
}
