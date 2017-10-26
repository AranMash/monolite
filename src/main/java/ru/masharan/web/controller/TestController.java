package ru.masharan.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class TestController {

    @Autowired
    private ConnectionFactoryLocator factoryLocator;

    @GetMapping(path = "/test")
    public Set<String> test() {
        return factoryLocator.registeredProviderIds();
    }
}
