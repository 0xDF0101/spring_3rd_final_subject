package nhnacademy.springfinal.service;

import nhnacademy.springfinal.model.MessageRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(
        name = "dooray-messenger",
        url = "https://nhnacademy.dooray.com/services/3204376758577275363/4190915706678802424/h6Y2F2isRUWrYyAYQVEEbg"

) // FeignClient를 인터페이스로 정의만 하면 , url,http,request body, post 요청을 자동으로 해줌!
public interface DoorayMessengerClient {

    @PostMapping
    Map<String, Object> sendMessage(@RequestBody MessageRequest request);
}