package com.springboot.blog.springbootblogrestapi.config;

import com.springboot.blog.springbootblogrestapi.security.JwtAuthenticationEntryPoint;
import com.springboot.blog.springbootblogrestapi.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//this class is for basic authentication only
//no form login will be there
//only basic authentication
//while testing via postman add password and username in authorization and do GET,POST,etc(any request)

@Configuration
//required below annotation to allow access to methods by roles
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private UserDetailsService userDetailsService;
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private JwtAuthenticationFilter authenticationFilter;
    public SecurityConfig(UserDetailsService userDetailsService,JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                          JwtAuthenticationFilter authenticationFilter) {
        this.jwtAuthenticationEntryPoint=jwtAuthenticationEntryPoint;
        this.userDetailsService = userDetailsService;
        this.authenticationFilter=authenticationFilter;
    }


    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }
    //database authentication method (DAO authentication
    // )


    //encrypt the password using BCrypt

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(csrf -> csrf.disable())
                //using below line will impose authentication to all requests
                //.authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated())
                //using below line will impose authentication to all requests except GET (put,POST,DELETE -> will require authentication)
                .authorizeHttpRequests((authorize) ->
                        authorize.requestMatchers(HttpMethod.GET,"/api/**").permitAll()
                                .requestMatchers("/api/auth/**").permitAll()
                                .requestMatchers("/swagger-ui/**").permitAll()
                                .requestMatchers("/v3/api-docs/**").permitAll()
                                .anyRequest()

                                .authenticated()).httpBasic(Customizer.withDefaults()
                ).exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint)
                ).sessionManagement(session ->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return  http.build();
    }
    //defining various user roles for application

   //basic authentication method
//    @Bean
//    public UserDetailsService userDetailsService(){
//        UserDetails pinal= User.builder()
//                .username("pinal")
//                .password(passwordEncoder().encode("pinal"))
//                .roles("USER")
//                .build();
//
//        UserDetails admin=User.builder()
//                .username("admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(pinal,admin);
//    }


}
