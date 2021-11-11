package com.productsup.api;

public enum APIResources 
{
	

	
	//End point resources

	createUsers("api/users"),
	listUsers("api/users?page=2");
	

	private String resource;

	APIResources(String resource) 
	{
		this.resource = resource;
	}

	public String getResource() {
		return resource;
	}

}
