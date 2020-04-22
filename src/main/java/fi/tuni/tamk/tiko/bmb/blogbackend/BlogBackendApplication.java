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
import java.util.TimeZone;

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
		System.out.println("Team Bare Metal Bunnies: Heikki Kangas, Laura Kanerva, Sanni Kytölä");
		System.out.println("curl http://localhost:8080/api/posts \n" +
				"curl http://localhost:8080/api/posts/1 \n" +
				"curl -X POST http://localhost:8080/api/posts -d \"{ < BlogPost > }\" -H \"Content-Type:application/json\" \n" +
				"curl -X PATCH http://localhost:8080/api/posts/{id} -d \"{ < BlogPost > }\" -H \"Content-Type:application/json\" \n" +
				"curl -X DELETE http://localhost:8080/api/posts/{id}");
		User u = new User();
		u.setUserName("Admin");
		u.setAdmin(true);
		userDB.save(u);

		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

		for (int i = 0; i < 5; i++) {
			BlogPost post = new BlogPost();
			post.setAuthor(u);
			post.setTitle("Test Post #" + i);
			post.setText(LoremIpsum.loremIpsum[0]);
			post.setLikes((int) (Math.random() * 10));
			post.setTags(new ArrayList<>());
			post.setComments(new ArrayList<>());
			blogPostDB.save(post);
		}
	}
}
