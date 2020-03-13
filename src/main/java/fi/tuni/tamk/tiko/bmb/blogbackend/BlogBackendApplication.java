package fi.tuni.tamk.tiko.bmb.blogbackend;

import fi.tuni.tamk.tiko.bmb.blogbackend.model.BlogPost;
import fi.tuni.tamk.tiko.bmb.blogbackend.model.LoremIpsum;
import fi.tuni.tamk.tiko.bmb.blogbackend.model.User;
import fi.tuni.tamk.tiko.bmb.blogbackend.repositories.BlogPostRepository;
import fi.tuni.tamk.tiko.bmb.blogbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;

@SpringBootApplication
public class BlogBackendApplication implements CommandLineRunner {
	@Autowired
	BlogPostRepository blogPostDB;

	@Autowired
	UserRepository userDB;

	public static void main(String[] args) {
		SpringApplication.run(BlogBackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User u = new User();
		u.setUserName("Admin");
		u.setAdmin(true);
		u.setLikesGiven(0);
		u.setLikesReceived(0);
		userDB.save(u);

		for (int i = 0; i < 5; i++) {
			BlogPost post = new BlogPost();
			post.setAuthor(u);
			post.setTitle("Test Post #" + i);
			post.setText(LoremIpsum.loremIpsum);
			post.setTimestamp("2020-03-09 15:07");
			post.setLikes(new ArrayList<>());
			post.setTags(new ArrayList<>());
			post.setComments(new ArrayList<>());
			blogPostDB.save(post);
		}
	}
}
