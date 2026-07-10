package lippo.hris.system.timemanagement.controller;

import lippo.hris.system.response.ApiResponse;
import lippo.hris.system.timemanagement.request.TMDPRightsReq;
import lippo.hris.system.timemanagement.service.DayPaymentService;
import lippo.hris.system.timemanagement.validation.DayPaymentValidation;
import lippo.hris.system.user.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/timemanagement")
public class DayPaymentController {

    @Autowired
    DayPaymentValidation dayPaymentValidation;

    @Autowired
    DayPaymentService dayPaymentService;

    @PostMapping("/day-payment")
    public ApiResponse addDayPayment(@RequestBody TMDPRightsReq tmDPRightsReq,
                                     Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        dayPaymentValidation.checkDayPaymentValue(tmDPRightsReq);
        dayPaymentValidation.inputDayPaymentRequired(tmDPRightsReq);
        dayPaymentService.addDayPayment(tmDPRightsReq, customUserDetails.getUsername());
        return ApiResponse.ok(null, "Day Payment Added");
    }

    @GetMapping("/day-payment")
    public ApiResponse getDayPayment(@RequestParam(value = "empNIK", required = false) String empNIK,
                                     @RequestParam(value = "startDate", required = false) LocalDate startDate,
                                     @RequestParam(value = "expiryDate", required = false) LocalDate expiryDate,
                                     Pageable pageable) {
        return ApiResponse.ok(dayPaymentService.getDayPayment(empNIK, startDate, expiryDate, pageable), "Get Day Payment Successfully");
    }

    @GetMapping("/day-payment-detail")
    public ApiResponse getDayPaymentDetail(@RequestParam(value = "id") Long id) {
        return ApiResponse.ok(dayPaymentService.getDayPaymentDetail(id), "Get Day Payment Detail Successfully");
    }
}
