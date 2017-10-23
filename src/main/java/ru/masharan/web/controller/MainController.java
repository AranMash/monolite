package ru.masharan.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import ru.masharan.model.UserAlreadyExistException;
import ru.masharan.web.UserForm;
import ru.masharan.model.service.UserService;

import javax.validation.Valid;
import java.util.Date;
import java.util.Map;

@Controller
public class MainController {

    @Autowired
    private UserService service;



    @GetMapping("/")
    public String home(Map<String, Object> model) {
        model.put("message", "Hello World");
        model.put("title", "Hello Home");
        model.put("date", new Date());
        return "home";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String showRegistrationForm(WebRequest request, Model model) {
        UserForm userForm = new UserForm();
        model.addAttribute("user", userForm);
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView registerUserAccount(@ModelAttribute("user") @Valid UserForm accountDto,
                                            BindingResult result, WebRequest request,
                                            Errors errors) {
        if (result.hasErrors()) {
            return new ModelAndView("registration", "user", accountDto);
        }
        registerUser(accountDto, result);
        return new ModelAndView("successRegister", "user", accountDto);
    }

    private void registerUser(UserForm accountDto, BindingResult result) {
        try {
            service.registerUserAccount(accountDto);
        } catch (UserAlreadyExistException e) {
            result.rejectValue("email","message.regError");
        }
    }


}
