package nhnacademy.springfinal.controller;

import nhnacademy.springfinal.model.MemberEntity;
import nhnacademy.springfinal.model.MemberRequest;
import nhnacademy.springfinal.model.MemberResponse;
import nhnacademy.springfinal.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/members")
    public ResponseEntity createMember(@RequestBody MemberRequest memberRequest) {

        memberService.createMember(memberRequest);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/members/{id}")
    public ResponseEntity getMember(@PathVariable String id) {
        MemberResponse memberResponse = memberService.getMember(id);

        return ResponseEntity.ok().body(memberResponse);
    }
}
