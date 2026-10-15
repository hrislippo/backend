package lippo.hris.system.workflowengine.validation;

import lippo.hris.system.exception.BadRequestException;
import lippo.hris.system.workflowengine.request.TempApproveeRequest;
import lippo.hris.system.workflowengine.request.TempApproveeRequestDt;
import org.springframework.stereotype.Component;

@Component
public class WorkflowTempApproveeValidation {

    public void templateRequired(TempApproveeRequest tempApproveeRequest) {
        if(tempApproveeRequest.getTempId() == null){
            throw new BadRequestException("Template is required");
        }
    }

    public void detailRequired(TempApproveeRequest tempApproveeRequest) {
        if(tempApproveeRequest.getDetailList() != null){
            for(TempApproveeRequestDt tempApproveeRequestDt : tempApproveeRequest.getDetailList()){
                if(tempApproveeRequestDt.getCompanyCode() == null &&
                        tempApproveeRequestDt.getOrganizationCode() == null &&
                        tempApproveeRequestDt.getJobLevelCode() == null &&
                        tempApproveeRequestDt.getJobTitleCode() == null &&
                        tempApproveeRequestDt.getPositionCode() == null &&
                        tempApproveeRequestDt.getLocationCode() == null &&
                        tempApproveeRequestDt.getEmployeeNik() == null){
                    throw new BadRequestException("Details are required");
                }
            }
        }
    }
}
