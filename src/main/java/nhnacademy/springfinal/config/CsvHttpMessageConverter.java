package nhnacademy.springfinal.config;

import nhnacademy.springfinal.model.Member;
import nhnacademy.springfinal.model.MemberResponse;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class CsvHttpMessageConverter extends AbstractHttpMessageConverter<MemberResponse> {

    public CsvHttpMessageConverter() {
        super(new MediaType("text", "csv"));
    }

    @Override
    protected boolean supports(Class<?> clazz) { // Member 클래스를 다룰 수 있다.
        return MemberResponse.class.isAssignableFrom(clazz);
    }

    @Override
    protected  MemberResponse readInternal(Class<? extends MemberResponse> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        //TODO Not Supported Type
        return null;
    }

    @Override
    protected boolean canRead(MediaType mediaType) {
        return false;
    }

    @Override
    protected void writeInternal(MemberResponse memberResponse
            , HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {

        outputMessage.getHeaders().setContentType(MediaType.valueOf("text/csv; charset=UTF-8"));
        try (Writer writer = new OutputStreamWriter(outputMessage.getBody())) {
            writer.write("id,name,age,role");
            writer.write("\n");

            writer.write(String.join(",",
                    memberResponse.getId(),
                    memberResponse.getName(),
                    String.valueOf(memberResponse.getAge()),
                    String.valueOf(memberResponse.getRole())
            ));
            writer.flush();
        }

        return;
    }


}