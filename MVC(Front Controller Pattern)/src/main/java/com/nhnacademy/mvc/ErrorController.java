package com.nhnacademy.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static javax.servlet.RequestDispatcher.*;
import static javax.servlet.RequestDispatcher.ERROR_REQUEST_URI;

public class ErrorController implements Command{
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //todo exception_type
        //todo message
        //todo exception
        //todo request_uri
        Class<?> exceptionType = (Class<?>) req.getAttribute(ERROR_EXCEPTION_TYPE);
        String errorMessage = (String) req.getAttribute(ERROR_MESSAGE);
        Throwable exception = (Throwable) req.getAttribute(ERROR_EXCEPTION);
        String requestUri = (String) req.getAttribute(ERROR_REQUEST_URI);

        req.setAttribute("exception_type", exceptionType != null ? exceptionType.getName() : null);
        req.setAttribute("message", errorMessage);
        req.setAttribute("exception", exception);
        req.setAttribute("request_uri", requestUri);

        //todo /error.jsp forward 처리
        return "/error.jsp.do";
    }
}
