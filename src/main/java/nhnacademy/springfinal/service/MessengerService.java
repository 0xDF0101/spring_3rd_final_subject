package nhnacademy.springfinal.service;

import nhnacademy.springfinal.model.Member;
import nhnacademy.springfinal.model.MessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class MessengerService {

    private final DoorayMessengerClient doorayMessengerClient;

    @Autowired
    public MessengerService(DoorayMessengerClient doorayMessengerClient) {
        this.doorayMessengerClient = doorayMessengerClient;
    }

    public void sendMessenger() {
        String message = "아이디 비밀번호 5회 실패로 계정이 잠겼습니다.";
        MessageRequest messageRequest = new MessageRequest("계정 차단 알림 봇", message);
        Map<String, Object> response = doorayMessengerClient.sendMessage(messageRequest);
        System.out.println(response);
    }
}