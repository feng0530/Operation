package tw.idv.frank.common.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tw.idv.frank.common.handler.AccessDeniedHandlerImpl;
import tw.idv.frank.common.handler.AuthenticationEntryPointHandlerImpl;
import tw.idv.frank.common.handler.AuthenticationSuccessHandlerImpl;
import tw.idv.frank.common.filter.JwtAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter authFilter) throws Exception {

        http.authorizeHttpRequests(registry -> registry
                .requestMatchers("/csv/test",  "/crud.html", "/login.html", "/js/**", "/users/login").permitAll()
                .requestMatchers(HttpMethod.PUT, "/customer/update/**").hasAnyAuthority("ADMIN", "MANAGE")
                .requestMatchers(HttpMethod.POST, "/customer/add").hasAnyAuthority("ADMIN", "MANAGE")
                .requestMatchers(HttpMethod.DELETE, "/customer/delete/**").hasAuthority("ADMIN")
                .anyRequest().authenticated())
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin(successHandler -> successHandler
                        .successHandler(new AuthenticationSuccessHandlerImpl()))
                .exceptionHandling(exceptionHandle -> exceptionHandle
                        .accessDeniedHandler(new AccessDeniedHandlerImpl())
                        .authenticationEntryPoint(new AuthenticationEntryPointHandlerImpl())
                );
//                .formLogin(Customizer.withDefaults()); // 開啟內建登入畫面 (測試用途)
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(
            UserDetailsService userDetailsService,
            BCryptPasswordEncoder passwordEncoder
    ) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
