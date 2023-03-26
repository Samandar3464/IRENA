package com.example.irena.exception.handle;

import com.example.irena.exception.RecordAlreadyExistException;
import com.example.irena.exception.RecordNotFountException;
import com.example.irena.exception.UserNotFountException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
@Controller
@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(RecordNotFountException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String recordNotFound(Exception e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "404";
    }
    @ExceptionHandler(RecordAlreadyExistException.class)
    @ResponseStatus(HttpStatus.ALREADY_REPORTED)
    String recordAlreadyExistFound(Exception e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "404";
    }
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String badRequest(Exception e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "404";
    }
    @ExceptionHandler(UserNotFountException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String userNotFound(Exception e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "404";
    }

}
