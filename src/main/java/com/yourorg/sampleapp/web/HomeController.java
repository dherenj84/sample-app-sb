package com.yourorg.sampleapp.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController extends SecureBaseController {

	private static final Logger log = LoggerFactory.getLogger(HomeController.class);

	@GetMapping("/greet")
	public String greet() {
		log.info("inside greet..");
		return "Hello World from a secure Controller!";
	}
}
