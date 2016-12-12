package com.ers.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import com.ers.beans.Reimbursement;
import com.ers.beans.ReimbursementStatus;
import com.ers.beans.ReimbursementType;
import com.ers.beans.Users;

/**
 * Opens a connection.
 * @author kawee
 *
 */

class ReimbursementDAO {
	private Connection conn;

	public ReimbursementDAO(Connection conn) {
		super();
		this.conn = conn;
	}
	
	/**
	 * Retrieves the ID of the reimbursement to be inserted.
	 * @return
	 * @throws SQLException
	 */
	
	public int getNextId() throws SQLException{
		String sqlForMaxId = "SELECT MAX(REIMB_ID) FROM ERS_REIMBURSEMENT";
		PreparedStatement maxId = conn.prepareStatement(sqlForMaxId);
		ResultSet rs = maxId.executeQuery();
		rs.next();
		int theId = rs.getInt("MAX(REIMB_ID)") + 1;
		return theId;
	}
	
	/**
	 * Inserts a new reimbursement into our database.
	 * No resolved date, no resolver, and the status is pending.
	 * Called by createReimbObject in Facade class.
	 * @param reimb
	 * @throws SQLException
	 */

	public void insert(Reimbursement reimb) throws SQLException{
		String sql = "INSERT INTO ERS_REIMBURSEMENT " + 
					 "(REIMB_ID, REIMB_AMOUNT, REIMB_SUBMITTED, REIMB_DESCRIPTION, REIMB_AUTHOR, REIMB_STATUS_ID, REIMB_TYPE_ID) " + 
					 "VALUES (?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, reimb.getId());
		stmt.setDouble(2, reimb.getAmount());
		stmt.setTimestamp(3, reimb.getSubmitted()); 
		stmt.setString(4, reimb.getDescription());
		stmt.setInt(5, reimb.getAuthor().getUsersId());
		stmt.setInt(6, reimb.getStatus().getStatusId()); //status is pending when created...pending is id#1
		stmt.setInt(7, reimb.getType().getTypeId());
		conn.commit();
		stmt.executeUpdate();
		System.out.println("Successfully inserted row!");
		System.out.println(reimb.toString());
	}
	
	/**
	 * Updates an reimbursement.
	 * Used by employees.
	 * @param status
	 * @param username
	 * @param reimbId
	 * @param currentDate
	 * @throws SQLException
	 */
	
	public void updateReimbursement
		(String status, String username, int reimbId, Timestamp currentDate) throws SQLException{
		String sql = 
				"UPDATE ERS_REIMBURSEMENT " +
				"SET REIMB_RESOLVED = ?, " +
				"REIMB_STATUS_ID = " +
				"(SELECT REIMB_STATUS_ID FROM ERS_REIMBURSEMENT_STATUS WHERE REIMB_STATUS = ?), " +
				"REIMB_RESOLVER = " +
				"(SELECT ERS_USERS_ID FROM ERS_USERS WHERE ERS_USERNAME = ?) " +
				"WHERE REIMB_ID = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setTimestamp(1, currentDate);
		stmt.setString(2, status);
		stmt.setString(3, username);
		stmt.setInt(4, reimbId);
		stmt.executeUpdate();
		
		System.out.println("Successfully updated reimbursement#" + reimbId + " as " + status +
				". Resolved by " + username + " on " + currentDate.toString() + "."); 
	}

	/**
	 * Selects all the reimbursements of a user.
	 * @param username
	 * @throws SQLException
	 */
	
	public List<Reimbursement> selectAllByUsername(String username) throws SQLException {
		List<Reimbursement> results = new ArrayList<Reimbursement>();
		String sql = "SELECT * FROM ERS_REIMBURSEMENT " +
					 "WHERE REIMB_AUTHOR = " +
					 "(SELECT ERS_USERS_ID FROM ERS_USERS WHERE ERS_USERNAME = ?)";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, username);
		ResultSet rs = stmt.executeQuery();
		mapRows(rs, results);
		if(results.size() == 0) System.out.println("Could not find the user " + username + ".");
		return results;
	}
	
	/**
	 * Selects all the reimbursement of a user by status.
	 * Used for employees.
	 * @param status
	 * @param username
	 * @throws SQLException
	 */
	
	public void showAllByStatusOfUser(String status, String username) throws SQLException{
		List<Reimbursement> results = new ArrayList<Reimbursement>();
		String sql = "SELECT * FROM ERS_REIMBURSEMENT " +
					 "WHERE REIMB_STATUS_ID = " +
					 "(SELECT REIMB_STATUS_ID FROM ERS_REIMBURSEMENT_STATUS WHERE REIMB_STATUS = ?) " +
					 "AND REIMB_AUTHOR = " +
					 "(SELECT ERS_USERS_ID FROM ERS_USERS WHERE ERS_USERNAME = ?)";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, status);
		stmt.setString(2, username);
		ResultSet rs = stmt.executeQuery();
		mapRows(rs, results);
		//conn.close();
	}
	
	/**
	 * Selects reimbursements by the status given.
	 * Used for managers.
	 * @param status
	 * @throws SQLException
	 */
	
	public void selectByStatus(String status) throws SQLException{
		List<Reimbursement> results = new ArrayList<Reimbursement>();
		String sql = "SELECT * FROM ERS_REIMBURSEMENT " +
					 "WHERE REIMB_STATUS_ID = " +
					 "(SELECT REIMB_STATUS_ID FROM ERS_REIMBURSEMENT_STATUS WHERE REIMB_STATUS = ?)";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, status);
		ResultSet rs = stmt.executeQuery();
		mapRows(rs, results);
	}
	
	/**
	 * Assists us in returning all the rows in a query.
	 * @param rs
	 * @throws SQLException
	 */
	
	private void mapRows(ResultSet rs, List<Reimbursement> results) throws SQLException {
		while(rs.next()){
			int id = rs.getInt("REIMB_ID");
			double amount = rs.getDouble("REIMB_AMOUNT");
			Timestamp submitted = rs.getTimestamp("REIMB_SUBMITTED");
			Timestamp resolved = rs.getTimestamp("REIMB_RESOLVED");
			String description = rs.getString("REIMB_DESCRIPTION");
			
			int authorId = rs.getInt("REIMB_AUTHOR");
			UsersDAO authorDAO = new UsersDAO(conn);
			Users author = authorDAO.createUserObj(authorId);
			
			int resolverId = rs.getInt("REIMB_RESOLVER");
			Users resolver = null;
			if(resolverId != 0){
				UsersDAO resolverDAO = new UsersDAO(conn);
				resolver = resolverDAO.createUserObj(resolverId);
			}
			
			int statusId = rs.getInt("REIMB_STATUS_ID");
			ReimbursementStatusDAO statusDAO = new ReimbursementStatusDAO(conn);
			ReimbursementStatus status = statusDAO.createStatusObj(statusId);
			
			int typeId = rs.getInt("REIMB_TYPE_ID");
			ReimbursementTypeDAO typeDAO = new ReimbursementTypeDAO(conn);
			ReimbursementType type = typeDAO.createTypeObj(typeId);
			
			Reimbursement obj = new Reimbursement(id,
												  amount,
												  submitted,
												  resolved,
												  description,
												  author,
												  resolver,
												  status,
												  type);
			results.add(obj);
		}
		/*for(int i = 0; i < results.size(); i++) {
            System.out.println(results.get(i));
        }*/
	}
	
	/**
	 * Closes connection.
	 * @throws SQLException
	 */
	
	public void close() throws SQLException{
		conn.close();
	}
}
