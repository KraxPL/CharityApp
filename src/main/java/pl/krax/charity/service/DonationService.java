package pl.krax.charity.service;

import pl.krax.charity.entities.Donation;

import java.util.List;
import java.util.Optional;

public interface DonationService {
    void create(Donation donation);
    void update(Donation donation);
    List<Donation> findAll();
    Optional<Donation> findById(Long donationId);
    void delete (Long donationId);
}
