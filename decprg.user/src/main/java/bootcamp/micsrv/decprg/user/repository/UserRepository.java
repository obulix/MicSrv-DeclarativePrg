package bootcamp.micsrv.decprg.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import bootcamp.micsrv.decprg.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
