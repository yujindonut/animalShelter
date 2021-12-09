package model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Qna;

public class QnaDAO {

private JDBCUtil jdbcUtil = null;
	
	public QnaDAO() {			
		jdbcUtil = new JDBCUtil();
	}

	public int create(Qna qna) throws SQLException {
		String sql = "INSERT INTO Qna "
				+ "VALUES (qna_id_seq.nextval, ?, ?, ?, ?, ?)";		
		Object[] param = new Object[] {
				qna.getQna_title(),
				qna.getQna_content(),
				qna.getQna_writer(),
				qna.getQna_category_id()
			};				
		jdbcUtil.setSqlAndParameters(sql, param);
						
		String key[] = {"qna_id"};
		try {    
			int result = jdbcUtil.executeUpdate(key); 
		   	ResultSet rs = jdbcUtil.getGeneratedKeys();
		   	if(rs.next()) {
		   		int generatedKey = rs.getInt(1);  
		   		qna.setQna_id(generatedKey); 	
		   	}
		   	return result;
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		} finally {		
			jdbcUtil.commit();
			jdbcUtil.close();	
		}		
		return 0;			
	}
	
	public int update(Qna qna) throws SQLException {
		String sql = "UPDATE Qna "
					+ "SET  title=?, content=?, qna_category_id=? "
					+ "WHERE qna_id=?";
		Object[] param = new Object[] {
				qna.getQna_title(), 
				qna.getQna_content(),
				qna.getQna_category_id(),
				qna.getQna_id()
				};				
		jdbcUtil.setSqlAndParameters(sql, param);
			
		try {				
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

	public int remove(int qna_id) throws SQLException {
		String sql = "DELETE FROM Qna WHERE qna_id=?";		
		jdbcUtil.setSqlAndParameters(sql, new Object[] {qna_id});	

		try {				
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
	
	public Qna findQna(int qna_id) throws SQLException {
        String sql = "SELECT qna_id, qna_category_id, qna_title, qna_writer, qna_type, qna_content, qna_password, qna_date "
     		   + "FROM Qna q, Adopter a " 
     		  + "WHERE q.user_id = a.user_id and q.qna_id=? ";  
		jdbcUtil.setSqlAndParameters(sql, new Object[] {qna_id});	

		try {
			ResultSet rs = jdbcUtil.executeQuery();		
			List<Qna> qnaList = new ArrayList<Qna>();	
			if (rs.next()) {	
				String qna_writer = rs.getString("writer");
		        if(qna_writer == null) { 
		        	qna_writer = "(알 수 없음)";
		        }
				Qna qna = new Qna(		
					qna_id,
					qna_writer,
					rs.getString("qna_title"),
					rs.getString("qna_content"),
					rs.getString("qna_password"),
					rs.getDate("qna_date"),
					rs.getInt("qna_category_id"),
					rs.getString("qna_type")
					);
				return qna;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		
		}
		return null;
	}
	
	public List<Qna> findQnaList() throws SQLException {
        String sql = "SELECT qna_id, qna_category_id, title, user_id, qna_type, content, password, user_name "
      		   + "FROM Qna q, Adopter a " 
      		 + "WHERE q.user_id = a.user_id "
        		  + "ORDER BY qna_id ";        
        			
		jdbcUtil.setSqlAndParameters(sql, null);		
					
		try {
			ResultSet rs = jdbcUtil.executeQuery();			
			List<Qna> qnaList = new ArrayList<Qna>();	
			while (rs.next()) {
				String qna_writer = rs.getString("writer");
		        if(qna_writer == null) { 
		        	qna_writer = "(알 수 없음)";
		        }
				Qna animal = new Qna(
						rs.getInt("qna_id"),
						qna_writer,
						rs.getString("qna_title"),
						rs.getString("qna_content"),
						rs.getString("qna_password"),
						rs.getDate("qna_date"),
						rs.getInt("qna_category_id"),
						rs.getString("qna_type")
						);
				qnaList.add(animal);			
			}		
			return qnaList;					
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();
		}
		return null;
	}

	public int findQnaCategoryId(String qna_type) {
		String sql = "SELECT qna_category_id "
	     		   + "FROM Qna q, qna_category c " 
	     		  + "WHERE q.qna_type = c.qna_type ";  
			jdbcUtil.setSqlAndParameters(sql, new Object[] {null});	

			try {
				ResultSet rs = jdbcUtil.executeQuery();		
				if (rs.next()) {						
					return rs.getInt("qna_category_id");
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				jdbcUtil.close();		
			}
			return 0;
	}
	
	public List<Qna> findUserQnaList(String user_id) throws SQLException {
        String sql = "SELECT qna_id, qna_category_id, title, qna_type, content, password, user_name "
        			+ "FROM Qna q, Adopter a " 
        			+ "WHERE q.user_id = a.user_id and q.user_id=? "
        			+ "ORDER BY qna_id ";        
        			
		jdbcUtil.setSqlAndParameters(sql, new Object[] {user_id});		
					
		try {
			ResultSet rs = jdbcUtil.executeQuery();			
			List<Qna> qnaList = new ArrayList<Qna>();	
			while (rs.next()) {
				Qna qna = new Qna(
						rs.getInt("qna_id"),
						user_id,
						rs.getString("qna_title"),
						rs.getString("qna_content"),
						rs.getString("qna_password"),
						rs.getDate("qna_date"),
						rs.getInt("qna_category_id"),
						rs.getString("qna_type")
						);
				qnaList.add(qna);			
			}		
			return qnaList;					
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();
		}
		return null;
	}
}
