import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OidcDetailsRepository extends JpaRepository<OidcDetails, String> {
    // Custom query methods if needed
}
