package com.apps.quantitymeasurement.security;

import com.apps.quantitymeasurement.model.UserEntity;
import com.apps.quantitymeasurement.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private static final Logger logger= Logger.getLogger(OAuth2SuccessHandler.class.getName());

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    public OAuth2SuccessHandler(JwtTokenProvider jwtTokenProvider, UserRepository userRepository) {
        this.jwtTokenProvider= jwtTokenProvider;
        this.userRepository= userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,Authentication authentication) throws IOException {
        OAuth2User oauth2User= (OAuth2User) authentication.getPrincipal();

        String googleId= oauth2User.getAttribute("sub");
        String email= oauth2User.getAttribute("email");
        String name= oauth2User.getAttribute("name");
        String picture= oauth2User.getAttribute("picture");

        logger.info("Google login success for: " + email);

        Optional<UserEntity> existing= userRepository.findByGoogleId(googleId);

        UserEntity user;
        if (existing.isPresent()) {
            user= existing.get();
            user.setName(name);
            user.setPicture(picture);
            userRepository.save(user);
            logger.info("Returning user: " + email);
        } else {
            user= userRepository.save(new UserEntity(googleId, email, name, picture));
            logger.info("New user registered: " + email);
        }

        String jwt= jwtTokenProvider.generateToken(email, name, user.getId());

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(
            "{\n" +
            "  \"token\": \"" + jwt + "\",\n" +
            "  \"email\": \"" + email + "\",\n" +
            "  \"name\": \"" + name + "\",\n" +
            "  \"message\": \"Login successful! Copy the token and use it in Postman.\"\n" +
            "}"
        );
    }
}