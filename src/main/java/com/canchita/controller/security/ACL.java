package com.canchita.controller.security;

public interface ACL<E,G> {
	
	void addRole(Class<? extends E> subject);
	
	void addACL(G object, Class<? extends E>... subjects );
	
	boolean isAuthorized(E subject, G object);

}
