package introsde.processcentric.resources;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import introsde.adapter.ws.Exercise;
import introsde.business.ws.Business;
import introsde.business.ws.BusinessService;
import introsde.storage.ws.Storage;
import introsde.storage.ws.StorageService;


@Stateless // only used if the the application is deployed in a Java EE container
@LocalBean // only used if the the application is deployed in a Java EE container
@Path("/exercise")
public class ExerciseResource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    
	StorageService sService;
	Storage storage;
	BusinessService bService;
	Business business;
	
	
	public void initializeStorage(){
		sService = new StorageService();
		storage = sService.getStorageImplPort();
	}
	
	public void initializeBusiness(){
		bService = new BusinessService();
		business = bService.getBusinessImplPort();
	}
	
	@POST
	@Consumes({ MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_XML })
    @Path("{chatId}/calories")
	public Response getCalories(@PathParam("chatId") Long chatId, Exercise exercise) {
		System.out.println("--> Get calories... ");
		System.out.println("exercise " + exercise.getName() + " for " + exercise.getMinutes() + " minutes");

		initializeBusiness();
		
		double ris = business.getCalories(chatId, exercise);
		System.out.println(ris);
		if (ris < 0) {
			return Response.serverError().build();
		}
		return Response.ok().build();
	}

}
