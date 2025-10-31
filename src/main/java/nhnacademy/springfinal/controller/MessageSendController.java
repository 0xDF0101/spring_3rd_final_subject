package nhnacademy.springfinal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MessageSendController {

    @PostMapping("/send")
    public void sendMessage() {

    }
}
