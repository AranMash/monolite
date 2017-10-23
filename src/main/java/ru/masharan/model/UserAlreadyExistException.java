package ru.masharan.model;

public class UserAlreadyExistException extends Exception {
    public UserAlreadyExistException(String s) {
        super(s);
    }
}
