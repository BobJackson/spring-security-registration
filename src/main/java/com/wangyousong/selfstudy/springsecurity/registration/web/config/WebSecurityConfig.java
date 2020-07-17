package com.wangyousong.selfstudy.springsecurity.registration.web.config;

import com.wangyousong.selfstudy.springsecurity.registration.security.CustomAuthenticationFailureHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.annotation.Resource;

/**
 * @author Bob
 * @version 1.0
 * @date 2020/7/14 19:29
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private CustomAuthenticationFailureHandler authenticationFailureHandler;

    @Resource
    private AuthenticationSuccessHandler myAuthenticationSuccessHandler;


    @Override
    public void configure(final WebSecurity web) {
        web.ignoring().antMatchers("/resources/**");
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/login*", "/logout*", "/signin/**", "/signup/**", "/customLogin",
                        "/old/user/registration*", "/old/registrationConfirm",
                        "/old/user/resendRegistrationToken",
                        "/old/user/resetPassword*", "/old/user/changePassword*", "/old/user/savePassword",
                        "/user/registration*", "/user/registration*", "/registrationConfirm*", "/expiredAccount*", "/registration*",
                        "/badUser*", "/user/resendRegistrationToken*", "/forgetPassword*", "/user/resetPassword*", "/user/savePassword*", "/updatePassword*",
                        "/user/changePassword*", "/emailError*", "/resources/**", "/successRegister*", "/qrcode*", "/user/enableNewLoc*").permitAll()
                .antMatchers("/foos/**").hasIpAddress("11.11.11.11")
                .antMatchers("/invalidSession*").anonymous()
                .antMatchers("/user/updatePassword*").hasAuthority("CHANGE_PASSWORD_PRIVILEGE")
                .anyRequest().hasAuthority("READ_PRIVILEGE")
                .and()
                .formLogin()
                    .loginPage("/login")
                    .defaultSuccessUrl("/homepage.html")
                .failureUrl("/login?error=true")
                .successHandler(myAuthenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .permitAll()
                .and()
                .sessionManagement()
                .invalidSessionUrl("/invalidSession.html")
                .sessionFixation().none()
                .and()
                .logout()
                .invalidateHttpSession(true)
                    .logoutSuccessUrl("/logout.html?logSucc=true")
                    .deleteCookies("JSESSIONID")
                    .permitAll()
                .and()
                .headers().frameOptions().disable(); // this is needed to access the H2 db's console

        // @formatter:on
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(11);
    }

}
