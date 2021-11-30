package org.shanzhaozhen.authorize.jackson;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.shanzhaozhen.security.dto.AuthUser;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.jackson2.*;

@SuppressWarnings("serial")
public class AuthUserJackson2Module extends SimpleModule {

	public AuthUserJackson2Module() {
		super(CoreJackson2Module.class.getName(), new Version(1, 0, 0, null, null, null));
	}

	@Override
	public void setupModule(SetupContext context) {
		SecurityJackson2Modules.enableDefaultTyping(context.getOwner());
		context.setMixInAnnotations(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixin.class);
		context.setMixInAnnotations(AuthUser.class, AuthUserMixin.class);
	}

}
