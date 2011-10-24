package com.veilingsite.server;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.veilingsite.shared.ServerService;
import com.veilingsite.shared.domain.Auction;
import com.veilingsite.shared.domain.Category;
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

	@Override
	public ArrayList<Auction> getAuctionList(User limitUser, Category limitCat) {
		EntityManager em = EMF.get().createEntityManager();
		ArrayList<Auction> l = new ArrayList<Auction>();
		String query;
		if(limitUser == null){
			query = "select from Auction";
		}
		else{
			query = "select from Auction WHERE owner = " + limitUser.getUserName();
		}
		
		try {
			l = new ArrayList<Auction>(em.createQuery(query).getResultList());
		} finally {
			em.close();
		}
		return l;
	}
	
	@Override
	public ArrayList<Category> getCategoryList() {
		EntityManager em = EMF.get().createEntityManager();
		ArrayList<Category> l = new ArrayList<Category>();
		
		String query = "select from Category";
		
		try {
			l = new ArrayList<Category>(em.createQuery(query).getResultList());
		} finally {
			em.close();
		}
		return l;
	}
	
	public Category addCategory(Category c) {
		EntityManager em = EMF.get().createEntityManager();
		
		if(getCategory(c.getTitle())!=null)
			return null;
			
		try {
			em.persist(c);
		} finally {
			em.close();
		}
		return c;
	}
	
	public Category getCategory(String s) {		
		EntityManager em = EMF.get().createEntityManager();
		Category c = null;
		try {
			Query q = em.createQuery("select from Category where title = ?1").setParameter(1, s);
			c = (Category) q.getSingleResult();
		} catch (NoResultException nre){
			return null;
		}
		finally {
			em.close();
		}
		return c;
	}
}

