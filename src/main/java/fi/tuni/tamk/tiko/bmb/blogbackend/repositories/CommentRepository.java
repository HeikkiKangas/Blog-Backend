package fi.tuni.tamk.tiko.bmb.blogbackend.repositories;

import fi.tuni.tamk.tiko.bmb.blogbackend.model.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Allows the fetch of comment data from the database.
 */
@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {}
