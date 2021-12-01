package com.developery.azure;

import com.microsoft.azure.storage.table.TableServiceEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GMS_VO extends TableServiceEntity {

	String work_dt;
	String oper_id;
	int mvnt_qty;
	int wip_qty;
	String rowNum;
	
	
	public String getWork_dt() {
		return work_dt;
	}
	public void setWork_dt(String work_dt) {
		this.work_dt = work_dt;
	}
	public String getOper_id() {
		return oper_id;
	}
	public void setOper_id(String oper_id) {
		this.oper_id = oper_id;
	}
	public int getMvnt_qty() {
		return mvnt_qty;
	}
	public void setMvnt_qty(int mvnt_qty) {
		this.mvnt_qty = mvnt_qty;
	}
	public int getWip_qty() {
		return wip_qty;
	}
	public void setWip_qty(int wip_qty) {
		this.wip_qty = wip_qty;
	}
	public String getRowNum() {
		return rowNum;
	}
	public void setRowNum(String rowNum) {
		this.rowNum = rowNum;
	}
}
