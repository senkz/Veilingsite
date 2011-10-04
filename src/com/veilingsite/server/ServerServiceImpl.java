package com.veilingsite.server;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.veilingsite.shared.ServerService;
import com.veilingsite.shared.domain.User;

public class ServerServiceImpl extends RemoteServiceServlet implements ServerService {
	
	public ServerServiceImpl() {}

	/**
	 * Get a single user based on a user name.
	 * @param s String User name of the user to be returned.
	 * @return User returns the User if found, else null.
	 */
	public User getUser(String s) {
		EntityManager em = EMF.get().createEntityManager();
		User u = null;
		try {
			Query q = em.createQuery("select us from User us where us.userName = ?1").setParameter(1, s);
			u = (User) q.getSingleResult();
		} finally {
			em.close();
		}
		return u;
	}

	/**
	 * adds User u to the DataStore.
	 * @param u User The user to be added.
	 */
	public Boolean addUser(User u) {
		EntityManager em = EMF.get().createEntityManager();
		
		if(getUser(u.getUserName())!=null)
			return false;
			
		try {
			em.persist(u);
		} finally {
			em.close();
		}
		return true;
	}

	/**
	 * Gets an ArrayList of all users currently in the DataStore.
	 * @return ArrayList<User> ArrayList of users.
	 */
	public ArrayList<User> getUserList() {
		EntityManager em = EMF.get().createEntityManager();
		ArrayList<User> l = new ArrayList<User>();
		try {
			l = new ArrayList<User>(em.createQuery("select from User").getResultList());
		} finally {
			em.close();
		}
		
		return l;
	}

	@Override
	public User loginUser(User u) {
		if(u==null)
			return null;
		
		User user = getUser(u.getUserName());
		if(user.getPassword().equals(u.getPassword())) {
			return user;
		} else {
			return null;
		}
	}
}
