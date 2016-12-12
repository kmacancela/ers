package com.ers.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ers.beans.ReimbursementStatus;

class ReimbursementStatusDAO {
	
	private Connection conn;

	public ReimbursementStatusDAO(Connection conn) {
		super();
		this.conn = conn;
	}
	
	/**
	 * Returns the status as a String given the status ID.
	 * @param statusId
	 * @return
	 * @throws SQLException
	 */
	
	public String statusNameById(int statusId) throws SQLException{
		String result = null;
		String sql = "SELECT REIMB_STATUS FROM ERS_REIMBURSEMENT_STATUS " + 
					 "WHERE REIMB_STATUS_ID = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, statusId);
		ResultSet rs = stmt.executeQuery();
		rs.next();
		if(rs.getString("REIMB_STATUS").equals("Pending")){
			result = "Pending";
			return result;
		} else if(rs.getString("REIMB_STATUS").equals("Approved")){
			result = "Approved";
			return result;
		}
		result = "Denied";
		return result;	
	}
	
	/**
	 * Returns a status Object given a status ID.
	 * Used in order to put a status Object into Reimbursement bean.
	 * @param statusId
	 * @return
	 * @throws SQLException
	 */
	
	public ReimbursementStatus createStatusObj(int statusId) throws SQLException{
		String sql = "SELECT REIMB_STATUS FROM ERS_REIMBURSEMENT_STATUS " + 
					 "WHERE REIMB_STATUS_ID = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, statusId);
		ResultSet rs = stmt.executeQuery();
		rs.next();
		String status = rs.getString("REIMB_STATUS");
		ReimbursementStatus statusObj = new ReimbursementStatus(statusId, status);
		return statusObj;
	}

}