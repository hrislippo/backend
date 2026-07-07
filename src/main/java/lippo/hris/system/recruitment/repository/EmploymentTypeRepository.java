package lippo.hris.system.recruitment.repository;

import lippo.hris.system.recruitment.entity.EmploymentType;
import lippo.hris.system.recruitment.entity.Venue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmploymentTypeRepository extends JpaRepository<EmploymentType, Long> {

    @Query(nativeQuery = true,
            value = "SELECT e.* " +
                    "FROM RCMEmployType e " +
                    "WHERE (:code IS NULL OR e.RcmEmployTypeCode LIKE '%'+:code+'%') " +
                    "AND (:name IS NULL OR e.RcmEmployTypeName LIKE '%'+:name+'%')",
            countQuery = "SELECT COUNT(1) " +
                    "FROM RCMEmployType e " +
                    "WHERE (:code IS NULL OR e.RcmEmployTypeCode LIKE '%'+:code+'%') " +
                    "AND (:name IS NULL OR e.RcmEmployTypeName LIKE '%'+:name+'%')")
    Page<EmploymentType> getEmploymentType(@Param("code") String code,
                         @Param("name") String name,
                         Pageable pageable);
}
