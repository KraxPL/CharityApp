package pl.krax.charity.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.krax.charity.entities.Institution;
import pl.krax.charity.repo.InstitutionRepo;
import pl.krax.charity.service.InstitutionService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class InstitutionServiceImpl implements InstitutionService {
    private final InstitutionRepo institutionRepo;
    @Override
    @Transactional
    public void create(Institution institution) {
        institutionRepo.save(institution);
    }

    @Override
    @Transactional
    public void update(Institution institution) {
        institutionRepo.save(institution);
    }

    @Override
    @Transactional
    public List<Institution> findAll() {
        return institutionRepo.findAll();
    }

    @Override
    @Transactional
    public Optional<Institution> findById(Long institutionId) {
        return institutionRepo.findById(institutionId);
    }

    @Override
    @Transactional
    public void delete(Long institutionId) {
        findById(institutionId).ifPresent(institutionRepo::delete);
    }
}
