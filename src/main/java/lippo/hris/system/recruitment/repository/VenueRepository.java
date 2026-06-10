package lippo.hris.system.recruitment.repository;

import lippo.hris.system.recruitment.entity.Venue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VenueRepository extends JpaRepository<Venue, Long> {

    @Query(nativeQuery = true,
            value = "SELECT v.* " +
                    "FROM RCMVenue v " +
                    "WHERE (:code IS NULL OR v.RcmVenueCode LIKE '%'+:code+'%') " +
                    "AND (:name IS NULL OR v.RcmVenueName LIKE '%'+:name+'%')",
            countQuery = "SELECT COUNT(1) " +
                    "FROM RCMVenue v " +
                    "WHERE (:code IS NULL OR v.RcmVenueCode LIKE '%'+:code+'%') " +
                    "AND (:name IS NULL OR v.RcmVenueName LIKE '%'+:name+'%')")
    Page<Venue> getVenue(@Param("code") String code,
                         @Param("name") String name,
                         Pageable pageable);
}
