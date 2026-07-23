package lippo.hris.system.service;

import lippo.hris.system.entity.AuditLog;
import lippo.hris.system.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuditLogService {

    @Autowired
    AuditLogRepository auditLogRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void log(String action){
        AuditLog auditLog = new AuditLog();
        auditLog.setAction(action);
        auditLogRepository.save(auditLog);
    }
}
