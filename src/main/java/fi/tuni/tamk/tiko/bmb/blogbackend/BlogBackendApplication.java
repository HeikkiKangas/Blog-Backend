package fi.tuni.tamk.tiko.bmb.blogbackend;

import fi.tuni.tamk.tiko.bmb.blogbackend.model.BlogPost;
import fi.tuni.tamk.tiko.bmb.blogbackend.repositories.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BlogBackendApplication implements CommandLineRunner {
	@Autowired
	BlogPostRepository blogPostDB;

	public static void main(String[] args) {
		SpringApplication.run(BlogBackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		for (int i = 0; i < 50; i++) {
			BlogPost b = new BlogPost();

		}
	}
}
