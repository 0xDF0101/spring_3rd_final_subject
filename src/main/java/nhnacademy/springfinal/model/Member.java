package nhnacademy.springfinal.model;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Member {
    private String id;
    private String name;
    private String password;
    private Integer age;
    private Role role;
}
