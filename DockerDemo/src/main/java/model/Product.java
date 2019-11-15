package main.java.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@NotNull
	private int productId;
	@NotNull
	private String productName;
	@Override
	public String toString() {
		return "{productId=" + productId + ", productName=" + productName + "}";
	}
	
	
}
