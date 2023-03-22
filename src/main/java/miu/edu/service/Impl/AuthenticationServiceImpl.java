package miu.edu.service.Impl;


import miu.edu.domain.Member;
import miu.edu.domain.Role;
import miu.edu.dto.AuthDTO.AuthLoginDTO;
import miu.edu.dto.AuthDTO.AuthRequestDTO;
import miu.edu.dto.AuthDTO.AuthResponseDTO;
import miu.edu.repository.MemberRepository;
import miu.edu.service.AuthenticationService;
import miu.edu.service.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public AuthResponseDTO register(AuthRequestDTO authRequestDTO) {
       // var roles = Arrays.asList(Role.builder().name("User").build(), Role.builder().name("Admin").build());

        //TODO Use model mapper
        var user = Member.builder()
                .firstname(authRequestDTO.getFirstname())
                .lastname(authRequestDTO.getLastname())
                .email(authRequestDTO.getEmail())
                .password(passwordEncoder.encode(authRequestDTO.getPassword()))
               // .roleTypes(roles)
                .build();
        memberRepository.save(user);
        var jwtToken = jwtTokenUtil.generateAccessToken(user);
        AuthResponseDTO responseDTO = AuthResponseDTO.builder()
                .token(jwtToken)
                .build();
        return responseDTO;
    }

    @Override
    public AuthResponseDTO authenticate(AuthLoginDTO authLoginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authLoginDto.getEmail(),
                        authLoginDto.getPassword()
                )
        );


        var user = memberRepository.findByEmail(authLoginDto.getEmail())
                .orElseThrow();
        if (!passwordEncoder.matches(authLoginDto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        } else {
            var jwtToken = jwtTokenUtil.generateAccessToken(user);
            AuthResponseDTO authResponseDTO = AuthResponseDTO.builder()
                    .id(user.getId())
                    .firstName(user.getFirstname())
                    .lastName(user.getLastname())
                    .email(user.getEmail())
                    .password(passwordEncoder.encode(user.getPassword()))
                    .token(jwtToken)
                    // .roleTypes(roles)
                    .build();


            return authResponseDTO;
        }

    }
}
