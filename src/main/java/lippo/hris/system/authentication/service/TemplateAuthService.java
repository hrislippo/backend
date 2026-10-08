package lippo.hris.system.authentication.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lippo.hris.system.authentication.entity.TemplateAuth;
import lippo.hris.system.authentication.entity.TemplateAuthDetail;
import lippo.hris.system.authentication.repository.TemplateAuthDetailRepository;
import lippo.hris.system.authentication.repository.TemplateAuthRepository;
import lippo.hris.system.authentication.request.TempAuthDtRequest;
import lippo.hris.system.authentication.request.TempAuthRequest;
import lippo.hris.system.authentication.response.JobLevelResponse;
import lippo.hris.system.authentication.response.OrgResponse;
import lippo.hris.system.authentication.response.TemplateAuthDtResponse;
import lippo.hris.system.authentication.response.TemplateAuthResponse;
import lippo.hris.system.feign.ProIntClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TemplateAuthService {

    @Autowired
    ProIntClient proIntClient;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    TemplateAuthRepository templateAuthRepository;

    @Autowired
    TemplateAuthDetailRepository templateAuthDetailRepository;

    public void addTemplateAuth(TempAuthRequest tempAuthRequest) {
        TemplateAuth templateAuth = new TemplateAuth();
        templateAuth.setCode(tempAuthRequest.getTempAuthCode());
        templateAuth.setName(tempAuthRequest.getTempAuthName());
        templateAuth = templateAuthRepository.save(templateAuth);

        for(TempAuthDtRequest detail : tempAuthRequest.getDetailList()){
            TemplateAuthDetail templateAuthDetail = new TemplateAuthDetail();
            templateAuthDetail.setTemplateAuth(templateAuth);
            templateAuthDetail.setOrganizationCode(detail.getOrganizationCode());
            templateAuthDetail.setOrganizationName(detail.getOrganizationName());
            templateAuthDetail.setJobLevelCode(detail.getJobLevelCode());
            templateAuthDetail.setJobLevelName(detail.getJobLevelName());
            templateAuthDetailRepository.save(templateAuthDetail);
        }
    }

    public void modifyTemplateAuth(TempAuthRequest tempAuthRequest) {
        TemplateAuth templateAuth = templateAuthRepository.findById(tempAuthRequest.getId()).get();
        List<TemplateAuthDetail> templateAuthDetailList = templateAuthDetailRepository.findByTemplateAuth(templateAuth);
        templateAuth.setCode(tempAuthRequest.getTempAuthCode());
        templateAuth.setName(tempAuthRequest.getTempAuthName());
        templateAuth = templateAuthRepository.save(templateAuth);

        List<TempAuthDtRequest> newDetail = tempAuthRequest.getDetailList();
        List<TempAuthDtRequest> existingDetail = new ArrayList<>();
        for(TemplateAuthDetail detail : templateAuthDetailList){
            TempAuthDtRequest detailRequest = new TempAuthDtRequest();
            detailRequest.setOrganizationCode(detail.getOrganizationCode());
            detailRequest.setOrganizationName(detail.getOrganizationName());
            detailRequest.setJobLevelCode(detail.getJobLevelCode());
            detailRequest.setJobLevelName(detail.getJobLevelName());
            existingDetail.add(detailRequest);
        }

        List<TempAuthDtRequest> addDetail = new ArrayList<>(newDetail);
        addDetail.removeAll(existingDetail);
        List<TempAuthDtRequest> removeDetail = new ArrayList<>(existingDetail);
        removeDetail.removeAll(newDetail);

        for(TempAuthDtRequest detail : addDetail){
            TemplateAuthDetail templateAuthDetail = new TemplateAuthDetail();
            templateAuthDetail.setTemplateAuth(templateAuth);
            templateAuthDetail.setOrganizationCode(detail.getOrganizationCode());
            templateAuthDetail.setOrganizationName(detail.getOrganizationName());
            templateAuthDetail.setJobLevelCode(detail.getJobLevelCode());
            templateAuthDetail.setJobLevelName(detail.getJobLevelName());
            templateAuthDetailRepository.save(templateAuthDetail);
        }

        for(TempAuthDtRequest detail : removeDetail){
            TemplateAuthDetail templateAuthDetail = templateAuthDetailRepository.findByTemplateAuthAndOrganizationCodeAndJobLevelCode(templateAuth, detail.getOrganizationCode(), detail.getJobLevelCode());
            templateAuthDetailRepository.delete(templateAuthDetail);
        }
    }

    public Page<TemplateAuth> getTemplateAuth(String templateCode, String templateName, Pageable pageable) {
        return templateAuthRepository.findAllByCodeAndName(templateCode, templateName, pageable);
    }

    public List<TemplateAuth> getTemplateAuthList() {
        return templateAuthRepository.findAll();
    }

    public TemplateAuthResponse getTemplateAuthDetail(Long id){
        TemplateAuth templateAuth = templateAuthRepository.findById(id).get();
        List<TemplateAuthDetail> templateAuthDetailList = templateAuthDetailRepository.findByTemplateAuth(templateAuth);

        TemplateAuthResponse templateAuthResponse = new TemplateAuthResponse();
        List<TemplateAuthDtResponse> templateAuthDtResponseList = new ArrayList<>();
        templateAuthResponse.setTemplateName(templateAuth.getName());
        templateAuthResponse.setTemplateCode(templateAuth.getCode());
        for (TemplateAuthDetail templateAuthDetail : templateAuthDetailList) {
            TemplateAuthDtResponse templateAuthDtResponse = new TemplateAuthDtResponse();
            templateAuthDtResponse.setOrgCode(templateAuthDetail.getOrganizationCode());
            templateAuthDtResponse.setOrgName(templateAuthDetail.getOrganizationName());
            templateAuthDtResponse.setJobLvlCode(templateAuthDetail.getJobLevelCode());
            templateAuthDtResponse.setJobLvlName(templateAuthDetail.getJobLevelName());
            templateAuthDtResponseList.add(templateAuthDtResponse);
        }
        templateAuthResponse.setDetail(templateAuthDtResponseList);
        return templateAuthResponse;
    }

    public List<OrgResponse> getActiveOrganization() {
        Object orgData = proIntClient.getActiveOrganization().getData();
        List<OrgResponse> organizations = objectMapper.convertValue(orgData, new TypeReference<>(){});
        return organizations;
    }

    public List<JobLevelResponse> getActiveJobLevel() {
        Object jobLevelData = proIntClient.getActiveJobLevel().getData();
        List<JobLevelResponse> jobLevels = objectMapper.convertValue(jobLevelData, new TypeReference<>(){});
        return jobLevels;
    }

    public void deleteTemplateAuth(Long id){
        TemplateAuth templateAuth = templateAuthRepository.findById(id).get();
        List<TemplateAuthDetail> templateAuthDetailList = templateAuthDetailRepository.findByTemplateAuth(templateAuth);

        for(TemplateAuthDetail templateAuthDetail : templateAuthDetailList){
            templateAuthDetailRepository.delete(templateAuthDetail);
        }
        templateAuthRepository.delete(templateAuth);
    }
}
