package azur.azurAuth2.config;
import org.springframework.context.annotation.Bean;
////
////import org.springframework.context.annotation.Bean;
////import org.springframework.context.annotation.Configuration;
////import org.springframework.security.authentication.AuthenticationProvider;
////import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
////import org.springframework.security.config.annotation.web.builders.HttpSecurity;
////import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
////import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
////import org.springframework.security.core.userdetails.UserDetailsService;
////import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
////import org.springframework.security.crypto.password.PasswordEncoder;
////import org.springframework.security.web.SecurityFilterChain;
////
////@Configuration
////@EnableWebSecurity
////public class SecurityConfig {
////
////    private final UserDetailsService userDetailsService;
////
////    public SecurityConfig(UserDetailsService userDetailsService) {
////        this.userDetailsService = userDetailsService;
////    }
////
////    @Bean
////    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////        http
////            .authorizeHttpRequests(authorizeRequests ->
////                authorizeRequests
////                    .requestMatchers("/api/login").permitAll() // antMatchers yerine requestMatchers
////                    .anyRequest().authenticated()
////            )
////            .formLogin(formLogin -> formLogin
////                .loginPage("/login") // Özel bir login sayfası belirtirseniz burayı kullanın
////            )
////            .oauth2Login(); // Azure AD OAuth2 kimlik doğrulaması
////
////        return http.build();
////    }
////
////    @Bean
////    public AuthenticationProvider authenticationProvider() {
////        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
////        provider.setUserDetailsService(userDetailsService);
////        provider.setPasswordEncoder(passwordEncoder());
////        return provider;
////    }
////
////    @Bean
////    public PasswordEncoder passwordEncoder() {
////        return new BCryptPasswordEncoder();
////    }
////
////    @Bean
////    public WebSecurityCustomizer webSecurityCustomizer() {
////        return (web) -> web.ignoring().requestMatchers("/resources/**", "/static/**"); // antMatchers yerine requestMatchers
////    }
////}

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                    .anyRequest().authenticated()
            )
            .oauth2Login(oauth2Login ->
                oauth2Login
                    .defaultSuccessUrl("/demo", true)
                    .clientRegistrationRepository(clientRegistrationRepository())
            )
            .sessionManagement(sessionManagement ->
                sessionManagement
                    .sessionFixation().none()
            );

        return http.build();
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(azureClientRegistration());
    }

    private ClientRegistration azureClientRegistration() {
        return ClientRegistration.withRegistrationId("azure")
            .clientId("*")
            .clientSecret("*")
            .scope("openid", "profile", "email", "User.Read")
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .redirectUri("http://localhost:8080/login/oauth2/code/azure")
            .authorizationUri("https://login.microsoftonline.com/*/oauth2/v2.0/authorize")
            .tokenUri("https://login.microsoftonline.com/*/oauth2/v2.0/token")
            .userInfoUri("https://graph.microsoft.com/v1.0/me")
            .jwkSetUri("https://login.microsoftonline.com/*/v2.0/keys")
//            .userNameAttributeName("sub") // "sub" genellikle ID token'daki ana kullanıcı adıdır
            .build();
    }
}
