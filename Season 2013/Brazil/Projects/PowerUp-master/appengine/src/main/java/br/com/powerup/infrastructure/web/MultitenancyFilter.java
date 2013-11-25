package br.com.powerup.infrastructure.web;


import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.NamespaceManager;

/**
 * Enforces multitenancy by analysing the requested path
 * 
 * @author fabio 
 */
public class MultitenancyFilter implements Filter {

	private static final Logger logger = Logger.getLogger(MultitenancyFilter.class.getName());


	private TenantContext tenantContext;
	private TenantRepository tenantRepository;
	
	public MultitenancyFilter(TenantContext tenantContext,
			TenantRepository tenantRepository) {
		super();
		this.tenantContext = tenantContext;
		this.tenantRepository = tenantRepository;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
	}

	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String urlTenantId = determineTenantId(request);
		request.setAttribute("tenantId", urlTenantId);
			
		if (tenantContext.isInitialized()) {
			// tenant context is not initialized
			// it means this is the first request in the session
			// let's set the tenant context
			Tenant tenant = tenantRepository.get(urlTenantId);
			tenantContext.setTenant(tenant);			
		} else {
			// tenant context already initialized
			// check whether it is as expected
			String sessionTenantId = tenantContext.tenant().id();
			if (!sessionTenantId.equals(urlTenantId))
				throw new IllegalStateException("Unexpected tenant [session="
						+ sessionTenantId + ", url=" + urlTenantId + "]");
		}
			
        String oldNamespace = NamespaceManager.get();
		logger.fine("Backing up old namespace: " + oldNamespace);
        logger.fine("Setting namespace to: " + tenantContext.tenant().id());
        NamespaceManager.set(tenantContext.tenant().id());
        try {
            filterChain.doFilter(request, response);
        } finally {
        	logger.fine("Setting namespace back to: " + oldNamespace);
        	NamespaceManager.set(oldNamespace);
        }
		
	}

	private String determineTenantId(HttpServletRequest request) {
		logger.fine("Trying to resolve tenantId from request: " + request.getRequestURL());
		String[] domainParts = request.getServerName().split("\\.");
		String urlTenantId = domainParts[0];
		if ("www".equals(urlTenantId)) {
			urlTenantId = domainParts[1];
		}
		logger.fine("TenantId resolved to: " + urlTenantId);
		return urlTenantId;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}


	@Override
	public void destroy() {
	}

}