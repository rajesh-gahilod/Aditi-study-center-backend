package co.Example.Study.Center.UserRepo;

import co.Example.Study.Center.Entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepo extends JpaRepository<Admin , Long> {
    Optional<Admin> findByUserAdmin(String userAdmin);

}
