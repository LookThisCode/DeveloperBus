package br.com.powerup.infrastructure.web;

import java.io.Serializable;

public class TenantContext implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1941982389490487884L;
	
	private Tenant tenant;

	public Tenant tenant() {
		return tenant;
	}

	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}

	public boolean isInitialized() {
		return tenant() == null;
	}
	
}
