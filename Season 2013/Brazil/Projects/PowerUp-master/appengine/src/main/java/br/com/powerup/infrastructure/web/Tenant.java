package br.com.powerup.infrastructure.web;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Tenant {

	@Id
	private String tenantId;

	public Tenant(String tenantId) {
		this.tenantId = tenantId;
	}

	public String id() {
		return tenantId;
	}
	
}