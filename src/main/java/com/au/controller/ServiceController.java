package com.au.controller;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.au.domain.Job;
import com.au.domain.Jobs;
import com.au.domain.Parameter;
import com.au.service.AnalyzerService;
import com.au.service.ParameterService;
import com.au.utils.TimeUtils;

@Controller
@RequestMapping("app")
public class ServiceController {
	private static final Logger logger = LoggerFactory.getLogger(ServiceController.class);
	
	@Autowired
	private ParameterService userService;
	@Autowired
	AnalyzerService jobAnalyzerService;
    @Autowired
    
	
	@Value("${report.file}")
	private String reportPath;

	@Value("${jenkins.url}")
	private String jenkinsUrl;

	@Value("${jenkins.api.path}")
	private String jenkinsApiPath;
	
	@Value("${jenkins.view}")
	private String jenkinsView;
	
	static {
	    //for localhost testing only
	    javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
	    new javax.net.ssl.HostnameVerifier(){

	        public boolean verify(String hostname,
	                javax.net.ssl.SSLSession sslSession) {
	            if (hostname.equals("localhost")) {
	                return true;
	            }
	            return false;
	        }
	    });
	}
	
	
	@GetMapping("ServiceData")
	public ModelAndView dashBoardView() {
		ModelAndView mav = new ModelAndView();
	    mav.setViewName("TestDashboard");
	    mav.addObject("parameter", new Parameter());
	    mav.addObject("allServices", getService());
	    mav.addObject("allJenkinsPOD", getJenkinsPODS());
	    return mav;
    }
	@PostMapping("ServiceData")
	public ModelAndView dashBoard(@Valid Parameter parameter, BindingResult result) {
		System.out.println("Parameter:"+parameter);
	    ModelAndView mav = new ModelAndView();
        if(result.hasErrors()) {
        	logger.info("Validation errors while submitting form.");
        	mav.setViewName("TestDashboard");
    	    mav.addObject("parameter", parameter);
    	    mav.addObject("allServices", getService());
    	    mav.addObject("allJenkinsPOD", getJenkinsPODS());
    	    return mav;
        }		
		userService.addServiceParameter(parameter);
	    mav.addObject("allService", userService.getAllServiceParameters());
	    mav.setViewName("serviceDetail");
    	logger.info("Form submitted successfully.");	    
	    return mav;
    }
	
	@RequestMapping(value="listOfJobs", method=RequestMethod.GET) 
	//@PostMapping("listOfJobs")
	public ModelAndView listOfJobs(/* @Valid Parameter parameter */) {
		
		  ModelAndView mav=new ModelAndView(); String
		 // requestUrl="https://aupadhyay:11eb0ee61ebe55c86313a0db5709329432@jenkins01.p13.eng.in03.qualys.com/view/all/api/json";
		  requestUrl="http://127.0.0.1:8080/view/all/api/json";
		  Jobs jobs = jobAnalyzerService.analyze(requestUrl, Jobs.class);
		  System.out.println("Jobs"+jobs);
		 
	    return mav;
    }
	
	@RequestMapping(value="buildJobs", method=RequestMethod.POST) 
	public void buildJobs(@Valid Parameter parameter) {
		HttpHeaders headers = new HttpHeaders();
	     
	     headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	     headers.setContentType(MediaType.APPLICATION_JSON);
	     String requestUrl="https://aupadhyay:11eb0ee61ebe55c86313a0db5709329432@jenkins01.p13.eng.in03.qualys.com/user/aupadhyay/my-views/view/all/job/AGMS_PerformanceCore2/buildWithParameters?token=11eb0ee61ebe55c86313a0db5709329432";
	     RestTemplate restTemplate = new RestTemplate();
	     Jobs jobs=new Jobs();
	     HttpEntity<Jobs> requestEntity = new HttpEntity<Jobs>(jobs, headers);
	     ResponseEntity<String> result = restTemplate.exchange(requestUrl, HttpMethod.POST, requestEntity, String.class);
	     System.out.println("----StatusCode"+result.getStatusCodeValue());
	     
    }
	
	
	@RequestMapping(value="getJobDetail", method=RequestMethod.GET) 
	public void getJobDetails() {
		ModelAndView mav=new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
	     
	     headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	     headers.setContentType(MediaType.APPLICATION_JSON);
	     String requestUrl="https://aupadhyay:11eb0ee61ebe55c86313a0db5709329432@jenkins01.p13.eng.in03.qualys.com//view/all/api/json";
	     RestTemplate restTemplate = new RestTemplate();
	     Jobs jobs=new Jobs();
	     HttpEntity<Jobs> requestEntity = new HttpEntity<Jobs>(jobs, headers);
	     ResponseEntity<String> result = restTemplate.exchange(requestUrl, HttpMethod.GET, requestEntity, String.class);
	     System.out.println("----StatusCode"+result.getStatusCodeValue());
    }
	
	private List<String> getService() {
		List<String> list = new ArrayList<>();
		list.add("AGMS(Asset Group Management Service)");
		list.add("SJMS (Scan Job Management Service)");
		list.add("HDS");
		list.add("PIS");
		return list;
	}
	
	private List<String> getJenkinsPODS() {
		List<String> list = new ArrayList<>();
		list.add("https://jenkins01.p13.eng.in03.qualys.com/");
		list.add("https://jenkins01.p04.eng.in03.qualys.com/");
		return list;
	}
	
	private String sumTotalDuration(Job[] jobs) {
		long totalDurationInMillis = Stream.of(jobs).mapToLong(job -> job.getLastBuild().getDuration()).sum();
		return TimeUtils.durationToHhMm(totalDurationInMillis);
	}
} 