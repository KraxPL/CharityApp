package pl.krax.charity.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.krax.charity.entities.Institution;

@Repository
public interface InstitutionRepo extends JpaRepository<Institution, Long> {
}
