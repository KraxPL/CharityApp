package pl.krax.charity.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.krax.charity.entities.Donation;
@Repository
public interface DonationRepo extends JpaRepository<Donation, Long> {
}
