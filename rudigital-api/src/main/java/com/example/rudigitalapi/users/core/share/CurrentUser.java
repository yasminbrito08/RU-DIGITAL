package com.example.rudigitalapi.users.core.share;

import java.util.UUID;

public class CurrentUser {
    private UUID userId;

    public CurrentUser(UUID userId){
        this.userId = userId;
    }

    public UUID getId(){
        return userId;
    }
}
