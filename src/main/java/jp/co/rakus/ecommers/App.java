package jp.co.rakus.ecommers;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class App {
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setMaxFileSize(51200 * 1024);		// ファイルの許容容量 (50MB)
		factory.setMaxRequestSize(51200 * 1024);	// テキスト込みのファイルの許容容量 (50MB)
		return factory.createMultipartConfig();
	}
}
