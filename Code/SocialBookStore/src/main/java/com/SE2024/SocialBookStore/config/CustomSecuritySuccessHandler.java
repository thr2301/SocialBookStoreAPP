package com.SE2024.SocialBookStore.config;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Configuration
public class CustomSecuritySuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	 @Override
	    protected void handle(
	    		HttpServletRequest request, 
	    		HttpServletResponse response, 
	    		Authentication authentication)
	    throws java.io.IOException {
	        String targetUrl = determineTargetUrl(authentication);
	        if(response.isCommitted()) return;
	        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	        redirectStrategy.sendRedirect(request, response, targetUrl);
	    }

	    protected String determineTargetUrl(Authentication authentication){
	        String url = "/login?error=true";
	        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
	        List<String> roles = new ArrayList<String>();
	        
	        for(GrantedAuthority a : authorities){
	            roles.add(a.getAuthority());
	        }
	        
	        if(roles.contains("ADMIN")){
	            url = "/admin/dashboard";
	        }else if(roles.contains("USER")) {
	            url = "/app/home";
	        }
	        
	        return url;
	    }
}
