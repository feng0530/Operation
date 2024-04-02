package tw.idv.frank.users.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.idv.frank.users.model.entity.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {

    Users findByEmail(String email);
}
