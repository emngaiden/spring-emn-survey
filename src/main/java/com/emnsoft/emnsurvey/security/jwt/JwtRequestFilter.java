package com.emnsoft.emnsurvey.security.jwt;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.emnsoft.emnsurvey.config.Constants;
import com.emnsoft.emnsurvey.config.Constants.IgnoredEndpoint;
import com.emnsoft.emnsurvey.service.JwtUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private final RequestMatcher[] ignoredMatchers = new AntPathRequestMatcher[Constants.IGNORED_SECURITY_ENDPOINTS.length];

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    public JwtRequestFilter() {
        super();
        int c = 0;
        for (IgnoredEndpoint endpoint : Constants.IGNORED_SECURITY_ENDPOINTS) {
            ignoredMatchers[c] = new AntPathRequestMatcher(endpoint.getEndpoint());
            c++;
        }
    }

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        boolean m = false;
        for (RequestMatcher requestMatcher : ignoredMatchers) {
            if(requestMatcher.matches(request)) {
                m = true;
                break;
            }
        }
        if(!m) {
            final String requestTokenHeader = request.getHeader("Authorization");
            String username = null;
            String jwtToken = null;
            if(requestTokenHeader != null){
                // JWT Token is in the form "Bearer token". Remove Bearer word and get
                // only the Token
                if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
                    jwtToken = requestTokenHeader.substring(7);
                    try {
                        username = jwtTokenUtil.getUsernameFromToken(jwtToken);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Unable to get JWT Token");
                    } catch (ExpiredJwtException e) {
                        System.out.println("JWT Token has expired");
                    }
                } else {
                    logger.warn("JWT Token does not begin with Bearer String");
                }
            } else {
                logger.warn("No Authorization header was provided");
            }
            

            // Once we get the token validate it.
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
                // if token is valid configure Spring Security to manually set
                if (jwtTokenUtil.validateToken(jwtToken, userDetails.getUsername())) {

                    // TODO: Add implementation for authorities
                    //UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, new ArrayList<GrantedAuthority>());
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    // After setting the Authentication in the context, we specify
                    // that the current user is authenticated. So it passes the
                    // Spring Security Configurations successfully.
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        }
		chain.doFilter(request, response);
	}

}