package edu.shu.redis102;

import edu.shu.redis102.service.DummyCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@Slf4j
@SpringBootApplication
@EnableCaching
public class Redis102Application implements CommandLineRunner {

	@Autowired
	DummyCacheService dummyCacheService;

	public static void main(String[] args) {
		SpringApplication.run(Redis102Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String firstString = dummyCacheService.cacheThis();
		log.info("First: {}", firstString);
		String secondString = dummyCacheService.cacheThis();
		log.info("Second: {}", secondString);
		String thirdString = dummyCacheService.cacheThat();
		log.info("Second: {}", thirdString);
		String forthString = dummyCacheService.cacheThat();
		log.info("Second: {}", forthString);

		// dummyCacheService.cleanCacheThat();
	}
}
