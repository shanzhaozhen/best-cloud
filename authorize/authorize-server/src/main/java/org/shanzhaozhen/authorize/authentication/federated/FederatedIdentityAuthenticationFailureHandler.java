package org.shanzhaozhen.authorize.authentication.federated;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;


public class FederatedIdentityAuthenticationFailureHandler implements AuthenticationFailureHandler {

	protected final Log logger = LogFactory.getLog(getClass());

	private String defaultFailureUrl = "/login?error";

	private boolean forwardToDestination = false;

	private boolean allowSessionCreation = true;

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	public FederatedIdentityAuthenticationFailureHandler() {
	}

	public FederatedIdentityAuthenticationFailureHandler(String defaultFailureUrl) {
		setDefaultFailureUrl(defaultFailureUrl);
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
										AuthenticationException exception) throws IOException, ServletException {
		if (this.defaultFailureUrl == null) {
			if (this.logger.isTraceEnabled()) {
				this.logger.trace("Sending 401 Unauthorized error since no failure URL is set");
			}
			else {
				this.logger.debug("Sending 401 Unauthorized error");
			}
			response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
			return;
		}
		saveException(request, exception);

		String msg = exception.getMessage();

		if (this.forwardToDestination) {
			this.logger.debug("Forwarding to " + this.defaultFailureUrl);
			request.getRequestDispatcher(this.defaultFailureUrl).forward(request, response);
		}
		else {
			this.redirectStrategy.sendRedirect(request, response,
					this.defaultFailureUrl + (StringUtils.hasText(msg) ? "&msg=" + msg : ""));
		}
	}

	protected final void saveException(HttpServletRequest request, AuthenticationException exception) {
		if (this.forwardToDestination) {
			request.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, exception);
			return;
		}
		HttpSession session = request.getSession(false);
		if (session != null || this.allowSessionCreation) {
			request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, exception);
		}
	}

	public void setDefaultFailureUrl(String defaultFailureUrl) {
		Assert.isTrue(UrlUtils.isValidRedirectUrl(defaultFailureUrl),
				() -> "'" + defaultFailureUrl + "' is not a valid redirect URL");
		this.defaultFailureUrl = defaultFailureUrl;
	}

	protected boolean isUseForward() {
		return this.forwardToDestination;
	}

	public void setUseForward(boolean forwardToDestination) {
		this.forwardToDestination = forwardToDestination;
	}

	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}

	protected RedirectStrategy getRedirectStrategy() {
		return this.redirectStrategy;
	}

	protected boolean isAllowSessionCreation() {
		return this.allowSessionCreation;
	}

	public void setAllowSessionCreation(boolean allowSessionCreation) {
		this.allowSessionCreation = allowSessionCreation;
	}

}
