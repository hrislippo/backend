package lippo.hris.system.authentication.response;

import lombok.Data;

import java.util.List;

@Data
public class MenuResponse {
    private Long id;
    private String name;
    private String path;
    private List<MenuResponse> children;
}
