package com.tastytown.backend.security.jwt;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tastytown.backend.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter{

    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response); // let spring security handles unauthorized access
                return;
            }

            String token = authHeader.substring(7);
            String userId = jwtUtils.getUserId(token);

            if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // extracting user Object for extracting roles
                var user = userRepository.findById(userId).orElseThrow();

                // extract role from the user Object
                var roles = List.of(new SimpleGrantedAuthority(user.getRole().toString()));

                // create authentication Object which contains the user info along with its role
                var authentication = new UsernamePasswordAuthenticationToken(user, null, roles);

                //store the authentication Object inside the Security Context
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            request.setAttribute("userId", userId);
            filterChain.doFilter(request, response); 
        } catch (Exception e) {
            var problemDetail = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
            problemDetail.setTitle("Invalid Token");
            problemDetail.setDetail(e.getMessage());

            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println(problemDetail);
        }
    }
    
}
