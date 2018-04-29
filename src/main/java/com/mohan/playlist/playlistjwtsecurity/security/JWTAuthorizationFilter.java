package com.mohan.playlist.playlistjwtsecurity.security;

import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static com.mohan.playlist.playlistjwtsecurity.constant.SecurityConstants.HEADER_STRING;
import static com.mohan.playlist.playlistjwtsecurity.constant.SecurityConstants.SECRET;
import static com.mohan.playlist.playlistjwtsecurity.constant.SecurityConstants.TOKEN_PREFIX;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
	
    public JWTAuthorizationFilter(final AuthenticationManager authManager) {
        super(authManager);
    }
    
    @Override
    protected void doFilterInternal(final HttpServletRequest req,
                                    final HttpServletResponse res,
                                    final FilterChain chain) throws IOException, ServletException {
        final String header = req.getHeader(HEADER_STRING);
        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }
        final UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }
    
    private UsernamePasswordAuthenticationToken getAuthentication(final HttpServletRequest request) {
        final String token = request.getHeader(HEADER_STRING);
        UsernamePasswordAuthenticationToken userInfo = null;
        if (token != null) {
            // parse the token.
            final String user = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();
            if (user != null) {
            	userInfo = new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            }
        }
        return userInfo;
    }
}