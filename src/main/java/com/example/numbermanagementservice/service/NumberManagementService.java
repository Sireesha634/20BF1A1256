package com.example.numbermanagementservice.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.numbermanagementservice.controller.NumberManagementController;

@Service
public class NumberManagementService {

	final static Logger logger = LoggerFactory.getLogger(NumberManagementService.class.getName());

	@Autowired
	RestTemplate restTemplate;

	@SuppressWarnings("unchecked")
	public List<Integer> getNumbers(List<String> urls) {
		List<Integer> result = new ArrayList<>();
		List<LinkedHashMap<String, List<Integer>>> numberList = new ArrayList<>();
		for (String url : urls) {
			try {
				ResponseEntity<Object> list = restTemplate.exchange(url, HttpMethod.GET, null, Object.class);
				numberList.add((LinkedHashMap<String, List<Integer>>) list.getBody());
			} catch (Exception e) {
				logger.error("Internal server occured while connecting remote service", e);
			}
		}
		mergeObjects(numberList, result);

		return result.stream().distinct().sorted().collect(Collectors.toList());
	}

	private void mergeObjects(List<LinkedHashMap<String, List<Integer>>> numberList, List<Integer> result) {
		for (LinkedHashMap<String, List<Integer>> linkedHashMap : numberList) {
			result.addAll(linkedHashMap.get("numbers"));
		}
	}
}
