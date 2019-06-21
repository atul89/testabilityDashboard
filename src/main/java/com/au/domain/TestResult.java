package com.au.domain;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Component
@ToString(includeFieldNames = true)
public class TestResult {

	private int passed;
	private int failed;
	private int pending;
	private int skipped;
	private int total;

}
