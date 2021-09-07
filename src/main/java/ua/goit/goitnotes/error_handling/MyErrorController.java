package ua.goit.goitnotes.error_handling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
public class MyErrorController implements ErrorController {
    @RequestMapping("/error")
    @ExceptionHandler(Throwable.class)
    public ModelAndView renderErrorPage(Throwable exception) {
        log.error("An error happened", exception);
        ModelAndView errorPage = new ModelAndView("errorPage");
        String errorMsg = "Something went wrong. Please, check your request and try again later. ";
        errorMsg = errorMsg + (exception.getMessage() != null ? exception.getMessage() : "");
        errorPage.addObject("errorMsg", errorMsg);
        return errorPage;
    }
}
