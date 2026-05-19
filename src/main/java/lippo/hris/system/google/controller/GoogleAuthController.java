package lippo.hris.system.google.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/googleauth")
public class GoogleAuthController {

    @Autowired
    OAuth2AuthorizedClientService clientService;

    @GetMapping("/token")
    public String token(
            OAuth2AuthenticationToken authentication
    ) {

        OAuth2AuthorizedClient client =
                clientService.loadAuthorizedClient(
                        authentication
                                .getAuthorizedClientRegistrationId(),
                        authentication.getName()
                );

        return client.getRefreshToken().getTokenValue();
    }
}
