package com.veilingsite.server;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.gwt.user.server.rpc.UnexpectedException;
import com.veilingsite.shared.ServerService;
import com.veilingsite.shared.domain.Auction;
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
		} catch (NoResultException nre){
			return null;
		}
		finally {
			em.close();
		}
		return u;
	}

	/**
	 * adds User u to the DataStore.
	 * @param u User The user to be added.
	 */
	public User addUser(User u) {
		EntityManager em = EMF.get().createEntityManager();
		
		if(getUser(u.getUserName())!=null)
			return null;
			
		try {
			em.persist(u);
		} finally {
			em.close();
		}
		return u;
	}
	public void updateUser(User user) {
		EntityManager em = EMF.get().createEntityManager();
		  try{
		    User userx = em.find(User.class, user.getUserName());
		    userx.setUserName(user.getUserName()); 
		    userx.setEmail(user.getEmail());
		    userx.setFirstName(user.getFirstName()); 
		    userx.setSurName(user.getSurName()); 
		    userx.setMobilePhoneNumber(user.getMobilePhoneNumber()); 
		    userx.setPassword(user.getPassword());
		  } finally {
		    em.close();
		  }
		}
	
	public Auction addAuction(Auction a) {
		EntityManager em = EMF.get().createEntityManager();
		System.out.println(a.getDescription());
		try {
			em.persist(a);
		} finally {
			em.close();
		}
		return a;
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
