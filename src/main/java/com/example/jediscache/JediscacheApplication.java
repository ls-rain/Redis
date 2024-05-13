package com.example.jediscache;

import jakarta.servlet.http.HttpSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@SpringBootApplication
public class JediscacheApplication {

	public static void main(String[] args) {
		SpringApplication.run(JediscacheApplication.class, args);
	}

	@GetMapping("/")
	public Map<String, String> home(HttpSession session){
		Integer visitCount = (Integer) session.getAttribute("visits");
		if(visitCount == null){
			visitCount = 0;
		}
		session.setAttribute("visits", ++visitCount);
		return Map.of("session id", session.getId(), "visits", visitCount.toString());
	}
}
