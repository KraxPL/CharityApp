package pl.krax.charity.service;

import pl.krax.charity.dto.DonationDto;
import pl.krax.charity.entities.Donation;

import java.util.List;
import java.util.Optional;

public interface DonationService {
    void create(DonationDto donationDto);
    void update(Donation donation);
    List<Donation> findAll();
    Optional<Donation> findById(Long donationId);
    void delete (Long donationId);
    Integer donatedBagsSoFar();
    Integer donationsSoFar();
    void itemsCollected(Long donationId);
    List<Donation> donationsByUserId(Long userId);
    boolean checkDonationIdAndUserIdMatch(Long donationId, Long userId);
}
