package com.alow.model;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;


@Entity
public class Pessoa {
	
	@Id
	private Long id;
	@Index
	private String googleUserId;
	private String googleDisplayName;
	private String googlePublicProfileUrl;
	private String googlePublicProfilePhotoUrl;
	private List<String> devices = new ArrayList<>();
	private String registrationId;
	
	public Pessoa(String vGoogleUserId, String vGoogleDisplayName, String vGooglePublicProfileUrl, String vGooglePublicProfilePhotoUrl){
		if(vGoogleUserId == null){
			throw new IllegalArgumentException("parametro nulo no construtor de pessoa");
		}
		this.googleUserId = vGoogleUserId;
		this.googleDisplayName = vGoogleDisplayName;
		this.googlePublicProfileUrl = vGooglePublicProfileUrl;
		this.googlePublicProfilePhotoUrl = vGooglePublicProfilePhotoUrl;
	}
	
	public Pessoa(String vGoogleUserId, String vRegistrationId, String vDeviceId){
		if(vGoogleUserId == null || vRegistrationId == null || vDeviceId == null){
			throw new IllegalArgumentException("parametros nulos no construtor de pessoa");
		}
		this.googleUserId = vGoogleUserId;
		this.registrationId = vRegistrationId;
		addDevice(vDeviceId);
	}
	
	public Pessoa(){}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getGoogleUserId() {
		return googleUserId;
	}
	public String getGoogleDisplayName() {
		return googleDisplayName;
	}
	public String getGooglePublicProfileUrl() {
		return googlePublicProfileUrl;
	}
	public String getGooglePublicProfilePhotoUrl() {
		return googlePublicProfilePhotoUrl;
	}
	
	public List<String> getDevices() {
		return devices;
	}

	public void setDevices(List<String> devices) {
		this.devices = devices;
	}

	public String getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}

	public void setGoogleUserId(String googleUserId) {
		this.googleUserId = googleUserId;
	}

	public void setGoogleDisplayName(String googleDisplayName) {
		this.googleDisplayName = googleDisplayName;
	}

	public void setGooglePublicProfileUrl(String googlePublicProfileUrl) {
		this.googlePublicProfileUrl = googlePublicProfileUrl;
	}

	public void setGooglePublicProfilePhotoUrl(String googlePublicProfilePhotoUrl) {
		this.googlePublicProfilePhotoUrl = googlePublicProfilePhotoUrl;
	}
	
	public List<String> addDevice(String deviceId){
		if(!this.devices.contains(deviceId)){
			devices.add(deviceId);
		}
		return this.devices;
	}
}
