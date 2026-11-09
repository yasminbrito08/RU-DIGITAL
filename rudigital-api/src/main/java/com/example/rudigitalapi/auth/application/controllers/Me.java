package com.example.rudigitalapi.auth.application.controllers;

import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.rudigitalapi.shared.exceptions.InvalidCredentialsException;
import com.example.rudigitalapi.users.core.share.CurrentUser;
import com.example.rudigitalapi.users.core.share.current_user_details;
import com.example.rudigitalapi.users.domain.enums.UserType;
import com.example.rudigitalapi.users.infra.queries.IGetUser;

@RestController
@RequestMapping("api/v1/auth/me")
public class Me {

    private final IGetUser getUser;

    public Me(IGetUser getUser) {
        this.getUser = getUser;
    }

    @GetMapping
    public UserInfo me() {
        CurrentUser userLogged = current_user_details.getContext();
        if (userLogged == null) {
            throw new InvalidCredentialsException();
        }

        IGetUser.UserIdentity userIdentity = getUser.byId(userLogged.getId())
                .orElseThrow(InvalidCredentialsException::new);

        return new UserInfo(userIdentity.id(), userIdentity.login(), userIdentity.userType());
    }

    record UserInfo(
            UUID userId,
            String login,
            UserType userType
    ) {
    }
}
