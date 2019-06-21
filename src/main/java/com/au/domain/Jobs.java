package com.au.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(includeFieldNames = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL) 
public class Jobs {

	@JsonProperty("_class")
	private String type;

	@JsonProperty("jobs")
	private Job[] allJobs;
	@JsonInclude(JsonInclude.Include.NON_NULL) 
	private String description;
	private String name;
	private String url;
	private String[] property;

}
