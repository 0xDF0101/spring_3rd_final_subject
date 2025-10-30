package nhnacademy.springfinal.config;

import nhnacademy.springfinal.model.MemberRequest; // ⭐️ MemberRequest DTO를 임포트
import nhnacademy.springfinal.model.Role; // MemberRequest에 Role Enum이 있다면 임포트
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.BufferedReader; // 입력 스트림을 효율적으로 읽기 위해 추가
import java.io.IOException;
import java.io.InputStreamReader; // 입력 스트림을 Reader로 변환하기 위해 추가
import java.nio.charset.StandardCharsets; // 문자셋 지정을 위해 추가 (UTF-8)

// ⭐️ CsvRequestConverter: CSV 요청을 MemberRequest 객체로 역직렬화하는 컨버터
// 제네릭 타입은 MemberRequest로 설정
public class CsvToMemberRequestConverter extends AbstractHttpMessageConverter<MemberRequest> {

    // 1. 생성자: 이 컨버터가 어떤 미디어 타입(text/csv)을 처리하는지 Spring에게 알림
    public CsvToMemberRequestConverter() {
        super(new MediaType("text", "csv"));
    }

    // 2. supports(Class<?> clazz): 이 컨버터가 어떤 타입의 객체를 다룰 수 있는지 정의
    // 클라이언트로부터 받은 CSV 데이터를 MemberRequest 클래스로 변환할 수 있다고 알림
    @Override
    protected boolean supports(Class<?> clazz) {
        return MemberRequest.class.isAssignableFrom(clazz);
    }

    // 3. readInternal(): 클라이언트 -> 서버 (역직렬화) 핵심 로직 구현
    // 클라이언트가 보낸 HTTP 요청 본문(CSV 데이터)을 읽어서 MemberRequest 객체로 파싱
    @Override
    protected MemberRequest readInternal(Class<? extends MemberRequest> clazz, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {

        // HTTP 요청 본문(InputStream)을 BufferedReader로 감싸서 라인 단위로 읽을 수 있도록 준비
        // StandardCharsets.UTF_8을 명시하여 문자셋 문제를 방지
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputMessage.getBody(), StandardCharsets.UTF_8))) {

            // CSV 데이터에서 헤더 라인(예: id,name,password,age,role)을 읽음.
            // 역직렬화 시에는 헤더 라인을 건너뛰는 것이 일반적이지만, 필요에 따라 유효성 검사나 매핑에 활용 가능.
            String headerLine = reader.readLine();

            // 실제 데이터 라인을 읽음 (예: user1,Alice,secure_pass,30,ADMIN)
            String dataLine = reader.readLine();

            // 데이터가 없거나 비어있으면 예외 발생 (Bad Request)
            if (dataLine == null || dataLine.trim().isEmpty()) {
                throw new HttpMessageNotReadableException("CSV data is empty or malformed for MemberRequest.", inputMessage);
            }

            // 쉼표(,)를 기준으로 데이터 라인을 분리
            String[] values = dataLine.split(",");

            // MemberRequest의 필드 수와 CSV 값의 수가 일치하는지 간단히 확인
            // 실제 MemberRequest의 필드 개수에 맞게 조정해야 함
            if (values.length < 5) { // 예: id,name,password,age,role 5개 필드라고 가정
                throw new HttpMessageNotReadableException("Not enough CSV fields for MemberRequest. Expected 5 fields.", inputMessage);
            }

            // 분리된 값들을 MemberRequest 객체의 필드에 매핑하여 객체 생성
            // ⭐️ 주의: CSV 값은 모두 String으로 파싱되므로, 각 필드 타입에 맞게 형변환 필요
            String id = values[0].trim();
            String name = values[1].trim();
            String password = values[2].trim();
            Integer age;
            try {
                age = Integer.parseInt(values[3].trim()); // String -> Integer 변환
            } catch (NumberFormatException e) {
                throw new HttpMessageNotReadableException("Invalid age format in CSV: " + values[3], (Throwable) inputMessage, (HttpInputMessage) e);
            }
            Role role;
            try {
                // String -> Role Enum 변환 (대소문자 무시를 위해 toUpperCase() 사용 고려)
//                role = Role.valueOf(values[4].trim().toUpperCase());
                role = Role.fromString(values[4].trim()); // toUpperCase()는 fromString() 내부에서 처리됨
            } catch (IllegalArgumentException e) {
                throw new HttpMessageNotReadableException("Invalid role in CSV: " + values[4], (Throwable) inputMessage, (HttpInputMessage) e);
            }

            // 최종적으로 파싱된 값들로 MemberRequest 객체를 생성하여 반환
            // MemberRequest에 @AllArgsConstructor 또는 적절한 생성자가 필요
            return new MemberRequest(id, name, password, age, role);

        } catch (Exception e) {
            // 파싱 중 발생할 수 있는 모든 예외를 HttpMessageNotReadableException으로 감싸서 반환
            throw new HttpMessageNotReadableException("Could not read CSV for MemberRequest: " + e.getMessage(), (Throwable) inputMessage, (HttpInputMessage) e);
        }
    }


    // 4. canRead(MediaType mediaType): 이 컨버터가 특정 미디어 타입에 대해 읽기(역직렬화)를 지원하는지 여부
    // text/csv 미디어 타입을 읽을 수 있다고 Spring에게 알림
    @Override
    protected boolean canRead(MediaType mediaType) {
        // 클라이언트가 보낸 Content-Type이 "text/csv"와 호환되는지 확인
        return mediaType != null && mediaType.isCompatibleWith(new MediaType("text", "csv"));
    }

    // 5. writeInternal(): 서버 -> 클라이언트 (직렬화)
    // 이 컨버터는 요청(Request) 처리용이므로, 응답(Response) 직렬화 기능은 지원하지 않음
    // 따라서 이 메서드는 구현하지 않거나, HttpMessageNotWritableException을 발생시킬 수 있음
    @Override
    protected void writeInternal(MemberRequest memberRequest
            , HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        // Request 컨버터는 쓰기를 지원하지 않으므로, 이 메서드가 호출될 일은 없어야 함.
        // 만약 호출되면 에러를 던지거나 아무것도 하지 않도록 할 수 있습니다.
        throw new HttpMessageNotWritableException("This converter does not support writing (serialization) for MemberRequest.");
    }
}