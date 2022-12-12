package com.backend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final String allPlayersUrl = "/player/getAllPlayers/**";
    private final String savePlayerUrl = "/player/savePlayer";
    private final String saveScheduleUrl = "/schedule/save/**";
    private final String loginUrl = "/login";
    private  final String getAllCourtsUrl = "/court/getAllCourts/**";

    private final String adminCourtSaveUrl = "court/save/**";
    private final String adminPlayerDeleteUrl = "/player/delete/**";
    private final String adminScheduleDeleteUrl = "/schedule/delete/**";
    private final String adminCourtDeleteUrl = "/court/delete/**";
    private final UserDetailsService userDetailsService;

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    };

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);

        http.authorizeRequests().antMatchers(allPlayersUrl,savePlayerUrl,
                saveScheduleUrl,loginUrl,getAllCourtsUrl).permitAll();

        http.authorizeRequests().antMatchers(POST,adminCourtSaveUrl,adminPlayerDeleteUrl,
                adminScheduleDeleteUrl,adminCourtDeleteUrl).hasAnyAuthority("ROLE_ADMIN");

        http.addFilter(new CustomAutenticationFilter(authenticationManagerBean()));
        http.addFilterBefore(new CustomAutorizationFilter(), UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }
}
