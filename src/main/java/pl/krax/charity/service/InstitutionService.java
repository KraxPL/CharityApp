package pl.krax.charity.service;

import pl.krax.charity.entities.Institution;

import java.util.List;
import java.util.Optional;

public interface InstitutionService {
    void create(Institution institution);
    void update(Institution institution);
    List<Institution> findAll();
    Optional<Institution> findById(Long institutionId);
    void delete (Long institutionId);
}
