package pl.krax.charity.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.krax.charity.entities.Donation;

import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {
    @Query("SELECT sum(d.quantity) FROM Donation d")
    Integer countDonatedBags();
    @Query("SELECT count(d) FROM Donation d")
    Integer countDonations();
    @EntityGraph(attributePaths = {"institution"})
    List<Donation> findAllDonationsByUserId(Long userId, Sort sort);
}
