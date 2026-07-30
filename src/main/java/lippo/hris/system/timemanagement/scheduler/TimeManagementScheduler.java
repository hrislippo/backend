package lippo.hris.system.timemanagement.scheduler;

import lippo.hris.system.timemanagement.service.TimeManagementSchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TimeManagementScheduler {

    @Autowired
    TimeManagementSchedulerService timeManagementSchedulerService;

    @Scheduled(cron = "0 0 0 * * ?", zone = "Asia/Jakarta")
    public void checkMobileAttendanceMember() {
        timeManagementSchedulerService.checkMobileAttendanceMember();
    }
}
