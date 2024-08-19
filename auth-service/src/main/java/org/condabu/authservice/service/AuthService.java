package org.condabu.authservice.service;

import org.condabu.authservice.entity.AuthRequest;
import org.condabu.authservice.entity.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class AuthService {

    @Value("${security.jwt.secret-key}")
    private String secret_token;

    @Value("${security.jwt.expiration-time}")
    private Long expiration_time;


    private final WebClient userServiceClient;

    @Autowired
    public AuthService(WebClient.Builder webClientBuilder) {
        this.userServiceClient = webClientBuilder.baseUrl("http://localhost:8082").build();
    }

    public Mono<AuthResponse> authenticate(AuthRequest authRequest){

        return userServiceClient.post()
                .uri("/api/v1/user/verify")
                .bodyValue(authRequest)
                .retrieve()
                .bodyToMono(Boolean.class)
                .flatMap(isValid -> {
        if (Boolean.TRUE.equals(isValid)) {
            String token = generateToken(authRequest.getUsername());

            return Mono.just(new AuthResponse(token, "Authorization Successful"));
        } else {
            return Mono.empty();
        }});


}

    private String generateToken(String username) {
        return "dummy-token-for" +username;
    }
    }
