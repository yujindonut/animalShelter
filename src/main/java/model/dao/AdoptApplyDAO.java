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
import model.Animal;

public class AdoptApplyDAO {

	private JDBCUtil jdbcUtil = null;
	AdoptApply adoptApply = new AdoptApply();

	public AdoptApplyDAO() {
		jdbcUtil = new JDBCUtil(); // JDBCUtil 揶쏆빘猿 占쎄문占쎄쉐
	}

	public int create(AdoptApply adoptApply) throws SQLException {
		String sql = "INSERT INTO AdoptApply VALUES (apply_id_seq.nextval, ?, ?, ?, ?, ?, ?, SYSDATE, null)";
		Object[] param = new Object[] { adoptApply.getUser_id(), adoptApply.getAnimal_id(), adoptApply.getContent(),
				adoptApply.getLiving_environment(), adoptApply.getHave_pets(), 0 };
		jdbcUtil.setSqlAndParameters(sql, param);

		String key[] = { "apply_id" };
		int generatedKey = 0;
		try {
			jdbcUtil.executeUpdate(key);
			ResultSet rs = jdbcUtil.getGeneratedKeys();
			if (rs.next()) {
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

	public int approval(AdoptApply adoptApply) throws SQLException {

		String sql = "UPDATE AdoptApply " + "SET  matched=?, approval_date=SYSDATE " + "WHERE apply_id=?";
		Object[] param = new Object[] { 1, adoptApply.getApply_id() };

		try {
			jdbcUtil.setSqlAndParameters(sql, param);
			int result = jdbcUtil.executeUpdate(); // update 눧占 占쎈뼄占쎈뻬
			return result;
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		} finally {
			jdbcUtil.commit();
			jdbcUtil.close(); // resource 獄쏆꼹 넎
		}
		return 0;
	}

	public int decline(AdoptApply adoptApply) throws SQLException {

		String sql = "UPDATE AdoptApply " + "SET  matched=?, approval_date=SYSDATE " + "WHERE apply_id=?";
		Object[] param = new Object[] { -1, adoptApply.getApply_id() };

		try {

			jdbcUtil.setSqlAndParameters(sql, param);
			int result = jdbcUtil.executeUpdate();
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

	// view
	public AdoptApply findAdoptApply(int apply_id) throws SQLException {
		String sql = "SELECT adp.apply_id, adp.user_id, adp.animal_id, adp.content, adp.living_environment, have_pets, adp.apply_matched, adp.apply_date, a.image, u.user_name, c.animal_type, c.species  "
				+ "FROM AdoptApply adp JOIN A u ON adp.user_id = u.user_id and Animal a JOIN adp ON a.animal_id = adp.animal_id and a JOIN Category c ON a.category_id = c.category_id"
				+ "WHERE apply_id=? ";
		jdbcUtil.setSqlAndParameters(sql, new Object[] { apply_id });

		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

		try {
			ResultSet rs = jdbcUtil.executeQuery();
			if (rs.next()) {
				Date apply_date = new Date(rs.getDate("apply_date").getTime());
				String apply_dateString = df.format(apply_date);

				adoptApply = new AdoptApply(rs.getInt("apply_id"), rs.getString("user_id"), rs.getInt("animal_id"),
						rs.getString("content"), rs.getString("living_environment"), rs.getString("have_pets"),
						rs.getInt("apply_matched"), apply_dateString, rs.getString("image"), rs.getString("user_name"),
						rs.getString("animal_type"), rs.getString("species"));
				return adoptApply;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close(); // resource 獄쏆꼹 넎
		}
		return null;
	}

	public AdoptApply findAdoptApplyResult(String apply_id) throws SQLException {
		String sql = "SELECT apply_id, user_id, animal_id, content, living_environment, have_pets, apply_matched, apply_date, approval_date, image, user_name, animal_type, species "
				+ "FROM AdoptApply a" + "WHERE apply_matched = ? ";
		jdbcUtil.setSqlAndParameters(sql, new Object[] { 1 }); 

		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

		try {
			ResultSet rs = jdbcUtil.executeQuery(); // query 占쎈뼄占쎈뻬
			if (rs.next()) {
				Date apply_date = new Date(rs.getDate("apply_date").getTime());
				String apply_dateString = df.format(apply_date);

				Date approval_date = new Date(rs.getDate("approval_date").getTime());
				String approval_dateString = df.format(approval_date);

				adoptApply = new AdoptApply( 
						rs.getInt("apply_id"), rs.getString("user_id"), rs.getInt("animal_id"), rs.getString("content"),
						rs.getString("living_environment"), rs.getString("have_pets"), rs.getInt("apply_matched"),
						apply_dateString, approval_dateString, rs.getString("image"), rs.getString("user_name"),
						rs.getString("animal_type"), rs.getString("species"));
				return adoptApply;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close(); // resource 獄쏆꼹 넎
		}
		return null;
	}

	public List<AdoptApply> findAdoptApplyList() throws SQLException {
		String sql = "SELECT adp.apply_id, adp.user_id,  u.user_name, adp.animal_id,adp.apply_matched, adp.apply_date "
				+ "FROM AdoptApply adp JOIN User u ON adp.user_id = u.user_id " + "ORDER BY apply_id";
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

		try {
			ResultSet rs = jdbcUtil.executeQuery(); // query 占쎈뼄占쎈뻬
			List<AdoptApply> adoptApplyList = new ArrayList<AdoptApply>();
			while (rs.next()) {
				Date apply_date = new Date(rs.getDate("apply_date").getTime());
				String apply_dateString = df.format(apply_date);
				AdoptApply adoptApply = new AdoptApply(rs.getInt("apply_id"), rs.getString("user_id"),
						rs.getInt("animal_id"), rs.getInt("apply_matched"), apply_dateString,
						rs.getString("user_name"));
				adoptApplyList.add(adoptApply);
			}
			return adoptApplyList;

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close(); // resource 獄쏆꼹 넎
		}
		return null;
	}

}
