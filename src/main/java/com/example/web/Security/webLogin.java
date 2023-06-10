package com.example.web.Security;

import com.example.web.Security.authentication.JwtAccessDeniedHandler;
import com.example.web.Security.authentication.JwtAuthenticationEntryPoint;
import com.example.web.Security.authentication.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class webLogin extends WebSecurityConfigurerAdapter {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final JwtAuthenticationEntryPoint authEntrypoint;
	private final JwtAccessDeniedHandler accessDeniedHandler;

	public webLogin(JwtAuthenticationFilter jwtAuthenticationFilter,
					JwtAuthenticationEntryPoint authEntrypoint,
					JwtAccessDeniedHandler accessDeniedHandler) {
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
		this.authEntrypoint = authEntrypoint;
		this.accessDeniedHandler = accessDeniedHandler;
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.exceptionHandling().authenticationEntryPoint(authEntrypoint)
				.and().exceptionHandling().accessDeniedHandler(accessDeniedHandler)
				.and().authorizeRequests()
				.antMatchers("/api/auth/**","/api/authenticate","/api/public/**").permitAll()
				.antMatchers("/api/admin/**").hasAnyRole("ADMIN")
				.anyRequest().authenticated();
		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManagerBean();
	}

}
