package com.city.snow.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private static final Integer MAX_PAYLOAD_LENGTH = 10_000;
  @Value("${urls.frontend.origins}")
  private String[] origins;

  private void sharedSecurityConfiguration(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .csrf(AbstractHttpConfigurer::disable)
        .cors(AbstractHttpConfigurer::disable)
        .sessionManagement(httpSecuritySessionManagementConfigurer -> {
          httpSecuritySessionManagementConfigurer
              .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });
  }

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return web -> web.ignoring().requestMatchers(permitAllPattern());
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    sharedSecurityConfiguration(httpSecurity);

    httpSecurity.httpBasic(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(authz -> authz
            .requestMatchers(HttpMethod.PATCH).denyAll()
            .requestMatchers(HttpMethod.HEAD).denyAll()
            .requestMatchers(HttpMethod.TRACE).denyAll()
            .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
            .requestMatchers("*")
            .permitAll()
            .anyRequest().authenticated()
        )
        .formLogin(AbstractHttpConfigurer::disable)
        .csrf(AbstractHttpConfigurer::disable)
        .logout(AbstractHttpConfigurer::disable)
        .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .headers(header -> header
            .addHeaderWriter(new StaticHeadersWriter("server", "None"))
            .httpStrictTransportSecurity(HeadersConfigurer.HstsConfig::disable)
            .xssProtection(HeadersConfigurer.XXssConfig::disable)
            .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
            .contentTypeOptions(HeadersConfigurer.ContentTypeOptionsConfig::disable)
        )
        .cors(withDefaults -> {
          withDefaults.configurationSource(corsConfigurationSource());
        });
    return httpSecurity.build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(List.of(origins));
    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(
        List.of("Content-Type", "X-Requested-With", "Authorization", "withCredentials",
            "Access-Control-Allow-Origin"));
    configuration.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  /**
   * Add URL that I'm going to permit
   *
   * @return Permitted URL
   */
  private RequestMatcher[] permitAllPattern() {
    return new RequestMatcher[]{
//        new AntPathRequestMatcher("/permitted-url"),
        PathRequest.toStaticResources().atCommonLocations()
    };
  }

  @Bean
  public CommonsRequestLoggingFilter logFilter() {
    CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
    filter.setIncludeQueryString(true);
    filter.setIncludePayload(true);
    filter.setMaxPayloadLength(MAX_PAYLOAD_LENGTH);
    filter.setIncludeHeaders(true);
    filter.setAfterMessagePrefix("REQUEST DATA : ");
    return filter;
  }
}
