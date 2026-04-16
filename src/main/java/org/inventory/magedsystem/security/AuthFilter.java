package org.inventory.magedsystem.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthFilter extends OncePerRequestFilter {
    private final  JWTUtils jwtUtils;
    private final CustomUserDetailService customUserDetailService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String token =getTokenFromRequest(request);
        if(token!=null){
            String email=jwtUtils.getusernameFromToken(token);
            UserDetails userDetails=customUserDetailService.loadUserByUsername(email);
            if(StringUtils.hasText(email)&&jwtUtils.isTokenValid(token,userDetails)){

                log.info("User token {} is authenticated",email);
                UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(
                        userDetails,null,userDetails.getAuthorities()
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        try {
            filterChain.doFilter(request, response);

        }catch (Exception e){
            log.error("error occuerd in AuthFilter: {}",e.getMessage());
        }

    }
    private String getTokenFromRequest(HttpServletRequest request){
        String tokenWithBearer =request.getHeader("Authorization");
        if(tokenWithBearer!=null&&tokenWithBearer.startsWith("Bearer ")){
            return tokenWithBearer.substring(7);
        }
        return null;
    }
}
