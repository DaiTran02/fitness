package com.fit.ws.resource.enums;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

public enum MuscleType {
	ABDOMINALS("abdominals","Bụng"),
	SHOULDERS("shoulders","Vai"),
	BICEPS("biceps","Bắp tay"),
	TRICEPS("triceps","Cơ tam đầu"),
	FOREARMS("forearms","Cẳng tay"),
	QUADRICEPS("quadriceps","Cơ tứ đầu");
	
	private String title;
	private String dsr;
	
	MuscleType(String title,String dsr) {
		this.title = title;
		this.dsr = dsr;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDsr() {
		return dsr;
	}

	public void setDsr(String dsr) {
		this.dsr = dsr;
	}
	
	public static List<Pair<String, String>> getListTypes(){
		List<Pair<String, String>> listItems = new ArrayList<Pair<String,String>>();
		for(MuscleType type : MuscleType.values()) {
			listItems.add(Pair.of(type.getTitle(),type.getDsr()));
		}
		return listItems;
	}
	
	public static MuscleType findByKey(String key) {
		for(MuscleType type : MuscleType.values()) {
			if(type.getTitle().equals(key)) {
				return type;
			}
		}
		return null;
	}
	
	
}
