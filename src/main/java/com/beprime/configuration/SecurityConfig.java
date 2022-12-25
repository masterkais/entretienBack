package com.beprime.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/login/**", "/swagger-ui/index.html").permitAll();
        http.authorizeRequests().antMatchers("/api/**/**").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/api/group/**").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/api/user/**").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/api/vehicule/**").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/api/brand/**").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/api/category/**").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/api/rowMaterial/**").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/api/image/**").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/api/product/**").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/api/brand/**").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/api/entryVoucher/**").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/api/exitVoucher/**").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/api/requestTransfert/**").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/api/statistique/**").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/api/salesorder/**").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/actuator/**").permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(new JWTAuthenticationFilter(authenticationManager()));
        http.addFilterBefore(new JWTAuthorizationFiltre(), UsernamePasswordAuthenticationFilter.class);
    }
}
