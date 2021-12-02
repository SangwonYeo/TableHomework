package com.developery.azure;

import com.microsoft.azure.storage.table.TableServiceEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GmsVO extends TableServiceEntity {

	String sysID;
	String userID;
	String userNM;
	String state;
	
	
	public String getSysID() {
		return sysID;
	}
	public void setSysID(String sysID) {
		this.sysID = sysID;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getUserNM() {
		return userNM;
	}
	public void setUserNM(String userNM) {
		this.userNM = userNM;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

}
