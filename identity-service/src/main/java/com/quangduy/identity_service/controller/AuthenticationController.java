package com.quangduy.identity_service.controller;

import java.text.ParseException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.JOSEException;
import com.quangduy.identity_service.dto.request.AuthenticationRequest;
import com.quangduy.identity_service.dto.request.IntrospectRequest;
import com.quangduy.identity_service.dto.response.ApiResponse;
import com.quangduy.identity_service.dto.response.AuthenticationResponse;
import com.quangduy.identity_service.dto.response.IntrospectReponse;
import com.quangduy.identity_service.service.AuthenticationService;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/auth")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationController {
    AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/token")
    public ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse result = this.authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/introspect")
    public ApiResponse<IntrospectReponse> authenticate(@RequestBody IntrospectRequest request)
            throws JOSEException, ParseException {
        IntrospectReponse result = this.authenticationService.introspect(request);
        return ApiResponse.<IntrospectReponse>builder()
                .result(result)
                .build();
    }
}
