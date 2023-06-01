package pl.krax.charity.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.krax.charity.dto.DonationDto;
import pl.krax.charity.entities.Donation;
import pl.krax.charity.mapper.DonationMapper;
import pl.krax.charity.repo.DonationRepository;
import pl.krax.charity.service.CategoryService;
import pl.krax.charity.service.DonationService;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class DonationServiceImpl implements DonationService {
    private final DonationRepository donationRepo;
    private final CategoryService categoryService;
    @Override
    @Transactional
    public void create(DonationDto donationDto) {
        Donation donation = DonationMapper.INSTANCE.toEntity(donationDto, categoryService);
        donationRepo.save(donation);
    }

    @Override
    @Transactional
    public void update(Donation donation) {
        donationRepo.save(donation);
    }

    @Override
    @Transactional
    public List<Donation> findAll() {
        return donationRepo.findAll();
    }

    @Override
    @Transactional
    public Optional<Donation> findById(Long donationId) {
        return donationRepo.findById(donationId);
    }

    @Override
    @Transactional
    public void delete(Long donationId) {
        findById(donationId).ifPresent(donationRepo::delete);
    }

    @Override
    public Integer donatedBagsSoFar() {
        return donationRepo.countDonatedBags();
    }

    @Override
    public Integer donationsSoFar() {
        return donationRepo.countDonations();
    }
}
