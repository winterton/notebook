package edu.bgsu.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;


@Path("/auto")
public class Autocomplete {

	@GET
	@Path("/complete")
	public String auto(@QueryParam("query") String query){
		return query;
	}
}
