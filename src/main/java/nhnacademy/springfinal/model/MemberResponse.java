package nhnacademy.springfinal.model;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberResponse {
    private String id;
    private String name;
    private String password;
    // ---> 얘는 반환 안해도 되잖아
    private Integer age;
    private Role role;
}
