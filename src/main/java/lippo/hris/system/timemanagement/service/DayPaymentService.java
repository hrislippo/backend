package lippo.hris.system.timemanagement.service;

import feign.FeignException;
import lippo.hris.system.feign.ProIntClient;
import lippo.hris.system.recruitment.response.BusinessUnitResp;
import lippo.hris.system.timemanagement.entity.DayPaymentRequest;
import lippo.hris.system.timemanagement.repository.DayPaymentRequestRepository;
import lippo.hris.system.timemanagement.request.TMDPRightsReq;
import lippo.hris.system.timemanagement.response.DayPaymentResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
public class DayPaymentService {

    @Autowired
    ProIntClient proIntClient;

    @Autowired
    DayPaymentRequestRepository dayPaymentRequestRepository;

    public void addDayPayment(TMDPRightsReq tmDPRightsReq, String username){
        tmDPRightsReq.setNikEmpCreate(username);
        proIntClient.addDayPayment(tmDPRightsReq);

        DayPaymentRequest dayPaymentRequest = new DayPaymentRequest();
        dayPaymentRequest.setEmployee(tmDPRightsReq.getNikEmp());
        dayPaymentRequest.setCount(tmDPRightsReq.getDpCount());
        dayPaymentRequest.setDate(tmDPRightsReq.getDpDate());
        dayPaymentRequest.setExpiredDate(tmDPRightsReq.getDpExpiredDate());
        dayPaymentRequest.setDescription(tmDPRightsReq.getDescription());
        dayPaymentRequestRepository.save(dayPaymentRequest);
    }

    public Page<DayPaymentResp> getDayPayment(String empNIK, LocalDate startDate, LocalDate expiryDate, Pageable pageable){
        return dayPaymentRequestRepository.getDayPayment(empNIK, startDate, expiryDate, pageable);
    }

    public DayPaymentRequest getDayPaymentDetail(Long id){
        return dayPaymentRequestRepository.findById(id).get();
    }
}
