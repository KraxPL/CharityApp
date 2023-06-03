package pl.krax.charity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.krax.charity.entities.Institution;

@Repository
public interface InstitutionRepository extends JpaRepository<Institution, Long> {
}
