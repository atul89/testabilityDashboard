package com.au.domain;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.au.utils.TimeUtils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(includeFieldNames = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LastBuildData {

	private boolean building;
	private long duration;
	private String durationInStr;
	private String timestamp;
	private String url;
	private int number;
	private String result;

	public void setDuration(long duration) {
		this.duration = duration;
		this.durationInStr = TimeUtils.durationToHhMm(duration);
	}

	public void setTimestamp(long timestamp) {
		Timestamp stamp = new Timestamp(timestamp);
		Date date = new Date(stamp.getTime());
		this.timestamp = new SimpleDateFormat("dd/MM/yyyy").format(date);
	}
}
