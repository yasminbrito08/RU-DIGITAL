package com.example.rudigitalapi.users.core.share;

public class current_user_details {
    private static final ThreadLocal<CurrentUser> CONTEXT = new ThreadLocal<>();

    public static void setContext(CurrentUser user){
        CONTEXT.set(user);
    }
    public static CurrentUser getContext(){
        return CONTEXT.get();
    }
    public static void clearContext(){
        CONTEXT.remove();
    }
}