package com.au.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.au.domain.Parameter;
@Service
public class ParameterService {
	private List<Parameter> allparameters = new ArrayList<>();
	public List<Parameter> getAllServiceParameters(){
		return allparameters;
	}
	public void addServiceParameter(Parameter parameter) {
		allparameters.add(parameter);
	}
}
