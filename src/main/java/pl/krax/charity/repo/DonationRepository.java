package pl.krax.charity.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.krax.charity.entities.Donation;
@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {
    @Query("SELECT sum(d.quantity) FROM Donation d")
    Integer countDonatedBags();
    @Query("SELECT count(d) FROM Donation d")
    Integer countDonations();
}
