package ru.masharan.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import ru.masharan.model.UserAlreadyExistException;
import ru.masharan.model.entity.EmailVerificationToken;
import ru.masharan.model.entity.User;
import ru.masharan.model.events.CompleteRegistrationEvent;
import ru.masharan.model.service.UserService;
import ru.masharan.web.UserForm;

import javax.validation.Valid;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class RegistrationController {

    private final static String USER_FORM = "userForm";

    @Autowired
    private UserService service;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @GetMapping("/")
    public String home(Map<String, Object> model) {
        model.put("message", "Hello World");
        model.put("title", "Hello Home");
        model.put("date", new Date());
        return "home";
    }

    @RequestMapping(value = "/registration", method = GET)
    public String showRegistrationForm(WebRequest request, Model model) {
        UserForm userForm = new UserForm();
        model.addAttribute(USER_FORM, userForm);
        return "registration";
    }

    @RequestMapping(value = "/registration", method = POST)
    public ModelAndView registerUserAccount(@ModelAttribute(USER_FORM) @Valid UserForm accountDto,
                                            BindingResult result, WebRequest request,
                                            Errors errors) {
        if (result.hasErrors()) {
            return new ModelAndView("registration", USER_FORM, accountDto);
        }
        User user = registerUser(accountDto, result);
        if (user != null) {
            publishRegistrationEvent(user, request);
        }
        return new ModelAndView("login", USER_FORM, accountDto);
    }

    private User registerUser(UserForm accountDto, BindingResult result) {
        try {
            return  service.createUserAccount(accountDto);
        } catch (UserAlreadyExistException e) {
            result.rejectValue("email", "message.regError");
        }
        return null;
    }

    private void publishRegistrationEvent(User user, WebRequest request) {
        eventPublisher.publishEvent(new CompleteRegistrationEvent(user, request.getLocale(), request.getContextPath()));
    }

    @RequestMapping(value = "/registrationConfirm", method = GET)
    public void confirmRegistration(WebRequest request, Model model, @RequestParam("token") String token) {
        Locale locale = request.getLocale();
        EmailVerificationToken verificationToken = null;
        if (verificationToken == null) {
            /*throw exception*/
        }
        // TODO: 25.10.2017 Redirect to login page if all ok
    }


}
