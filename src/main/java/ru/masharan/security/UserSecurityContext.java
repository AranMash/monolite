package ru.masharan.security;

import ru.masharan.entity.User;

public class UserSecurityContext {

    private static final ThreadLocal<User> currentUser = new ThreadLocal<>();

    public static User getCurrentUser() {
        User user = currentUser.get();
        if (user == null) {
            throw new IllegalArgumentException("No user is currently signed in");
        }

        return user;
    }

    public static void setCurrentUser(User user) {
        currentUser.set(user);
    }

    public static boolean userSignIn() {
        return currentUser.get() != null;
    }

    public static void remove() {
        currentUser.remove();
    }
}
