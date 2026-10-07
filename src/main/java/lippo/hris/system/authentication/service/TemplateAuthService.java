package lippo.hris.system.authentication.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lippo.hris.system.authentication.entity.TemplateAuth;
import lippo.hris.system.authentication.entity.TemplateAuthDetail;
import lippo.hris.system.authentication.repository.TemplateAuthDetailRepository;
import lippo.hris.system.authentication.repository.TemplateAuthRepository;
import lippo.hris.system.authentication.request.TempAuthOrgRequest;
import lippo.hris.system.authentication.request.TempAuthRequest;
import lippo.hris.system.authentication.response.OrgResponse;
import lippo.hris.system.authentication.response.TemplateAuthOrgResponse;
import lippo.hris.system.authentication.response.TemplateAuthResponse;
import lippo.hris.system.feign.ProIntClient;
import lippo.hris.system.talentmanagement.entity.TalentPool;
import lippo.hris.system.talentmanagement.entity.TalentPoolRequestDetail;
import lippo.hris.system.talentmanagement.request.TalentPoolRequestDetailReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

        for(TempAuthOrgRequest org : tempAuthRequest.getOrganizationList()){
            TemplateAuthDetail templateAuthDetail = new TemplateAuthDetail();
            templateAuthDetail.setTemplateAuth(templateAuth);
            templateAuthDetail.setOrganizationCode(org.getOrganizationCode());
            templateAuthDetail.setOrganizationName(org.getOrganizationName());
            templateAuthDetailRepository.save(templateAuthDetail);
        }
    }

    public void modifyTemplateAuth(TempAuthRequest tempAuthRequest) {
        TemplateAuth templateAuth = templateAuthRepository.findById(tempAuthRequest.getId()).get();
        List<TemplateAuthDetail> templateAuthDetailList = templateAuthDetailRepository.findByTemplateAuth(templateAuth);
        templateAuth.setCode(tempAuthRequest.getTempAuthCode());
        templateAuth.setName(tempAuthRequest.getTempAuthName());
        templateAuth = templateAuthRepository.save(templateAuth);

        List<String> added = new ArrayList<>(tempAuthRequest.getOrganizationList().stream().map(TempAuthOrgRequest::getOrganizationCode).toList());
        added.removeAll(templateAuthDetailList.stream().map(TemplateAuthDetail::getOrganizationCode).toList());
        List<String> removed = new ArrayList<>(templateAuthDetailList.stream().map(TemplateAuthDetail::getOrganizationCode).toList());
        removed.removeAll(tempAuthRequest.getOrganizationList().stream().map(TempAuthOrgRequest::getOrganizationCode).toList());

        for(String addOrgCode : added){
            String addOrgName = tempAuthRequest.getOrganizationList().stream().filter(e -> e.getOrganizationCode().equals(addOrgCode)).findFirst().get().getOrganizationName();
            TemplateAuthDetail templateAuthDetail = new TemplateAuthDetail();
            templateAuthDetail.setTemplateAuth(templateAuth);
            templateAuthDetail.setOrganizationCode(addOrgCode);
            templateAuthDetail.setOrganizationName(addOrgName);
            templateAuthDetailRepository.save(templateAuthDetail);
        }

        for(String removeOrgCode : removed){
            TemplateAuthDetail templateAuthDetail = templateAuthDetailRepository.findByTemplateAuthAndOrganizationCode(templateAuth, removeOrgCode);
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
        List<TemplateAuthOrgResponse> templateAuthOrgResponseList = new ArrayList<>();
        templateAuthResponse.setTemplateName(templateAuth.getName());
        templateAuthResponse.setTemplateCode(templateAuth.getCode());
        for (TemplateAuthDetail templateAuthDetail : templateAuthDetailList) {
            TemplateAuthOrgResponse templateAuthOrgResponse = new TemplateAuthOrgResponse();
            templateAuthOrgResponse.setOrgCode(templateAuthDetail.getOrganizationCode());
            templateAuthOrgResponse.setOrgName(templateAuthDetail.getOrganizationName());
            templateAuthOrgResponseList.add(templateAuthOrgResponse);
        }
        templateAuthResponse.setOrganization(templateAuthOrgResponseList);
        return templateAuthResponse;
    }

    public List<OrgResponse> getActiveOrganization() {
        Object orgData = proIntClient.getActiveOrganization().getData();
        List<OrgResponse> organizations = objectMapper.convertValue(orgData, new TypeReference<>(){});
        return organizations;
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
