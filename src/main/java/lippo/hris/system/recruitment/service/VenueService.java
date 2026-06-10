package lippo.hris.system.recruitment.service;

import lippo.hris.system.recruitment.entity.Venue;
import lippo.hris.system.recruitment.repository.VenueRepository;
import lippo.hris.system.recruitment.request.VenueReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class VenueService {

    @Autowired
    VenueRepository venueRepository;

    public void addVenue(VenueReq venueReq) {
        Venue venue = new Venue();
        venue.setCode(venueReq.getCode());
        venue.setName(venueReq.getName());
        venueRepository.save(venue);
    }

    public void modifyVenue(VenueReq venueReq) {
        Venue venue = venueRepository.findById(venueReq.getId()).orElse(new Venue());
        venue.setCode(venueReq.getCode());
        venue.setName(venueReq.getName());
        venueRepository.save(venue);
    }

    public Page<Venue> getVenue(String code, String name, Pageable pageable) {
        return venueRepository.getVenue(code, name, pageable);
    }

    public List<Venue> getVenue() {
        return venueRepository.findAll();
    }

    public Venue getVenue(Long id) {
        return venueRepository.findById(id).orElse(null);
    }

    public void deleteVenue(Long id) {
        venueRepository.deleteById(id);
    }
}
