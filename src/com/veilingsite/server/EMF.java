package com.veilingsite.server;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EMF {
	private static final EntityManagerFactory emfInstance =
	Persistence.createEntityManagerFactory("transactions-optional");
	
	/**
	 * to avoid multiple EntityManagerFactory objects, you cannot initialize one. use EMF.get() instead.
	 */
	private EMF() {}
	
	/**
	 * Returns the EntityManagerFactory.
	 * @return EntityManagerFactory The EMF that already exists.
	 */
	public static EntityManagerFactory get() {
		return emfInstance;
	}
}