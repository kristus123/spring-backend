package com.example.demo.configurations;


import com.example.demo.services.userAuth.UserDetailsServiceImp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


//configure(HttpSecurity security)       security.httpBasic().disable();

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService() { return new UserDetailsServiceImp(); };

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //Her er koden fra den guiedn som setter opp JPA-auth
        http.authorizeRequests()
                .and().csrf().disable()
                .authorizeRequests()
                    .antMatchers("/api/public/**").permitAll()
                    .antMatchers("/v1/users/signup").permitAll()
                    .antMatchers("/api/authenticate").permitAll()
                    .antMatchers("/hello").permitAll()
                    .antMatchers("/v1/admin/**").permitAll() //.hasRole("ADMINISTRATOR")
                    .anyRequest().authenticated()
                    .and().addFilter(new JwtAuthenticationFilter(authenticationManager()))
                    .addFilter(new JwtAuthorizationFilter(authenticationManager()))
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);


        //http.cors().and()
        //        .csrf().disable()
        //        .authorizeRequests()
        //            .antMatchers("/api/public").permitAll()
        //            .antMatchers("/v1/users/signup").permitAll()
        //            .antMatchers("/api/authenticate").permitAll()
        //            .antMatchers("/hello").permitAll()
        //            .anyRequest().authenticated()
        //            .and().addFilter(new JwtAuthenticationFilter(authenticationManager()))
        //            .addFilter(new JwtAuthorizationFilter(authenticationManager()))
        //            .sessionManagement()
        //            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());

        //auth.inMemoryAuthentication()
        //        .withUser("user")
        //        .password(passwordEncoder().encode("password"))
        //        .authorities("ROLE_USER");
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());

        return source;
    }
}