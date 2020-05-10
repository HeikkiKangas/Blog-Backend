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
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.TimeZone;

/**
 * Main class in the application.
 * Contains the main method.
 */
@SpringBootApplication
public class BlogBackendApplication implements CommandLineRunner {
	@Autowired
	BlogPostRepository blogPostDB;

	@Autowired
	UserRepository userDB;

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		SpringApplication.run(BlogBackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Team Bare Metal Bunnies: Heikki Kangas, Laura Kanerva, Sanni Kytölä");
		System.out.println(
				"curl http://localhost:8080/api/posts \n" +
				"curl http://localhost:8080/api/posts/{post id} \n" +
				"curl -X POST http://localhost:8080/api/posts -d \"{ < BlogPost > }\" -H \"Content-Type:application/json\" \n" +
				"curl -X PATCH http://localhost:8080/api/posts/{post id} -d \"{ < BlogPost > }\" -H \"Content-Type:application/json\" \n" +
				"curl -X DELETE http://localhost:8080/api/posts/{post id} \n" +
				"curl http://localhost:8080/api/generateposts \n" +
				"curl -X POST http://localhost:8080/api/posts/{post id}/like \n" +
				"curl -X POST http://localhost:8080/api/posts/{post id}/comment -d \"{ < Comment > }\" -H \"Content-Type:application/json\" \n" +
				"curl -X DELETE http://localhost:8080/api/posts/{post id}/comment/{comment id} \n" +
				"curl -X DELETE http://localhost:8080/api/posts/{post id}/comment/{comment id}/like"
		);

		User u = new User("admin", passwordEncoder().encode("admin"), true);
		userDB.save(u);

		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

		for (int i = 0; i < 5; i++) {
			BlogPost post = new BlogPost();
			post.setAuthor(u);
			post.setTitle("Test Post #" + i);
			post.setText(LoremIpsum.loremIpsum[0]);
			post.setLikes((int) (Math.random() * 10));
			post.setComments(new ArrayList<>());
			blogPostDB.save(post);
		}
	}

	/**
	 * Configures CORS and returns a new WebMvcConfigurer.
	 *
	 * @return	new WebMvcConfigurer
	 */
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry
						.addMapping("/**")
						.allowedOrigins("*")
						//.allowedOrigins("https://bmb-blog.herokuapp.com")
						.allowedMethods("GET", "POST", "DELETE", "PATCH");
			}
		};
	}
}
