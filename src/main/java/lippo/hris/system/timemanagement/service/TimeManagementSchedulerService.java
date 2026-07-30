package lippo.hris.system.timemanagement.service;

import lippo.hris.system.feign.ProIntClient;
import lippo.hris.system.timemanagement.entity.MobileAttendanceRequest;
import lippo.hris.system.timemanagement.repository.MobileAttendanceRequestRepository;
import lippo.hris.system.timemanagement.request.MOTMAtdTempMbrReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TimeManagementSchedulerService {

    @Autowired
    MobileAttendanceRequestRepository mobileAttendanceRequestRepository;

    @Autowired
    ProIntClient proIntClient;

    public void checkMobileAttendanceMember() {

        List<MobileAttendanceRequest> mobileAttendanceRequests = mobileAttendanceRequestRepository.getMobileAttendanceDay();
        for (MobileAttendanceRequest mobileAttendanceRequest : mobileAttendanceRequests) {
            MOTMAtdTempMbrReq motmAtdTempMbrReq = new MOTMAtdTempMbrReq();
            motmAtdTempMbrReq.setTempCode(mobileAttendanceRequest.getTemplateCode());
            motmAtdTempMbrReq.setNik(mobileAttendanceRequest.getEmployee());
            motmAtdTempMbrReq.setCreatedBy("SYSTEM");
            if(!mobileAttendanceRequest.getExist()){
                proIntClient.addMobileAttendanceTemplateMember(motmAtdTempMbrReq);
                mobileAttendanceRequest.setExist(true);
                mobileAttendanceRequestRepository.save(mobileAttendanceRequest);
            } else{
                proIntClient.deleteMobileAttendanceTemplateMember(motmAtdTempMbrReq);
                mobileAttendanceRequest.setExist(false);
                mobileAttendanceRequestRepository.save(mobileAttendanceRequest);
            }
        }
    }
}
