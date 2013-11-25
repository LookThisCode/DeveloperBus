package br.com.powerup.infrastructure.persistence;

import static br.com.powerup.infrastructure.persistence.OfyService.ofy;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.googlecode.objectify.TxnType;
import com.googlecode.objectify.Work;

/**
 * @author fabio
 * 
 */
public class TransactInterceptor implements MethodInterceptor {

	/** Work around java's annoying checked exceptions */
	private static class ExceptionWrapper extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public ExceptionWrapper(final Throwable cause) {
			super(cause);
		}

		/** This makes the cost of using the ExceptionWrapper negligible */
		@Override
		public synchronized Throwable fillInStackTrace() {
			return this;
		}
	}

	/**
	 * The only trick here is that we need to wrap & unwrap checked exceptions
	 * that go through the Work interface
	 */
	@Override
	public Object invoke(final MethodInvocation inv) throws Throwable {
		final Transact attr = inv.getStaticPart().getAnnotation(Transact.class);
		final TxnType type = attr.value();

		try {
			return ofy().execute(type, new Work<Object>() {
				@Override
				public Object run() {
					try {
						return inv.proceed();
					} catch (final RuntimeException ex) {
						throw ex;
					} catch (final Throwable th) {
						throw new ExceptionWrapper(th);
					}
				}
			});
		} catch (final ExceptionWrapper ex) {
			throw ex.getCause();
		}
	}
}