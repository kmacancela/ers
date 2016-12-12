package com.ers.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ers.beans.ReimbursementType;

class ReimbursementTypeDAO {
	private Connection conn;

	public ReimbursementTypeDAO(Connection conn) {
		super();
		this.conn = conn;
	}
	
	/**
	 * Helper method used to return the rows in a list.
	 * @param rs
	 * @throws SQLException
	 */
	
	private void mapRows(ResultSet rs) throws SQLException {
		List<ReimbursementType> results = new ArrayList<ReimbursementType>();
		while(rs.next()){
			int typeId = rs.getInt("REIMB_TYPE_ID");
			String type = rs.getString("REIMB_TYPE");
			ReimbursementType obj = new ReimbursementType(typeId,
								  						  type);			  
			results.add(obj);
		}
		for(int i = 0; i < results.size(); i++) {
            System.out.println(results.get(i));
        }
	}

	/**
	 * Selects all the reimbursements depending on the type.
	 * We will define the typeId in the Facade in order to select
	 * lodging, travel, food, other reimbursements.
	 * @param typeId
	 * @throws SQLException
	 */
	
	public void selectByType(int typeId) throws SQLException{
		String sql = "SELECT * FROM ERS_REIMBURSEMENT " + 
					 "WHERE REIMB_TYPE_ID = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, typeId);
		ResultSet rs = stmt.executeQuery();
		mapRows(rs);
	}
	
	/**
	 * Returns a type Object when given the type ID.
	 * Used in order to put into Reimbursement bean.
	 * @param typeId
	 * @return
	 * @throws SQLException
	 */
	
	public ReimbursementType createTypeObj(int typeId) throws SQLException{
		String sql = "SELECT REIMB_TYPE FROM ERS_REIMBURSEMENT_TYPE " +
					 "WHERE REIMB_TYPE_ID = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, typeId);
		ResultSet rs = stmt.executeQuery();
		rs.next();
		String type = rs.getString("REIMB_TYPE");
		ReimbursementType typeObject = new ReimbursementType(typeId, type);
		return typeObject;
	}
}

