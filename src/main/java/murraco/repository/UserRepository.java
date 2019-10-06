package murraco.repository;

import javax.transaction.Transactional;

import murraco.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<UserModel, Integer> {

  boolean existsByUsername(String username);

  UserModel findByUsername(String username);

  @Transactional
  void deleteByUsername(String username);

}
