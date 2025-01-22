package azureAuth3.config;
// config paketinin amacı logout işlemini güvenli hale getirmek 


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomLogoutHandler logoutHandler;
// CustomLogoutHandler class'ındaki 
    public SecurityConfig(CustomLogoutHandler logoutHandler) {
        this.logoutHandler = logoutHandler;
    }

    @Bean
//SecurityFilterChain request'ler için oluşturulmuş olan hazır bir interface
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(
                        auth->auth.anyRequest().authenticated()
                )
                .logout(
                        logout -> logout.addLogoutHandler(logoutHandler)
                )
                .oauth2Login(Customizer.withDefaults())
                .build();
    }
}