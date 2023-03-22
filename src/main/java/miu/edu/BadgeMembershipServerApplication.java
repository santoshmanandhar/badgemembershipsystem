package miu.edu;


import lombok.RequiredArgsConstructor;
import miu.edu.domain.Member;
import miu.edu.domain.Role;
import miu.edu.repository.MemberRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;


@SpringBootApplication
@RequiredArgsConstructor
public class BadgeMembershipServerApplication implements CommandLineRunner {
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    public static void main(String[] args) {
        SpringApplication.run(BadgeMembershipServerApplication.class, args);
        System.out.println("Server is running!!!!!!");

    }


//    @Autowired
//    private  UserRepository userRepository;

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Override
    public void run(String... args) throws Exception {
        //userRepository.deleteAll();
        Set<Role> roles = Set.of(new Role("ADMIN"));
        Set<Role> roles1 = Set.of(new Role("FACULTY"));
        memberRepository.deleteAll();
        Member faculty = new Member();
        faculty.setEmail("bijy@example.com");
        faculty.setFirstname("Faculty");
        faculty.setLastname("User");
        faculty.setPassword(passwordEncoder.encode("faculty123"));
        faculty.setRoleTypes(roles1);
        memberRepository.save(faculty);
        Member admin = new Member();
        admin.setEmail("faculty@example.com");
        admin.setFirstname("Faculty");
        admin.setLastname("User");
        admin.setPassword(passwordEncoder.encode("faculty123"));
        admin.setRoleTypes(roles);
        memberRepository.save(admin);
        System.out.println("Server is running!!!!!!");

    }
}
