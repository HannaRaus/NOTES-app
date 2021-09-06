package ua.goit.goitnotes.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.nio.file.AccessDeniedException;

@Controller
@Slf4j
public class MyErrorController implements ErrorController {
    @RequestMapping("/error")
    @ExceptionHandler(Throwable.class)
    public ModelAndView renderErrorPage(Throwable exception) {
        log.error("An error happened", exception);
        ModelAndView errorPage = new ModelAndView("errorPage");
        String errorMsg = "Something went wrong. Please, check your request and try again later. ";

        if(exception instanceof NullPointerException){
            errorMsg = errorMsg + ((NullPointerException) exception).getMessage();
        }else if(exception instanceof AccessDeniedException){
            errorMsg = errorMsg + ((AccessDeniedException) exception).getMessage();
        }else if(exception instanceof DataNotAvailableException){
            errorMsg = errorMsg + ((DataNotAvailableException) exception).getMessage();
        }else if(exception instanceof UserAlreadyExistException){
            errorMsg = errorMsg + ((UserAlreadyExistException) exception).getMessage();
        }else if( exception instanceof InvalidAccessTypeException){
            errorMsg = errorMsg + ((InvalidAccessTypeException) exception).getMessage();
        }else if(exception instanceof ObjectNotFoundException){
            errorMsg = errorMsg + ((ObjectNotFoundException) exception).getMessage();
        }else{
            errorMsg = errorMsg + (exception.getMessage() != null ? exception.getMessage() : "");
        }
        errorPage.addObject("errorMsg", errorMsg);
        return errorPage;
    }
}
