package miu.edu.controller;


import lombok.RequiredArgsConstructor;
import miu.edu.dto.AuthDTO.AuthLoginDTO;
import miu.edu.dto.AuthDTO.AuthRequestDTO;
import miu.edu.service.Impl.AuthenticationServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthController {


    private final AuthenticationServiceImpl authenticationService;


    @PostMapping("/login/authenticate")
    public ResponseEntity<?> login(@RequestBody @Valid AuthLoginDTO authLoginDTO) {
        return ResponseEntity.ok(authenticationService.authenticate(authLoginDTO));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequestDTO authRegisterDto) {
        return ResponseEntity.ok(authenticationService.register(authRegisterDto));
    }

    @GetMapping("/")
    public String test3() {
        return "asd";

    }
}
