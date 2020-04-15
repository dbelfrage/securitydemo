package com.example.securitydemo;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import com.example.securitydemo.controller.JournalRepository;
import com.example.securitydemo.domain.Journal;



@SpringBootApplication
@EnableResourceServer
public class SecuritydemoApplication {
	private static final Logger log = LoggerFactory.getLogger(SecuritydemoApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(SecuritydemoApplication.class, args);
	}
	@Bean
	CommandLineRunner start(JournalRepository repo) {
		return args -> {
			log.info("> Deleting existing data....");
			repo.deleteAll();
			log.info("> Inserting new data....");
			repo.save(new Journal("Get to know spring boot","today I will learn spring boot","01/02/2016"));
			//repo.save(new Journal("spring boot reading ","tomorrow I will learn more spring boot","02/02/2016"));
	        log.info("> getting all data...");
	        repo.findAll().forEach(entry -> log.info(entry.toString()));
	        log.info("> getting data using like title ...");
	        repo.findByTitleLike("reading").forEach(entry -> log.info(entry.toString()));
	        log.info("> getting data using like summary ...");
	        repo.findBySummaryLike("learn").forEach(entry -> log.info(entry.toString()));
	        repo.findBySummaryLike("reading").forEach(entry -> log.info(entry.toString()));
	        log.info("> getting data using custom query ...");
	        repo.findByRegex("^Get").forEach(entry -> log.info(entry.toString()));
		};   
		}

}
