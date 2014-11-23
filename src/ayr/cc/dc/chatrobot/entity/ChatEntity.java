package ayr.cc.dc.chatrobot.entity;

import java.util.Date;

public class ChatEntity {
	private Type type;
	private Date date;
	private String message;

	public enum Type {
		INFORECEIVE, INFOSEND
	}

	public ChatEntity() {

	}

	public ChatEntity(Type type, Date date, String message) {
		this.type = type;
		this.date = date;
		this.message = message;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
