package lippo.hris.system.entity;

import lippo.hris.system.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/encrypt-test")
public class EncryptTestController {

    @Autowired
    EncryptTestRepository encryptTestRepository;

    @PostMapping
    public ApiResponse putAmount(@RequestParam(value = "amount") Long amount) {
        EncryptTest encryptTest = new EncryptTest();
        encryptTest.setAmount(amount);
        encryptTestRepository.save(encryptTest);
        return ApiResponse.ok(null, "Put Encrypt Amount Successfully");
    }

    @GetMapping
    public ApiResponse getAmount(@RequestParam(value = "id") Long id) {
        return ApiResponse.ok(encryptTestRepository.findById(id), "Put Encrypt Amount Successfully");
    }
}
