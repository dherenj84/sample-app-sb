package com.yourorg.sampleapp.security;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.AbstractContextMapper;
import org.springframework.stereotype.Service;

import com.yourorg.sampleapp.core.Role;
import com.yourorg.sampleapp.core.User;
import com.yourorg.sampleapp.model.AuthenticationRequest;

@Service
public class LdapAuthProvider implements AuthProvider {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	LdapTemplate ldapTemplate;

	@Override
	public User authenticate(AuthenticationRequest request) {
		ldapTemplate.authenticate(query().where("sAMAccountName").is(request.getUsername()), request.getPassword());
		log.info(this.getDnForUser(request.getUsername()));
		return ldapTemplate
				.search(query().where("sAMAccountName").is(request.getUsername()), new UserAttributesMapper()).get(0);
	}

	private String getDnForUser(String uid) {
		List<String> result = ldapTemplate.search(query().where("sAMAccountName").is(uid),
				new AbstractContextMapper<String>() {
					protected String doMapFromContext(DirContextOperations ctx) {
						return ctx.getNameInNamespace();
					}
				});

		if (result.size() != 1) {
			throw new RuntimeException("User not found or not unique");
		}

		return result.get(0);
	}

	private class UserAttributesMapper implements AttributesMapper<User> {
		public User mapFromAttributes(Attributes attrs) throws NamingException {
			User user = new User();
			user.setUsername((String) attrs.get("sAMAccountName").get());
			user.setName((String) attrs.get("cn").get());
			user.setLName((String) attrs.get("sn").get());
			user.setFName((String) attrs.get("givenName").get());
			StringTokenizer tkn = new StringTokenizer((attrs.get("memberOf").toString()).split(":")[1], ";");
			Set<Role> roles = new HashSet<>();
			while (tkn.hasMoreTokens()) {
				StringTokenizer tkn1 = new StringTokenizer(tkn.nextToken(), ",");
				while (tkn1.hasMoreTokens()) {
					String nxtToken = tkn1.nextToken();
					if (nxtToken.contains("CN=")) {
						Role role = new Role();
						role.setName(nxtToken.split("=")[1]);
						roles.add(role);
					}
				}
			}
			user.setRoles(roles);
			return user;
		}
	}

}
