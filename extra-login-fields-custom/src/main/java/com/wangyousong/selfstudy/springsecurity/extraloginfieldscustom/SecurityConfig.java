package com.wangyousong.selfstudy.springsecurity.extraloginfieldscustom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author Administrator
 */
@EnableWebSecurity
@PropertySource("classpath:/application-extrafields.properties")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(CustomUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/css/**", "/index").permitAll()
                .antMatchers("/user/**").authenticated()
                .and()
                .formLogin().loginPage("/login")
                .and()
                .logout()
                .logoutUrl("/logout");
    }

    public CustomAuthenticationFilter authenticationFilter() throws Exception {
        CustomAuthenticationFilter filter = new CustomAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManagerBean());
        filter.setAuthenticationFailureHandler(failureHandler());
        return filter;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authProvider());
    }

    public AuthenticationProvider authProvider() {
        return new CustomUserDetailsAuthenticationProvider(passwordEncoder, userDetailsService);
    }

    public SimpleUrlAuthenticationFailureHandler failureHandler() {
        return new SimpleUrlAuthenticationFailureHandler("/login?error=true");
    }

}
