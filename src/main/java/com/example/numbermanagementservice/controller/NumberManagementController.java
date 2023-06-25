package com.example.numbermanagementservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.numbermanagementservice.service.NumberManagementService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class NumberManagementController {
	final static Logger logger = LoggerFactory.getLogger(NumberManagementController.class.getName());

	@Autowired
	NumberManagementService numberManagementService;

	@GetMapping("/numbers")
	public ResponseEntity<List<Integer>> getNumbers(@RequestParam("url") List<String> urls) {
		logger.info("request params", urls);
		return ResponseEntity.status(HttpStatus.OK).body(numberManagementService.getNumbers(urls));

	}

}
