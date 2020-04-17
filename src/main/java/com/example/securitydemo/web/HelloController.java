package com.example.securitydemo.web;


import java.util.Arrays;
import java.util.List;

import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.Jwt;

import com.example.securitydemo.controller.JournalRepository;
import com.example.securitydemo.domain.Journal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HelloController {
	   private static Logger LOGGER = LoggerFactory
			      .getLogger(HelloController.class);

@Autowired
JournalRepository repo;

	@RequestMapping(value="/", method = RequestMethod.GET)
    @ResponseBody
	public String index(ModelAndView modelAndView) {
		String result = repo.findAll().stream().map(Journal::toString).collect(Collectors.joining(","));
		return result;
	}
	@PostMapping("/journals")
	public Journal newJournal(OAuth2Authentication principal, @RequestBody Journal newJournal) throws JsonMappingException, JsonProcessingException {
		if (principalContainsScope(principal,"User.Read")) {
			LOGGER.info("#######Received and saving  "+newJournal.toString());
			return repo.save(newJournal);
		}
		return null;
	  }
    @RequestMapping(value = "/secret")
    @ResponseBody
    public String helloSecret(OAuth2Authentication principal) throws JsonMappingException, JsonProcessingException {

        return principalContainsScope(principal,"User.Read")? "In S3CR3T page - Hello " + principal.getName():"In secret page: Hello anonymous";
    }
    private boolean principalContainsScope(OAuth2Authentication principal,String scope) throws JsonMappingException, JsonProcessingException {
        if (principal.getDetails() instanceof OAuth2AuthenticationDetails) {
        	String jwtString = (OAuth2AuthenticationDetails.class.cast(principal.getDetails())).getTokenValue();
            Jwt jwt = JwtHelper.decode(jwtString);
            String jwtClaims = jwt.getClaims();
            JsonNode jwtClaimsJsonNode = new ObjectMapper().readTree(jwtClaims);
            JsonNode scopeNode = jwtClaimsJsonNode.get("scp");
            LOGGER.info(scopeNode.asText());
            String[] scopes = scopeNode.asText().split(" ");
            List<String> scopeList = Arrays.asList(scopes);
            boolean test = scopeList.contains(scope);
            return test;
        }
         else
            return false;
    }

}
