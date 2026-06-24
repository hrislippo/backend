package lippo.hris.system.recruitment.scheduler;

import lippo.hris.system.recruitment.service.RecruitmentSchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RecruitmentScheduler {

    @Autowired
    RecruitmentSchedulerService recruitmentSchedulerService;

    @Scheduled(cron = "0 0 23 * * *", zone = "Asia/Jakarta")
    public void sendAddCandidateReminder() {
        recruitmentSchedulerService.sendAddCandidateReminder();
    }
}
