package miu.edu.service.Impl;

import lombok.AllArgsConstructor;
import miu.edu.domain.Member;
import miu.edu.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;


@AllArgsConstructor
public class MemberDetailsService   {
    //implements UserDetailsService
//    @Autowired
//    private MemberRepository memberRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        Optional<Member> optionalMember = memberRepository.findByEmail(email);
//
//        optionalMember.orElseThrow(() -> new UsernameNotFoundException("Invalid email or password"));
//
//        return optionalMember.map(MemberDetails::new).get();
    }
    //implements UserDetailsService
//    private final UserRepository userRepository;
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<User> user = userRepository.findByUserName(username);
//
//        return org.springframework.security.core.userdetails.User.builder()
//                .username(user.get().getUsername())
//                .password(user.get().getPassword())
//                .authorities(user.get().getRoles().stream()
//                        .map(role -> new SimpleGrantedAuthority(role.getRoleType().name()))
//                        .collect(Collectors.toList()))
//                .build();}


