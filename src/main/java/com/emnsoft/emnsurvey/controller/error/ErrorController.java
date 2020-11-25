package com.emnsoft.emnsurvey.controller.error;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController{

    private static final String PATH = "/error";

    @RequestMapping(PATH)
    public ResponseEntity<Void> handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if(status != null) {
            HttpStatus st = HttpStatus.valueOf(Integer.valueOf(status.toString()));
            switch (st) {
                case NOT_FOUND:
                    return ResponseEntity.notFound().build();
                default:
                    return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
    
}
