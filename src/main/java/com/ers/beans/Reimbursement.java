package com.ers.beans;

import java.sql.Timestamp;

public class Reimbursement {
	private int id;
	private double amount;
	private Timestamp submitted;
	private Timestamp resolved;
	private String description;
	private Users author;
	private Users resolver;
	private ReimbursementStatus status;
	private ReimbursementType type;
	
	public Reimbursement() {
		super();
	}
	public Reimbursement(int id, 
						 double amount, 
						 Timestamp submitted, 
						 Timestamp resolved, 
						 String description, 
						 Users author,
						 Users resolver, 
						 ReimbursementStatus status, 
						 ReimbursementType type) {
		super();
		this.id = id;
		this.amount = amount;
		this.submitted = submitted;
		this.resolved = resolved;
		this.description = description;
		this.author = author;
		this.resolver = resolver;
		this.status = status;
		this.type = type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Timestamp getSubmitted() {
		return submitted;
	}
	public void setSubmitted(Timestamp submitted) {
		this.submitted = submitted;
	}
	public Timestamp getResolved() {
		return resolved;
	}
	public void setResolved(Timestamp resolved) {
		this.resolved = resolved;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Users getAuthor() {
		return author;
	}
	public void setAuthor(Users author) {
		this.author = author;
		
	}
	public Users getResolver() {
		return resolver;
	}
	public void setResolver(Users resolver) {
		this.resolver = resolver;
	}
	public ReimbursementStatus getStatus() {
		return status;
	}
	public void setStatus(ReimbursementStatus status) {
		//statusObj.setStatusId(statusId);
		this.status = status;
	}
	public ReimbursementType getType() {
		return type;
	}
	public void setType(ReimbursementType type) {
		//typeObj.setTypeId(typeId);
		this.type = type;
	}
	@Override
	public String toString() {
		return "ID: " + id + "\nAmount: $" + amount + "\nSubmitted on: " + submitted.toLocaleString() + "\nResolved on: "
		+ resolved + "\nDescription: " + description + "\nSubmitted by: " + author.fullName() + "\nResolved by: " + resolver
		+ "\nStatus: " + status.getStatus() + "\nType: " + type.getType();
	}
}
