package com.app.security.filters;

import com.app.security.config.AuthUser;
import com.app.security.entities.User;
import com.app.security.respository.IUserRepository;
import com.app.security.service.AuthUserDetailsService;
import com.app.security.utilities.JwtUtility;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtility jwtUtility;

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if(authHeader!=null && authHeader.startsWith("Bearer")){
            String jwtToken = authHeader.replace("Bearer","").trim();
            /**1. Extract Username from token and fetch UserDetails from DB**/
            String userName = jwtUtility.getUserNameFromToken(jwtToken);
            Optional<User> userOptional = userRepository.findByEmail(userName);
            if(userOptional.isPresent()){
                User user = userOptional.get();
                AuthUser authUser = new AuthUser(user);
                /**2. Validate token with UserDetails**/
                boolean isTokenValid = jwtUtility.validateJwtToken(jwtToken,authUser);
                if(isTokenValid && SecurityContextHolder.getContext().getAuthentication()==null){
                    /**3. Based on UserDetails and its authorities create authToken and put it inside securityContextHolder**/
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(authUser,null,authUser.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        filterChain.doFilter(request,response);
    }
}
