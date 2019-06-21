package com.au.domain;

public class Parameter {
	
	private String LatestRunReport;
	//@NotNull
	//@Size(min = 5, max = 20)
	private String run;
	//@NotNull

	private String service;
	private String pod;

	public String getLatestRunReport() {
		return LatestRunReport;
	}

	public void setLatestRunReport(String latestRunReport) {
		LatestRunReport = latestRunReport;
	}

	public String getRun() {
		return run;
	}

	public void setRun(String run) {
		this.run = run;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getPod() {
		return pod;
	}

	public void setPod(String pod) {
		this.pod = pod;
	}
	

} 