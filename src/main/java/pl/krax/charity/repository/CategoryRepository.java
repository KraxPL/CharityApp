package pl.krax.charity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.krax.charity.entities.Category;
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
