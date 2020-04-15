package com.example.securitydemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
public class ResourceOAuthSecurityConfiguration extends ResourceServerConfigurerAdapter {
	@Override
    public void configure(HttpSecurity http) throws Exception {
	       http
 	        .authorizeRequests().antMatchers("/secret","/journals").hasRole("USER").anyRequest().fullyAuthenticated();
    }
	   @Override
	   public void configure(ResourceServerSecurityConfigurer resources) {
	       resources.resourceId("https://graph.microsoft.com");
	   }
}
