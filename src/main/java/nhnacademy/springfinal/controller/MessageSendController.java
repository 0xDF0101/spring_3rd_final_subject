package nhnacademy.springfinal.controller;

import lombok.RequiredArgsConstructor;
import nhnacademy.springfinal.model.Member;
import nhnacademy.springfinal.model.MemberResponse;
import nhnacademy.springfinal.service.MemberService;
import nhnacademy.springfinal.service.MessengerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MessageSendController {

    private final MemberService memberService;
    private final MessengerService messengerService;

    // 메세지 테스트
    @PostMapping("/send/{id}")
    public void sendMessage(@PathVariable String id) {
        MemberResponse getMember = memberService.getMember(id);
        Member member = new Member(getMember.getId(), getMember.getName(), getMember.getPassword(), getMember.getAge(), getMember.getRole());
        messengerService.sendMessenger();
    }
}
