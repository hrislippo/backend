package lippo.hris.system.workflowengine.entity;

import jakarta.persistence.*;
import lippo.hris.system.entity.Auditable;
import lombok.Data;

@Data
@Entity
@Table(name = "WMTemplate", schema = "dbo")
public class WorkflowTemp extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WmTemplateId")
    private Long id;

    @Column(name = "WmTemplateCode")
    private String code;

    @Column(name = "WmTemplateName")
    private String name;

    @Column(name = "WmTemplateActive")
    private Boolean active;

    @Column(name = "WmTemplateFgComp")
    private Boolean flagCompany;

    @Column(name = "WmTemplateFgOrg")
    private Boolean flagOrganization;

    @Column(name = "WmTemplateFgJobLvl")
    private Boolean flagJobLevel;

    @Column(name = "WmTemplateFgJobTtl")
    private Boolean flagJobTitle;

    @Column(name = "WmTemplateFgLoc")
    private Boolean flagLocation;

    @Column(name = "WmTemplateFgPos")
    private Boolean flagPosition;

    @Column(name = "WmTemplateFgEmp")
    private Boolean flagEmployee;
}
