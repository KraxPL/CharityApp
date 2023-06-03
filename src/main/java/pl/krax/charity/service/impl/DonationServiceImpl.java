package pl.krax.charity.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.krax.charity.dto.DonationDto;
import pl.krax.charity.entities.Donation;
import pl.krax.charity.entities.User;
import pl.krax.charity.enums.Status;
import pl.krax.charity.mapper.DonationMapper;
import pl.krax.charity.repository.DonationRepository;
import pl.krax.charity.service.CategoryService;
import pl.krax.charity.service.DonationService;

import jakarta.transaction.Transactional;

import java.time.LocalDate;
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
        donation.setStatus(Status.PENDING);
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

    @Override
    public void itemsCollected(Long donationId) {
        findById(donationId).ifPresent(donation -> {
            donation.setStatus(Status.COLLECTED);
            donation.setStatusChangeDate(LocalDate.now());
            update(donation);
        });
    }

    @Override
    public List<Donation> donationsByUserId(Long userId) {
        return donationRepo.findAllDonationsByUserId(userId);
    }

    @Override
    public boolean checkDonationIdAndUserIdMatch(Long donationId, Long userId) {
        return findById(donationId)
                .map(Donation::getUser)
                .map(User::getId)
                .filter(id -> id.equals(userId))
                .isPresent();
    }
}
