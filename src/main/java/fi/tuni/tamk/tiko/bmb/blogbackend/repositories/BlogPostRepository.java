package fi.tuni.tamk.tiko.bmb.blogbackend.repositories;

import fi.tuni.tamk.tiko.bmb.blogbackend.model.BlogPost;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogPostRepository extends CrudRepository<BlogPost, Long> {
    List<BlogPost> findByAuthorId(long id);
}
