package miu.edu.service.jwt;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import miu.edu.domain.Member;
import miu.edu.domain.Role;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {


    private final JwtTokenUtil jwtUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (!hasAuthorizationBearer(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = getAccessToken(request);

        if (!jwtUtil.validateAccessToken(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        setAuthenticationContext(token, request);
        filterChain.doFilter(request, response);
    }

    //header if token if it return false, token is not valid
    private boolean hasAuthorizationBearer(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (ObjectUtils.isEmpty(header) || !header.startsWith("Bearer")) {
            return false;
        }

        return true;
    }

    private String getAccessToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String token = header.split(" ")[1].trim();
        return token;
    }

    private void setAuthenticationContext(String token, HttpServletRequest request) {
        UserDetails userDetails = getUserDetails(token);

        UsernamePasswordAuthenticationToken
                authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private UserDetails getUserDetails(String token) {




        Member userDetails = new Member();
        String[] jwtSubject = jwtUtil.getSubject(token).split(",");
        Claims claims = jwtUtil.parseClaims(token);
        //String subject = (String) claims.get(Claims.SUBJECT);
        String roles = (String) claims.get("roles");

        roles = roles.replace("[", "").replace("]", "");
        String[] roleNames = roles.split(",");

        for (String aRoleName : roleNames) {
            userDetails.addRole(new Role(aRoleName));
        }

        // String[] jwtSubject = subject.split(",");

        userDetails.setId(Long.parseLong(jwtSubject[0]));
        userDetails.setEmail(jwtSubject[1]);

        return userDetails;

//        Claims claims = jwtUtil.parseClaims(token);
//        String subject = (String) claims.get(Claims.SUBJECT);
//        //String[] jwtSubject = subject.split(",");
//        String roles = (String) claims.get("roles");
//
//        // Remove the square brackets from the roles string
//        roles = roles.replace("[", "").replace("]", "");
//
//        // Split the roles string into an array of role names
//        String[] roleNames = roles.split(",");
//
//        // Create a set of Role objects from the array of role names
//        Set<Role> rolesSet = new HashSet<>();
//        for (String roleName : roleNames) {
//            rolesSet.add(new Role(roleName.trim()));
//        }
//
//        // Set the roles set in the userDetails object
//        userDetails.setRoleTypes(rolesSet);
//
//        // Split the subject string into an array of values
//        String[] jwtSubject = subject.split(",");
//
//        // Set the id and email in the userDetails object
//        userDetails.setId(Long.parseLong(jwtSubject[0]));
//        userDetails.setEmail(jwtSubject[1]);
//
//        return userDetails;
        // User userDetails = new User();
//        Claims claims = jwtUtil.parseClaims(token);
//        String subject = (String) claims.get(Claims.SUBJECT);
//        String roles = (String) claims.get("roles");
//
//        roles = roles.replace("[", "").replace("]", "");
//        String[] roleNames = roles.split(",");
//
//                // Create a set of Role objects from the array of role names
//        Set<Role> rolesSet = new HashSet<>();
//        for (String roleName : roleNames) {
//            rolesSet.add(new Role(roleName.trim()));
//        }
//
//        // Set the roles set in the userDetails object
//        userDetails.setRoleTypes(rolesSet);
//
//        String[] jwtSubject = subject.split(",");
//
//        userDetails.setId(Long.parseLong(jwtSubject[0]));
//        userDetails.setEmail(jwtSubject[1]);
//
//        return userDetails;
    }
}

