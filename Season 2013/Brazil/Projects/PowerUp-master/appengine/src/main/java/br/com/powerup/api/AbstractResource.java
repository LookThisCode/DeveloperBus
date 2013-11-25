package br.com.powerup.api;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Response;

public abstract class AbstractResource {

	public static <T> T checkNotNull(T reference) {
		if (reference == null) {
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}
		return reference;
	}

	public AbstractResource() {
		super();
	}

	protected CacheControl cacheControlFor(int aNumberOfSeconds) {
		CacheControl cacheControl = new CacheControl();
		cacheControl.setMaxAge(aNumberOfSeconds);
		return cacheControl;
	}

}