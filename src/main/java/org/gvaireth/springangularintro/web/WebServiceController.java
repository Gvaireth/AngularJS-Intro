package org.gvaireth.springangularintro.web;

import org.gvaireth.springangularintro.Greeting;
import org.gvaireth.springangularintro.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class WebServiceController {

	@Autowired
	GreetingService service;

	@RequestMapping("/greeting")
	public Greeting getGreeting() {
		return new Greeting("Hello!");
	}

	@RequestMapping("/greetingwithparam")
	public Greeting getGreetingWithParam(
			@RequestParam(name = "name", required = false, defaultValue = "stranger") String someone) {
		return new Greeting("Hello " + someone + "!");
	}

	@RequestMapping("/greetingwithmodel")
	public Greeting getGreetingWithModel(@Validated GreetingParams model) {
		return service.getGreeting(model.getTitle(), model.getName(), model.getSurname());
	}

	@RequestMapping("/greetingwithpath/{title}/{name}/{surname}/{age}")
	public Greeting getGreetingWithPath(@PathVariable String title, @PathVariable String name,
			@PathVariable String surname, @PathVariable Integer age) {
		System.out.println("He's " + age + " years old");
		return service.getGreeting(title, name, surname);
	}

	@RequestMapping(value = "/greetingwithpost", method = RequestMethod.POST)
	public Greeting getGreetingWithPost(@RequestBody GreetingParams body) {
		return service.getGreeting(body.getTitle(), body.getName(), body.getSurname());
	}

	@RequestMapping(value = "/greetingwithcodes", method = RequestMethod.POST)
	public ResponseEntity<Greeting> getGreetingWithCodes(@RequestBody GreetingParams body) {
		Greeting greeting = service.getGreeting(body.getTitle(), body.getName(), body.getSurname());
		HttpStatus status = HttpStatus.CREATED;
		System.out.println(body.getSurname());
		if ("Smith".equals(body.getSurname())) {
			status = HttpStatus.ALREADY_REPORTED;
		} else if ("Jackson".equals(body.getSurname())) {
			status = HttpStatus.GONE;
		}
		return new ResponseEntity<Greeting>(greeting, status);
	}

}
