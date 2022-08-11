package org.shanzhaozhen.authorize.jackson;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.shanzhaozhen.uaa.pojo.dto.AuthUser;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.jackson2.*;

public class SecurityJackson2Module extends SimpleModule {

	public SecurityJackson2Module() {
		super(CoreJackson2Module.class.getName(), new Version(1, 0, 0, null, null, null));
	}

	@Override
	public void setupModule(SetupContext context) {
		SecurityJackson2Modules.enableDefaultTyping(context.getOwner());
		context.setMixInAnnotations(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixin.class);
		context.setMixInAnnotations(AuthUser.class, AuthUserMixin.class);
	}

}
