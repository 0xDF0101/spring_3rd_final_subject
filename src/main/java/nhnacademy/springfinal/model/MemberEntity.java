package nhnacademy.springfinal.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MemberEntity {
    private String id;
    private String name;
    private String password;
    private Integer age;
    private Role role;

    public MemberEntity(MemberRequest memberRequest, String password) {
        this.id = memberRequest.getId();
        this.name = memberRequest.getName();
        this.age = memberRequest.getAge();
        this.role = memberRequest.getRole();
        this.password = password; // 해싱된 비밀번호
    }
}
