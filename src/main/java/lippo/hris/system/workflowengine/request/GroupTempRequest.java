package lippo.hris.system.workflowengine.request;

import lombok.Data;

import java.util.List;

@Data
public class GroupTempRequest {
    private Long id;
    private String groupTempCode;
    private String groupTempName;
    private String docTypeCode;
    private Boolean active;
    private Integer sequence;
    private Boolean flagCompany;
    private Boolean flagOrganization;
    private Boolean flagJobLevel;
    private Boolean flagJobTitle;
    private Boolean flagLocation;
    private Boolean flagPosition;
    private Boolean flagEmployee;
    private List<Long> templateList;
}
