package com.au.domain;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(includeFieldNames = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Job {

	@JsonProperty("_class")
	private String type;
	private String name;
	private String url;
	private String color;
	private LastBuildData lastBuild;
	
	@Autowired
	private TestResult result;
	
}
