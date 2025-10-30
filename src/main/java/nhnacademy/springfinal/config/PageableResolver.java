package nhnacademy.springfinal.config;

import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

public class PageableResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(Pageable.class); // 페이저블로 인자가 넘어옴
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {

        String pageParam = webRequest.getParameter("page");
        String sizeParam = webRequest.getParameter("size");

        int size = sizeParam == null ? 5 : Integer.parseInt(sizeParam);
//        int page = pageParam == null ? 0 : Integer.parseInt(pageParam);
        int page = 0;
        if(pageParam == null) {
            page = 0;
        } else if (Integer.parseInt(pageParam) > 5) {
            page = 5;
        }
        return PageRequest.of(page, size);
        // ---> 나중에 수정하기

    }
}
