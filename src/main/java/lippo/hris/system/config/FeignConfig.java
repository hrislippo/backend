package lippo.hris.system.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import feign.Util;
import feign.codec.ErrorDecoder;
import lippo.hris.system.exception.NotFoundException;
import lippo.hris.system.response.ApiResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;

@Configuration
public class FeignConfig {

    @Bean
    public ErrorDecoder errorDecoder(ObjectMapper objectMapper) {
        return (methodKey, response) -> {
            try {
                if (response.body() != null) {
                    String body = Util.toString(response.body().asReader(StandardCharsets.UTF_8));

                    ApiResponse apiResponse =
                            objectMapper.readValue(body, ApiResponse.class);

                    if (response.status() == 404) {
                        return new NotFoundException(apiResponse.getMessage());
                    }
                }
            } catch (Exception e) {
                // Ignore parsing errors and fall through
            }

            return FeignException.errorStatus(methodKey, response);
        };
    }
}
