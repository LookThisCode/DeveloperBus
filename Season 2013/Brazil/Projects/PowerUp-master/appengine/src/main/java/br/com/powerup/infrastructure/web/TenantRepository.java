package br.com.powerup.infrastructure.web;

import com.google.common.collect.ImmutableMap;

public class TenantRepository {

	private java.util.Map<String, Tenant> tenants = ImmutableMap.of(
	        "tim", new Tenant("tim"),
	        "claro", new Tenant("claro"),
	        "ciandt", new Tenant("ciandt"),
	        "vivo", new Tenant("vivo"),
	        "oi", new Tenant("oi")
	);
	
	public Tenant get(String urlTenantId) {	
		if (isInvalid(urlTenantId)) {
			throw new IllegalArgumentException(String.format("%s is an invalid tenantId.", urlTenantId));
		}
		return tenants.get(urlTenantId);
	}

	private boolean isInvalid(String urlTenantId) {
		return !tenants.containsKey(urlTenantId);
	}

}
