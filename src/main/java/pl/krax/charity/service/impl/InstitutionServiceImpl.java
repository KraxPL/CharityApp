package pl.krax.charity.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.krax.charity.entities.Institution;
import pl.krax.charity.repo.InstitutionRepo;
import pl.krax.charity.service.InstitutionService;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

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

    @Override
    @Transactional
    public List<Institution> evenList() {
        return makeListEven(findAll());
    }

    private List<Institution> makeListEven(List<Institution> institutions) {
        if (institutions.size() % 2 != 0) {
            int randomIndex = (int) (Math.random() * institutions.size());
            institutions.remove(randomIndex);
        }
        return institutions;
    }
}
