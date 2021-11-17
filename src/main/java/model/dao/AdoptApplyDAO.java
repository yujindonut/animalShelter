package model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import model.AdoptApply;
import model.Adopter;

public class AdoptApplyDAO {

	private JDBCUtil jdbcUtil = null;
	AdoptApply adoptApply = new AdoptApply();
	
	public AdoptApplyDAO() {			
		jdbcUtil = new JDBCUtil();	// JDBCUtil 媛앹껜 �깮�꽦
	}
	//癒쇱� DB�꽕怨꾩떆 CREATE SEQUENCE apply_id_seq;
	/*public AdoptApply create(AdoptApply adopt) throws SQLException {
		String sql = "INSERT INTO AdoptApply VALUES (apply_id_seq.nextval, ?, ?, ?, ?, SYSDATE, ?, ?)";		
		Object[] param = new Object[] {adopt.getContent(), 
				adopt.getMatched(), adopt.getUser_id(),adopt.getAnimal_id(), 
//				adopt.getApply_date(), : sysdate??
				adopt.getLiving_environment(),adopt.getHave_pets()};				
		jdbcUtil.setSqlAndParameters(sql, param);	// JDBCUtil �뿉 insert臾멸낵 留ㅺ컻 蹂��닔 �꽕�젙
						
		String key[] = {"apply_id"};	// PK 而щ읆�쓽 �씠由�     
		try {    
			jdbcUtil.executeUpdate(key);  // insert 臾� �떎�뻾 //??
		   	ResultSet rs = jdbcUtil.getGeneratedKeys();
		   	if(rs.next()) {
		   		int generatedKey = rs.getInt(1);   // �깮�꽦�맂 PK 媛�
		   		adopt.setApply_id(generatedKey); 	// id�븘�뱶�뿉 ���옣  
		   	}
		   	return adopt;
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		} finally {		
			jdbcUtil.commit();
			jdbcUtil.close();	// resource 諛섑솚
		}		
		return null;			
	}*/
	
	public int create(AdoptApply adoptApply) throws SQLException {
		/*
		 * System.out.println("create0.%%%%%%%%%%%%%%%%%%%%"+adoptApply);
		 * 
		 * LocalDate now = LocalDate.now();
		 * 
		 * System.out.println("LocalDate.%%%%%%%%%%%%%%%%%%%%"+adoptApply);
		 * 
		 * 
		 * SimpleDateFormat fm = new SimpleDateFormat("yyyy/MM/dd");
		 * 
		 * System.out.println("SimpleDate.%%%%%%%%%%%%%%%%%%%%"+adoptApply);
		 * 
		 * 
		 * String formatedNow = fm.format(now);
		 * 
		 * System.out.println("formateN.%%%%%%%%%%%%%%%%%%%%"+adoptApply);
		 * 
		 * 
		 * Date date = new Date(formatedNow);
		 * 
		 * System.out.println("create1.%%%%%%%%%%%%%%%%%%%%"+now +"::"+ date);
		 */
		
		/*String sql = "INSERT INTO AdoptApply VALUES (apply_id_seq.nextval, ?, ?, ?, ?, ?, ?, ?, ?";				
		Object[] param = new Object[] {adoptApply.getUser_id(), 
				adoptApply.getAnimal_id(), adoptApply.getContent(), adoptApply.getLiving_environment(),
				adoptApply.getHave_pets(), 0, date, 0};*/ 
		
		String sql = "INSERT INTO AdoptApply VALUES (apply_id_seq.nextval, ?, ?, ?, ?, ?, ?, SYSDATE, null)"; 
		Object[] param = new Object[] { adoptApply.getUser_id(), adoptApply.getAnimal_id(), adoptApply.getContent(),
	            adoptApply.getLiving_environment(), adoptApply.getHave_pets(),
	            0};
		jdbcUtil.setSqlAndParameters(sql, param);
	
		String key[] = {"apply_id"};	
		int generatedKey = 0;
		try {    
			jdbcUtil.executeUpdate(key);  
		   	ResultSet rs = jdbcUtil.getGeneratedKeys();
		   	if(rs.next()) {
		   		generatedKey = rs.getInt(1);   
		   		adoptApply.setAnimal_id(generatedKey); 	
		   	}
		   	return generatedKey;
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		} finally {		
			jdbcUtil.commit();
			jdbcUtil.close();	
		}		
		return 0;			
	}


	// 입양신청 승인시 matched = 1, approval_date 값 넣어주기
	public int approval(AdoptApply adoptApply) throws SQLException {
		/*
		 * LocalDate now = LocalDate.now(); SimpleDateFormat fm = new
		 * SimpleDateFormat("yyyy/MM/dd"); String formatedNow = fm.format(now); Date
		 * date = new Date(formatedNow);
		 */
		
		String sql = "UPDATE AdoptApply "
					+ "SET  matched=?, approval_date=SYSDATE "
					+ "WHERE apply_id=?";
		Object[] param = new Object[] {1, adoptApply.getApply_id()};
		
		//DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		
		try {		
			//Date date = new Date(df.parse(adoptApply.getApproval_date()).getTime());			
			jdbcUtil.setSqlAndParameters(sql, param);
			int result = jdbcUtil.executeUpdate();	// update 臾� �떎�뻾
			return result;
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		}
		finally {
			jdbcUtil.commit();
			jdbcUtil.close();	// resource 諛섑솚
		}		
		return 0;
	}
	
	// 입양신청 거절시 matched = -1, approval_date 값 넣어주기
	public int decline(AdoptApply adoptApply) throws SQLException {
		/*
		 * LocalDate now = LocalDate.now(); SimpleDateFormat fm = new
		 * SimpleDateFormat("yyyy/MM/dd"); String formatedNow = fm.format(now); Date
		 * date = new Date(formatedNow);
		 */
			
		String sql = "UPDATE AdoptApply "
						+ "SET  matched=?, approval_date=SYSDATE "
						+ "WHERE apply_id=?";
		Object[] param = new Object[] {-1, adoptApply.getApply_id()};
			
		//DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
			
		try {		
			//Date date = new Date(df.parse(adoptApply.getApproval_date()).getTime());			
			jdbcUtil.setSqlAndParameters(sql, param);
			int result = jdbcUtil.executeUpdate();	
			return result;
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		}
		finally {
			jdbcUtil.commit();
			jdbcUtil.close();	
		}		
		return 0;
	}

	// 신청 승인/거절 전에 매칭 미정인 신청리스트 찾을 때 사용
	public AdoptApply findAdoptApply(int apply_id) throws SQLException {
        String sql = "SELECT apply_id, user_id, animal_id, content, living_environment, have_pets, apply_matched, apply_date, image, user_name, animal_type, species  "
        			+ "FROM AdoptApply "
        			+ "WHERE apply_matched=? ";              
		jdbcUtil.setSqlAndParameters(sql, new Object[] {0});	
		
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		
		try {
			ResultSet rs = jdbcUtil.executeQuery();		
			if (rs.next()) {
				Date apply_date = new Date(rs.getDate("apply_date").getTime());
				String apply_dateString = df.format(apply_date);
				
				adoptApply = new AdoptApply(		
					rs.getInt("apply_id"),
					rs.getString("user_id"),
					rs.getInt("animal_id"),
					rs.getString("content"),
					rs.getString("living_environment"),
					rs.getString("have_pets"),
					rs.getInt("apply_matched"),
					apply_dateString,
					rs.getString("image"),
					rs.getString("user_name"),
					rs.getString("animal_type"),
					rs.getString("species")
				);
				return adoptApply;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// resource 諛섑솚
		}
		return null;
	}
	
	// 신청 승인/거절 이후에 신청 결과 리스트 찾을 때 사용
		public AdoptApply findAdoptApplyResult(String apply_id) throws SQLException {
	        String sql = "SELECT apply_id, user_id, animal_id, content, living_environment, have_pets, apply_matched, apply_date, approval_date, image, user_name, animal_type, species "
	        			+ "FROM AdoptApply "
	        			+ "WHERE apply_matched IN (?,?) ";              
			jdbcUtil.setSqlAndParameters(sql, new Object[] {-1, 1});	// JDBCUtil�뿉 query臾멸낵 留ㅺ컻 蹂��닔 �꽕�젙
			
			DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
			
			
			try {
				ResultSet rs = jdbcUtil.executeQuery();		// query �떎�뻾
				if (rs.next()) {
					Date apply_date = new Date(rs.getDate("apply_date").getTime());
					String apply_dateString = df.format(apply_date);
					
					Date approval_date = new Date(rs.getDate("approval_date").getTime());
					String approval_dateString = df.format(approval_date);
					
					adoptApply = new AdoptApply(		// User 媛앹껜瑜� �깮�꽦�븯�뿬 �븰�깮 �젙蹂대�� ���옣
						rs.getInt("apply_id"),
						rs.getString("user_id"),
						rs.getInt("animal_id"),
						rs.getString("content"),
						rs.getString("living_environment"),
						rs.getString("have_pets"),
						rs.getInt("apply_matched"),
						apply_dateString,
						approval_dateString,
						rs.getString("image"),
						rs.getString("user_name"),
						rs.getString("animal_type"),
						rs.getString("species")	
							);
					return adoptApply;
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				jdbcUtil.close();		// resource 諛섑솚
			}
			return null;
		}
	

	/**
	 * �쟾泥� �궗�슜�옄 �젙蹂대�� 寃��깋�븯�뿬 List�뿉 ���옣 諛� 諛섑솚
	 
	public List<User> findUserList() throws SQLException {
        String sql = "SELECT userId, name, email, NVL(commId,0) AS commId, cName " 
        		   + "FROM adopter u LEFT OUTER JOIN Community c ON u.commId = c.cId "
        		   + "ORDER BY userId";
		jdbcUtil.setSqlAndParameters(sql, null);		// JDBCUtil�뿉 query臾� �꽕�젙
					
		try {
			ResultSet rs = jdbcUtil.executeQuery();			// query �떎�뻾			
			List<User> userList = new ArrayList<User>();	// User�뱾�쓽 由ъ뒪�듃 �깮�꽦
			while (rs.next()) {
				User user = new User(			// User 媛앹껜瑜� �깮�꽦�븯�뿬 �쁽�옱 �뻾�쓽 �젙蹂대�� ���옣
					rs.getString("userId"),
					null,
					rs.getString("name"),
					rs.getString("email"),
					null,
					rs.getInt("commId"),
					rs.getString("cName"));
				userList.add(user);				// List�뿉 User 媛앹껜 ���옣
			}		
			return userList;					
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// resource 諛섑솚
		}
		return null;
	}
	*/
	
	/**
	 * �쟾泥� �궗�슜�옄 �젙蹂대�� 寃��깋�븳 �썑 �쁽�옱 �럹�씠吏��� �럹�씠吏��떦 異쒕젰�븷 �궗�슜�옄 �닔瑜� �씠�슜�븯�뿬
	 * �빐�떦�븯�뒗 �궗�슜�옄 �젙蹂대쭔�쓣 List�뿉 ���옣�븯�뿬 諛섑솚.
	 
	public List<User> findUserList(int currentPage, int countPerPage) throws SQLException {
		String sql = "SELECT userId, name, email, NVL(commId, 0), cName " 
					+ "FROM adopter u LEFT OUTER JOIN Community c ON u.commId = c.cId "
					+ "ORDER BY userId";
		jdbcUtil.setSqlAndParameters(sql, null,					// JDBCUtil�뿉 query臾� �꽕�젙
				ResultSet.TYPE_SCROLL_INSENSITIVE,				// cursor scroll 媛��뒫
				ResultSet.CONCUR_READ_ONLY);						
		
		try {
			ResultSet rs = jdbcUtil.executeQuery();				// query �떎�뻾			
			int start = ((currentPage-1) * countPerPage) + 1;	// 異쒕젰�쓣 �떆�옉�븷 �뻾 踰덊샇 怨꾩궛
			if ((start >= 0) && rs.absolute(start)) {			// 而ㅼ꽌瑜� �떆�옉 �뻾�쑝濡� �씠�룞
				List<User> userList = new ArrayList<User>();	// User�뱾�쓽 由ъ뒪�듃 �깮�꽦
				do {
					User user = new User(			// User 媛앹껜瑜� �깮�꽦�븯�뿬 �쁽�옱 �뻾�쓽 �젙蹂대�� ���옣
						rs.getString("userId"),
						null,
						rs.getString("name"),
						rs.getString("email"),
						null,
						rs.getInt("commId"),
						rs.getString("cName"));
					userList.add(user);							// 由ъ뒪�듃�뿉 User 媛앹껜 ���옣
				} while ((rs.next()) && (--countPerPage > 0));		
				return userList;							
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// resource 諛섑솚
		}
		return null;
	}*/

	/**
	 * �듅�젙 而ㅻ�ㅻ땲�떚�뿉 �냽�븳 �궗�슜�옄�뱾�쓣 寃��깋�븯�뿬 List�뿉 ���옣 諛� 諛섑솚
	
	public List<User> findUsersInCommunity(int communityId) throws SQLException {
        String sql = "SELECT userId, name, email, phone FROM adopter "
     				+ "WHERE commId = ?";                         
		jdbcUtil.setSqlAndParameters(sql, new Object[] {communityId});	// JDBCUtil�뿉 query臾멸낵 留ㅺ컻 蹂��닔 �꽕�젙
		
		try {
			ResultSet rs = jdbcUtil.executeQuery();		// query �떎�뻾
			List<User> memList = new ArrayList<User>();	// member�뱾�쓽 由ъ뒪�듃 �깮�꽦
			while (rs.next()) {
				User member = new User(			// User 媛앹껜瑜� �깮�꽦�븯�뿬 �쁽�옱 �뻾�쓽 �젙蹂대�� ���옣
					rs.getString("userId"),
					rs.getString("name"),
					rs.getString("email"),
					rs.getString("phone"));
				memList.add(member);			// List�뿉 Community 媛앹껜 ���옣
			}		
			return memList;					
				
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// resource 諛섑솚
		}
		return null;
	} */
	
	/**
	 * �듅�젙 而ㅻ�ㅻ땲�떚�뿉 �냽�븳 �궗�슜�옄�뱾�쓽 �닔瑜� count�븯�뿬 諛섑솚
	
	public int getNumberOfUsersInCommunity(int communityId) {
		String sql = "SELECT COUNT(userId) FROM adopter "
     				+ "WHERE commId = ?";              
		jdbcUtil.setSqlAndParameters(sql, new Object[] {communityId});	// JDBCUtil�뿉 query臾멸낵 留ㅺ컻 蹂��닔 �꽕�젙
		
		try {
			ResultSet rs = jdbcUtil.executeQuery();		// query �떎�뻾
			rs.next();										
			return rs.getInt(1);			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// resource 諛섑솚
		}
		return 0;
	}
	 */
	/**
	 * 二쇱뼱吏� �궗�슜�옄 ID�뿉 �빐�떦�븯�뒗 �궗�슜�옄媛� 議댁옱�븯�뒗吏� 寃��궗 
	 */
	/*public boolean existingUser(String user_id) throws SQLException {
		String sql = "SELECT count(*) FROM adopter WHERE user_id=?";      
		jdbcUtil.setSqlAndParameters(sql, new Object[] {user_id});	// JDBCUtil�뿉 query臾멸낵 留ㅺ컻 蹂��닔 �꽕�젙

		try {
			ResultSet rs = jdbcUtil.executeQuery();		// query �떎�뻾
			if (rs.next()) {
				int count = rs.getInt(1);
				return (count == 1 ? true : false);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// resource 諛섑솚
		}
		return false;
	}*/

	

}


