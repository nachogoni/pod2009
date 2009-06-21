package com.canchita.controller.security;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.canchita.model.user.Administrator;
import com.canchita.model.user.CommonUser;
import com.canchita.model.user.Guest;
import com.canchita.model.user.User;

public class ACLCanchita implements ACL<User, String> {

	// Singleton instance
	private static final ACL<User, String> INSTANCE = new ACLCanchita();

	private Map<Class<? extends User>, List<String>> acl;
	private List<String> resources;
	
	// Unchecked warning when creating array
	@SuppressWarnings("unchecked")
	private ACLCanchita() {
		this.acl = new HashMap<Class<? extends User>, List<String>>();
		this.resources = new LinkedList<String>();
		// We add the ACL's manually
		// TODO someone should call this ACL

		Class<Guest> guest = Guest.class;
		Class<CommonUser> commonUser = CommonUser.class;
		Class<Administrator> administrator = Administrator.class;

		this.addRole(guest);
		this.addRole(commonUser);
		this.addRole(administrator);

		//TODO: Delete TEST urls
		this.addACL("/test/", guest, commonUser, administrator);
		this.addACL("/test/charly", guest, commonUser, administrator);
		this.addACL("/test/pablo", guest, commonUser, administrator);
		this.addACL("/test/juani", guest, commonUser, administrator);
		this.addACL("/test/lombo", guest, commonUser, administrator);
		this.addACL("/test/maxi", guest, commonUser, administrator);
		this.addACL("/test/martin", guest, commonUser, administrator);
		
		this.addACL("/", guest, commonUser, administrator);
		this.addACL("/error/403", guest, commonUser, administrator);
		this.addACL("/error/404", guest, commonUser, administrator);
		this.addACL("/error/500", guest, commonUser, administrator);
		this.addACL("/error/servererror", guest, commonUser, administrator);

		this.addACL("/user/login", guest, commonUser, administrator);
		this.addACL("/user/register", guest);
		this.addACL("/user/finishregister", guest);
		this.addACL("/field/list", guest, commonUser, administrator);
		this.addACL("/ListComplex", guest, commonUser, administrator);
		this.addACL("/DetailedViewComplex", guest, commonUser, administrator);
		this.addACL("/field/detailedview", guest, commonUser, administrator);

		this.addACL("/ListRSS", commonUser, administrator);
		this.addACL("/LastFields", guest, commonUser, administrator);
		this.addACL("/DeadBooking", guest, commonUser, administrator);

		this.addACL("/field/book", commonUser);
		this.addACL("/field/bookmany", commonUser);
		this.addACL("/field/getavailablehours", commonUser);
		this.addACL("/field/getallhours", commonUser);
		this.addACL("/user/profile", commonUser, administrator);
		this.addACL("/user/logout", commonUser, administrator);
		this.addACL("/user/home", commonUser, administrator);

		this.addACL("/admin/", administrator);
		this.addACL("/admin/email", administrator);
		
		this.addACL("/AddComplex", administrator);
		this.addACL("/ModifyComplex", administrator);
		this.addACL("/DeleteComplex", administrator);

		this.addACL("/field/add", administrator);
		this.addACL("/field/modify", administrator);
		this.addACL("/field/delete", administrator);
		
		this.addACL("/booking/list", administrator);
		this.addACL("/booking/listfield", administrator);
		this.addACL("/booking/listcomplex", administrator);
		this.addACL("/booking/pay", administrator);
		this.addACL("/booking/fullpay", administrator);

		this.addACL("/scoresystem/ShowScoreSystem", administrator);
		this.addACL("/scoresystem/modify", administrator);
		
		this.addACL("/ListExpirationPolicy", administrator, commonUser);
		this.addACL("/ModifyExpirationPolicy", administrator);
		this.addACL("/AddExpirationPolicy", administrator);
		this.addACL("/DeleteExpirationPolicy", administrator);
		this.addACL("/field/ListFieldExpirationPolicy", administrator, commonUser);
		this.addACL("/field/ModifyFieldExpirationPolicy", administrator);
		this.addACL("/field/AddFieldExpirationPolicy", administrator);
		this.addACL("/field/DeleteFieldExpirationPolicy", administrator);
		
		this.resources.add("img");
		this.resources.add("js");
		this.resources.add("css");

	}

	public static ACL<User, String> getInstance() {
		return INSTANCE;
	}

	@Override
	public boolean isAuthorized(User subject, String object) {

		if (! this.acl.containsKey(subject.getClass())) {
			throw new IllegalArgumentException("Sujeto inexistente");
		}
		
		return this.isPublic(object)
				|| this.acl.get(subject.getClass()).contains(object);

	}

	private boolean isPublic(String object) {
		String[] resource = object.split("/");
		
		if( resource.length < 2 ) {
			return true;
		}
		
		return this.resources.contains(resource[1]);
		
	}

	private void addACL(String object, Class<? extends User> subject) {

		if (!this.acl.containsKey(subject)) {
			throw new IllegalArgumentException("Sujeto inexistente");
		}

		this.acl.get(subject).add(object);
	}

	@Override
	public void addRole(Class<? extends User> subject) {
		this.acl.put(subject, new LinkedList<String>());

	}

	@Override
	public void addACL(String object, Class<? extends User>... subjects) {

		for (Class<? extends User> subject : subjects) {
			this.addACL(object, subject);
		}

	}

}
