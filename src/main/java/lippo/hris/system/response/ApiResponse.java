package lippo.hris.system.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ApiResponse {

    private Integer code;
    private String status;
    private Object data;
    private String message;

    // 200 - OK
    public static ApiResponse ok(Object data, String message){
        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(),
                data, message);
    }

    // 401 - UNAUTHORIZED
    public static ApiResponse unauthorized(String message){
        return new ApiResponse(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                null, message);
    }
}
