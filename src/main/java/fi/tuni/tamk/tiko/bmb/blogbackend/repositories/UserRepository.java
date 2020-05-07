package fi.tuni.tamk.tiko.bmb.blogbackend.repositories;

import fi.tuni.tamk.tiko.bmb.blogbackend.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Allows the fetch of user data from the database.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {}
