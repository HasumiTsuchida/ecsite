package jp.co.rakus.ecommers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import jp.co.rakus.ecommers.service.LoginAdminUserService;
import jp.co.rakus.ecommers.service.LoginUserService;

/**
 * ログイン認証用JavaConfig.
 * @author kohei.sakata
 *
 */
@Configuration
@EnableWebMvcSecurity
public class SecurityConfig {

    /**
     * 管理者認証用JavaConfig.
     * @author kohei.sakata
     *
     */
    @Configuration
    @Order(1)
    public static class AdminUserSecurityConfiguration extends WebSecurityConfigurerAdapter {
        @Autowired
        private LoginAdminUserService loginAdminUserService;
        @Override
        public void configure(WebSecurity web) throws Exception {
        	web.ignoring().antMatchers("/css/**", "/img/**");
        }
        @Override
        public void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(loginAdminUserService).passwordEncoder(new BCryptPasswordEncoder());
        }
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
            	.antMatcher("/administer/**")
                .authorizeRequests()
                    .anyRequest().hasRole("ADMIN")
                .and()
                .formLogin()
                	.loginProcessingUrl("/administer/login")
                	.loginPage("/administer/loginForm")
//                	.failureUrl("/administer/loginForm?error")
                	.failureUrl("/administer/loginError")
                	.defaultSuccessUrl("/administer/top",true)
                	.usernameParameter("email")
                	.passwordParameter("password")
                	.permitAll()
                .and()
                .logout()
                	.logoutRequestMatcher(new AntPathRequestMatcher("/administer/logout**"))
                	.logoutSuccessUrl("/administer/loginForm")
                ;
        }
    }
    
    /**
     * ユーザー認証用JavaConfig.
     * @author kohei.sakata
     *
     */
    @Configuration
    @Order(2)
    public static class UserSecurityConfiguration extends WebSecurityConfigurerAdapter {
        @Autowired
        private LoginUserService loginUserService;
        @Override
        public void configure(WebSecurity web) throws Exception {
        	web.ignoring().antMatchers("/css/**", "/img/**");
        }
        @Override
        public void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(loginUserService).passwordEncoder(new BCryptPasswordEncoder());
        }
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
            	.antMatcher("/user/**")
                .authorizeRequests()
                    .anyRequest().hasRole("USER")
//                    .permitAll()
                .and()
                .formLogin()
                	.loginProcessingUrl("/user/login")
                	.loginPage("/user/loginForm")
//                	.failureUrl("/user/loginForm?error")
                	.failureUrl("/user/loginError")
                	.defaultSuccessUrl("/user/",true)
                	.usernameParameter("email")
                	.passwordParameter("password")
                	.permitAll()
                .and()
                .logout()
                	.logoutRequestMatcher(new AntPathRequestMatcher("/logout**"))
                	.logoutSuccessUrl("/user/")
                ;
        }
    }
}