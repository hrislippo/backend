package lippo.hris.system.authentication.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lippo.hris.system.authentication.entity.TemplateAuth;
import lippo.hris.system.authentication.entity.TemplateAuthDetail;
import lippo.hris.system.authentication.repository.TemplateAuthDetailRepository;
import lippo.hris.system.authentication.repository.TemplateAuthRepository;
import lippo.hris.system.authentication.request.TempAuthDtRequest;
import lippo.hris.system.authentication.request.TempAuthRequest;
import lippo.hris.system.authentication.response.*;
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
            templateAuthDetail.setCompanyCode(detail.getCompanyCode());
            templateAuthDetail.setCompanyName(detail.getCompanyName());
            templateAuthDetail.setLocationCode(detail.getLocationCode());
            templateAuthDetail.setLocationName(detail.getLocationName());
            templateAuthDetail.setPositionCode(detail.getPositionCode());
            templateAuthDetail.setPositionName(detail.getPositionName());
            templateAuthDetail.setJobTitleCode(detail.getJobTitleCode());
            templateAuthDetail.setJobTitleName(detail.getJobTitleName());
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
            detailRequest.setCompanyCode(detail.getCompanyCode());
            detailRequest.setCompanyName(detail.getCompanyName());
            detailRequest.setLocationCode(detail.getLocationCode());
            detailRequest.setLocationName(detail.getLocationName());
            detailRequest.setPositionCode(detail.getPositionCode());
            detailRequest.setPositionName(detail.getPositionName());
            detailRequest.setJobTitleCode(detail.getJobTitleCode());
            detailRequest.setJobTitleName(detail.getJobTitleName());
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
            templateAuthDetail.setCompanyCode(detail.getCompanyCode());
            templateAuthDetail.setCompanyName(detail.getCompanyName());
            templateAuthDetail.setLocationCode(detail.getLocationCode());
            templateAuthDetail.setLocationName(detail.getLocationName());
            templateAuthDetail.setPositionCode(detail.getPositionCode());
            templateAuthDetail.setPositionName(detail.getPositionName());
            templateAuthDetail.setJobTitleCode(detail.getJobTitleCode());
            templateAuthDetail.setJobTitleName(detail.getJobTitleName());
            templateAuthDetailRepository.save(templateAuthDetail);
        }

        for(TempAuthDtRequest detail : removeDetail){
            TemplateAuthDetail templateAuthDetail = templateAuthDetailRepository.findByTemplateAuthAndCriteria(templateAuth.getId(),
                    detail.getOrganizationCode(), detail.getJobLevelCode(), detail.getCompanyCode(), detail.getLocationCode(),
                    detail.getPositionCode(), detail.getJobTitleCode());
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
            templateAuthDtResponse.setCompCode(templateAuthDetail.getCompanyCode());
            templateAuthDtResponse.setCompName(templateAuthDetail.getCompanyName());
            templateAuthDtResponse.setLocCode(templateAuthDetail.getLocationCode());
            templateAuthDtResponse.setLocName(templateAuthDetail.getLocationName());
            templateAuthDtResponse.setPosCode(templateAuthDetail.getPositionCode());
            templateAuthDtResponse.setPosName(templateAuthDetail.getPositionName());
            templateAuthDtResponse.setJobTtlCode(templateAuthDetail.getJobTitleCode());
            templateAuthDtResponse.setJobTtlName(templateAuthDetail.getJobTitleName());
            templateAuthDtResponseList.add(templateAuthDtResponse);
        }
        templateAuthResponse.setDetail(templateAuthDtResponseList);
        return templateAuthResponse;
    }

    public List<String> getAuthEmployees(List<String> tempAuthList){
        List<String> compCodes = templateAuthRepository.findCompCodeByTempName(tempAuthList);
        List<String> orgCodes = templateAuthRepository.findOrgCodeByTempName(tempAuthList);
        List<String> jobLevelCodes = templateAuthRepository.findJobLevelByTempName(tempAuthList);
        List<String> jobTitleCodes = templateAuthRepository.findJobTitleByTempName(tempAuthList);
        List<String> positionCodes = templateAuthRepository.findPosCodeByTempName(tempAuthList);
        List<String> locationCodes = templateAuthRepository.findLocCodeByTempName(tempAuthList);
        Object employeeNIKData = proIntClient.getEmployeeAuth(compCodes, orgCodes, jobLevelCodes, jobTitleCodes, positionCodes, locationCodes).getData();
        if(employeeNIKData == null){
            return null;
        }
        List<String> employeeNIKList = objectMapper.convertValue(employeeNIKData, new TypeReference<>(){});
        return employeeNIKList;
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

    public List<CompanyResponse> getActiveCompany() {
        Object companyData = proIntClient.getActiveCompany().getData();
        List<CompanyResponse> companies = objectMapper.convertValue(companyData, new TypeReference<>(){});
        return companies;
    }

    public List<JobTitleResponse> getActiveJobTitle() {
        Object jobTitleData = proIntClient.getActiveJobTitle().getData();
        List<JobTitleResponse> jobTitles = objectMapper.convertValue(jobTitleData, new TypeReference<>(){});
        return jobTitles;
    }

    public List<LocationResponse> getActiveLocation() {
        Object locationData = proIntClient.getActiveLocation().getData();
        List<LocationResponse> locations = objectMapper.convertValue(locationData, new TypeReference<>(){});
        return locations;
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
