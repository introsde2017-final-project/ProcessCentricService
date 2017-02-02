package introsde.processcentric.resources;


import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.ws.Holder;

import introsde.business.ws.Business;
import introsde.business.ws.BusinessService;
import introsde.localdatabase.soap.Person;
import introsde.storage.ws.Storage;
import introsde.storage.ws.StorageService;

@Stateless // only used if the the application is deployed in a Java EE container
@LocalBean // only used if the the application is deployed in a Java EE container
@Path("/person")
public class PersonResource {

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
    @Consumes({MediaType.APPLICATION_XML})
    public Response createIfNotExistsPersonWithChatId(Person person) {
        System.out.println("--> Creating Person... ");
        System.out.println("name " + person.getFirstname());

        initializeBusiness();
        Holder<Person> holder = new Holder<Person>(person);
        business.createPerson(holder);
        	
        if (holder.value == null) {
        	return Response.notModified().build();
        }
        return Response.ok().build(); 
    }

}
