package com.example.controller;

import com.example.service.KafkaSender;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/send")
@RequiredArgsConstructor
public class SendMessageController {
    private final KafkaSender kafkaSender;
    @GetMapping("/message")
    public void send(@RequestParam("msg") String message) {
        System.out.println(message);
        kafkaSender.sendMessage(message, "topic-1");
    }
}
