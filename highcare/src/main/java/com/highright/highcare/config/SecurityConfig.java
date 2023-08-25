package com.highright.highcare.config;

import com.highright.highcare.jwt.JwtAccessDeniedHandler;
import com.highright.highcare.jwt.JwtAuthenticationEntryPoint;
import com.highright.highcare.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity  // 시큐리티 활성화
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint; // 403 code
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;            // 401 code

    // 시큐리티 설정무시 정적 리소스 빈 등록
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return web -> web.ignoring().antMatchers("/css/**", "/js/**", "/images/**",
                "/lib/**");
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // http 요청 권한설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
            .and()
                .authorizeRequests()
                .antMatchers("/").authenticated()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/admin/**").hasRole("ADMIN")    // 관리자 - 시스템운영담당자만 접근 가능
                .antMatchers("/api/**").hasAnyRole("USER", "MANAGER", "ADMIN") //일반 회원 이상 접근 가능
//                .antMatchers("/api/**").hasRole("MANAGER")   // 매니저- 각 부서 부장급 접근 가능
// .anyRequest().permitAll()   // 테스트 후 삭제
            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .cors()
            .and()
                .apply(new JwtSecurityConfig(tokenProvider)) // jwt시큐리티설정파일 적용하기
                ;
                // oauth2 추가하기
        return http.build();
    }

    // cors config
    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:8080"));
        config.setAllowedMethods(Arrays.asList("GET", "PUT", "POST","OPTIONS", "DELETE"));
        config.setAllowedHeaders(Arrays.asList("Access-Control-Allow-Origin", "Content-type"
                , "Access-Control-Allow-Headers", "Authorization", "Access-Control-Allow-Credentials"
                , "X-Requested-With", " application/json"));
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }



}
