package com.tinqinacademy.hotel.controllers;

import com.tinqinacademy.hotel.errorhandler.ErrorHandler;
import com.tinqinacademy.hotel.errorhandler.ErrorOutput;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(basePackageClasses = {HotelController.class, SystemController.class})
@Slf4j
@AllArgsConstructor
public class GlobalControllerExceptionHandler {
    private final ErrorHandler errorHandler;
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ModelAndView handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
//        log.error("Request {} raised MethodArgumentNotValidException {}", request.getRequestURL(), ex.getBody());
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("exception", ex);
//        StringBuilder info = new StringBuilder();
//        ex.getBindingResult()
//                .getAllErrors()
//                .forEach(
//                        (error) -> info.append(error.getDefaultMessage()).append("\n")
//                );
//        modelAndView.addObject("message", info);
//        modelAndView.addObject("url", request.getRequestURL());
//        modelAndView.setViewName("error");
//        return modelAndView;
//    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorOutput> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
                                                                             HttpServletRequest request) {
        log.error("Request {} raised {}", request.getRequestURL(), ex.getClass());

        return new ResponseEntity<>(errorHandler.handle(ex), HttpStatus.I_AM_A_TEAPOT);
    }
}
