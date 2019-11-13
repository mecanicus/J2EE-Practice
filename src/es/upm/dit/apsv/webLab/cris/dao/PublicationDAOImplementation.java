package es.upm.dit.apsv.webLab.cris.dao;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


import es.upm.dit.apsv.webLab.cris.model.Publication;

public class PublicationDAOImplementation implements PublicationDAO {
	private static PublicationDAOImplementation instance = null;
	private PublicationDAOImplementation() {}
	public static PublicationDAOImplementation getInstance() {
	    if( null == instance ) {
	        instance = new PublicationDAOImplementation();
	    }
	    return instance;
}
	@Override
	public Publication create(Publication publication) {
		ofy().save().entity(publication).now();
        return publication;
	}
	@Override
	public Publication read(String publicationId) {
		Publication p =ofy().load().type(Publication.class).id(publicationId).now();
		return p;
	}
	@Override
	public Publication update(Publication publication) {
		ofy().save().entity(publication).now();
        return publication;
	}
	@Override
	public Publication delete(Publication publication) {
		ofy().delete().entity(publication).now();
	return publication;
	}
	@Override
	public List<Publication> readAll() {
		List<Publication> publications = ofy().load().type(Publication.class).list();
		return publications;
	}
	@Override
	public List<Publication> parsePublications(Collection<String> ids) {
		List<Publication> publications = new ArrayList<>();
		publications.addAll(ofy().load().type(Publication.class).ids(ids).values());
		return publications;
	}
	
}
