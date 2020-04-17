package com.example.securitydemo.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

//import org.springframework.data.annotation.Transient;

@Entity
public class Journal {
	//@org.springframework.data.annotation.Id
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	//private String id;
	private Long id;
	private String title;
	private Date created;
	private String summary;
	
	@Transient
	private SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

	public Journal(String title, String summary,String date) throws ParseException {
		super();
		this.title = title;
		this.created = format.parse(date);
		this.summary = summary;
	}
	
	Journal(){}

//	public String getId() {
//		return id;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
    public String getCreatedAsShort() {
    	return format.format(created);
    }

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("* JournalEntry(");
		sb.append("id: ");
		sb.append(id);
		sb.append(", Title: ");
		sb.append(title);
		sb.append(", Summary: ");
		sb.append(summary);
		sb.append(", Created: ");
		sb.append(getCreatedAsShort());
		sb.append(")");
		return sb.toString();
	}
    
}
