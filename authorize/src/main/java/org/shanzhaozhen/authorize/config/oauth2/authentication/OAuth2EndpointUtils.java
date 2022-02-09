package org.shanzhaozhen.authorize.config.oauth2.authentication;

import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.endpoint.PkceParameterNames;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility methods for the OAuth 2.0 Protocol Endpoints.
 *
 * @author Joe Grandja
 * @since 0.1.2
 */
public class OAuth2EndpointUtils {
	static final String ACCESS_TOKEN_REQUEST_ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2";

	private OAuth2EndpointUtils() {
	}

	static MultiValueMap<String, String> getParameters(HttpServletRequest request) {
		Map<String, String[]> parameterMap = request.getParameterMap();
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>(parameterMap.size());
		parameterMap.forEach((key, values) -> {
			if (values.length > 0) {
				for (String value : values) {
					parameters.add(key, value);
				}
			}
		});
		return parameters;
	}

	static Map<String, Object> getParametersIfMatchesAuthorizationCodeGrantRequest(HttpServletRequest request, String... exclusions) {
		if (!matchesAuthorizationCodeGrantRequest(request)) {
			return Collections.emptyMap();
		}
		Map<String, Object> parameters = new HashMap<>(getParameters(request).toSingleValueMap());
		for (String exclusion : exclusions) {
			parameters.remove(exclusion);
		}
		return parameters;
	}

	static boolean matchesAuthorizationCodeGrantRequest(HttpServletRequest request) {
		return AuthorizationGrantType.AUTHORIZATION_CODE.getValue().equals(
				request.getParameter(OAuth2ParameterNames.GRANT_TYPE)) &&
				request.getParameter(OAuth2ParameterNames.CODE) != null;
	}

	static boolean matchesPkceTokenRequest(HttpServletRequest request) {
		return matchesAuthorizationCodeGrantRequest(request) &&
				request.getParameter(PkceParameterNames.CODE_VERIFIER) != null;
	}

	static void throwError(String errorCode, String parameterName, String errorUri) {
		OAuth2Error error = new OAuth2Error(errorCode, "OAuth 2.0 Parameter: " + parameterName, errorUri);
		throw new OAuth2AuthenticationException(error);
	}

}
