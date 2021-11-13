package model.service;

import java.sql.SQLException;
import java.util.List;

//import model.AdoptApply;
import model.Animal;
import model.dao.AnimalDAO;

/**
 * 사용자 관리 API를 사용하는 개발자들이 직접 접근하게 되는 클래스.
 * UserDAO를 이용하여 데이터베이스에 데이터 조작 작업이 가능하도록 하며,
 * 데이터베이스의 데이터들을 이용하여 비지니스 로직을 수행하는 역할을 한다.
 * 비지니스 로직이 복잡한 경우에는 비지니스 로직만을 전담하는 클래스를 
 * 별도로 둘 수 있다.
 */
public class AnimalManager {
	private static AnimalManager animalMan = new AnimalManager();
//	private AdopterDAO adopterDAO;
	private AnimalDAO animalDAO;

	public AnimalDAO getAnimalDAO() {
		return this.animalDAO;
	}
	
	private AnimalManager() {
		try {
			animalDAO = new AnimalDAO();
		} catch (Exception e) {
			e.printStackTrace();
		}			
	}
	
	public static AnimalManager getInstance() {
		return animalMan;
	}
	
	public int create(Animal animal) throws SQLException, ExistingUserException {
		if (animalDAO.findAnimal(animal.getAnimal_id()) == true) {
			throw new ExistingUserException(animal.getAnimal_id() + "는 존재하는 아이디입니다.");
		}
		return animalDAO.create(animal);
	}

	public int update(Adopter user) throws SQLException, UserNotFoundException {

		return adopterDAO.update(user);
	}	

	public int remove(String user_id) throws SQLException, UserNotFoundException {

		return adopterDAO.remove(user_id);
	}

	public Adopter findUser(String user_id)
		throws SQLException, UserNotFoundException {
		Adopter user = adopterDAO.findUser(user_id);
		
		if (user == null) {
			throw new UserNotFoundException(user_id + "는 존재하지 않는 아이디입니다.");
		}		
		return user;
	}

//	public List<User> findUserList() throws SQLException {
//			return userDAO.findUserList();
//	}
//	
//	public List<User> findUserList(int currentPage, int countPerPage)
//		throws SQLException {
//		return userDAO.findUserList(currentPage, countPerPage);
//	}

	public boolean login(String user_id, String password)
		throws SQLException, UserNotFoundException, PasswordMismatchException {
		Adopter user = findUser(user_id);

		if (!user.matchPassword(password)) {
			throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
		}
		return true;
	}

	/*
	 * public List<User> makeFriends(String userId) throws Exception { return
	 * userAanlysis.recommendFriends(userId); }
	 * 
	 * public Community createCommunity(Community comm) throws SQLException { return
	 * commDAO.create(comm); }
	 * 
	 * public int updateCommunity(Community comm) throws SQLException { return
	 * commDAO.update(comm); }
	 * 
	 * public Community findCommunity(int commId) throws SQLException { Community
	 * comm = commDAO.findCommunity(commId);
	 * 
	 * List<User> memberList = userDAO.findUsersInCommunity(commId);
	 * comm.setMemberList(memberList);
	 * 
	 * int numOfMembers = userDAO.getNumberOfUsersInCommunity(commId);
	 * comm.setNumOfMembers(numOfMembers); return comm; }
	 * 
	 * public List<Community> findCommunityList() throws SQLException { return
	 * commDAO.findCommunityList(); }
	 * 
	 * public List<User> findCommunityMembers(int commId) throws SQLException {
	 * return userDAO.findUsersInCommunity(commId); }
	 */

	
	
}
