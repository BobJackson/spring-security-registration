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


    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/login*", "/logout*", "/signin/**", "/signup/**", "/customLogin", "/old/registrationConfirm",
                        "/user/registration*", "/user/registration*", "/registrationConfirm*", "/expiredAccount*", "/registration*",
                        "/badUser*", "/user/resendRegistrationToken*", "/forgetPassword*", "/user/resetPassword*", "/user/savePassword*", "/updatePassword*",
                        "/user/changePassword*", "/emailError*", "/resources/**", "/old/user/registration*", "/successRegister*", "/qrcode*", "/user/enableNewLoc*").permitAll()
                .antMatchers("/invalidSession*").anonymous()
                .antMatchers("/user/updatePassword*").hasAuthority("CHANGE_PASSWORD_PRIVILEGE")
                .anyRequest().hasAuthority("READ_PRIVILEGE")
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/homepage.html")
                .failureUrl("/login?error=true")
                .failureHandler(authenticationFailureHandler)
                .permitAll()
                .and()
                .sessionManagement()
                .invalidSessionUrl("/invalidSession.html")
                .sessionFixation().none()
                .and()
                .logout()
                .invalidateHttpSession(false)
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
