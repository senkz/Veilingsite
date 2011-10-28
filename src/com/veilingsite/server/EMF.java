package com.veilingsite.server;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.eclipse.persistence.jpa.PersistenceProvider;

public class EMF {
	private static final PersistenceProvider provider = new PersistenceProvider();
	private static final Map<String, Object> overrides = new HashMap<String, Object>();
	{
		overrides.put(PersistenceUnitProperties.ECLIPSELINK_PERSISTENCE_XML, "..\\META-INF\\persistence.xml");
	}
	private static final EntityManagerFactory emfInstance =	provider.createEntityManagerFactory("online", overrides);
	
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