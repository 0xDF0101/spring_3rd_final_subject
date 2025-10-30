package nhnacademy.springfinal.controller;

import nhnacademy.springfinal.model.MemberEntity;
import nhnacademy.springfinal.model.MemberRequest;
import nhnacademy.springfinal.model.MemberResponse;
import nhnacademy.springfinal.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

//    @GetMapping("/members") // 전체 조회
//    public ResponseEntity getMembers() {
//        List<MemberResponse> memberList = memberService.getMembers();
//        return ResponseEntity.ok().body(memberList);
//    }

    @GetMapping("/members")
    public List<MemberResponse> getMembers(Pageable pageable){

        int size = pageable.getPageSize();
        long offset = pageable.getOffset();

        List<MemberResponse> allMember = memberService.getMembers();

        return allMember.stream()
                .skip(offset)
                .limit(size)
                .collect(Collectors.toList());
    }
}
