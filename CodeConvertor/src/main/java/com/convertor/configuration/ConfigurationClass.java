package com.convertor.configuration;


import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;




@Configuration
public class ConfigurationClass {

    @Value("${openai.api.key}")
     String openaiApiKey;

    @Bean
    public RestTemplate template(){
        RestTemplate restTemplate=new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("Authorization", "Bearer " + openaiApiKey);
            return execution.execute(request, body);
        });
        return restTemplate;
    }
    
    
    @Bean
	public SecurityFilterChain springSecurityConfiguration(HttpSecurity http) throws Exception {

		http.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			
		.cors(cors ->{
			cors.configurationSource(new CorsConfigurationSource() {
				@Override
				public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
				CorsConfiguration configuration= new CorsConfiguration();
					configuration.setAllowedOriginPatterns(Collections.singletonList("*"));
					configuration.setAllowedMethods(Collections.singletonList("*"));
					configuration.setAllowCredentials(true);
					configuration.setAllowedHeaders(Collections.singletonList("*"));
					configuration.setExposedHeaders(Arrays.asList("Authorization"));
					return configuration;
				}
			});
		})
		.authorizeHttpRequests(auth ->{
			auth
			.anyRequest().permitAll();
				})
			.csrf(csrf -> csrf.disable())
			.formLogin(Customizer.withDefaults())
			.httpBasic(Customizer.withDefaults());
		return http.build();
	}
}