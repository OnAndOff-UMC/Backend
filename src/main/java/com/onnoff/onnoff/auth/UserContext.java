package com.onnoff.onnoff.auth;

import com.onnoff.onnoff.domain.user.User;

public class UserContext {
    private static final ThreadLocal<User> authenticatedUser = new ThreadLocal<>();

    public static void setUser(User user){
        authenticatedUser.set(user);
    }
    public static User getUser(){
        return authenticatedUser.get();
    }
    public static void clearUser(){
        authenticatedUser.remove();
    }
}
