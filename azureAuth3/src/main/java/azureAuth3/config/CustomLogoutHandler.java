package azureAuth3.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
public class CustomLogoutHandler extends SecurityContextLogoutHandler {


    private final ClientRegistrationRepository clientRegistrationRepository;

    public CustomLogoutHandler(ClientRegistrationRepository clientRegistrationRepository) {
        this.clientRegistrationRepository = clientRegistrationRepository;
    }
    
// html sayfasındaki logout tuşuna basıldığında gerçek anlamda 
// çıkış yapmak için bu fonk. u yazdık
// security class'ındaki logour fonk. unun override'ı
//orada yazdık burada tekrardan yazdık 
    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {
        super.logout(request, response, authentication);

        // logout url' si oluşturmaya çalışıyoruz 
        // önce get logout endpoint
        String logoutendPoint = (String) clientRegistrationRepository
//        		cliient özellikleri için azure-dev'e gidiyor
                .findByRegistrationId("azure-dev")
                .getProviderDetails()
                .getConfigurationMetadata()
//                session'ı bitirmek için endpoint değerini alıp URL'ye katıyor
                .get("end_session_endpoint");


        // logout url' yi oluşturuyorum (yukarıddaki logoutendPoint'i kullanarak)
        String logoutUri = UriComponentsBuilder
                .fromHttpUrl(logoutendPoint)
                .encode()
                .toUriString();

        try {
            response.sendRedirect(logoutUri);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
