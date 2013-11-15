package com.devbus.controller;

import java.util.ArrayList;
import java.util.List;

import com.devbus.data.Tip;

public class Tips {

	List<Tip> tips;

	public Tips() {
		super();
	}

	public void fillList() {
		this.tips = new ArrayList<Tip>();
		int i = 0;		
		this.tips.add(new Tip("Mamila", "Description", "Advice",5));
		this.tips.add(new Tip("Ombligo", "Description", "Advice",4));
		this.tips.add(new Tip("Pañal", "Description", "Advice",3));
		this.tips.add(new Tip("Erupto", "Description", "Advice",2));
		this.tips.add(new Tip("Amamantar", "Hasta 6 meses", "Advice",1));
		this.tips.add(new Tip("Nacimiento", "Nació el bebe!", "Advice",0));		
	}

	public List<Tip> getTips() {
		return tips;
	}

}
