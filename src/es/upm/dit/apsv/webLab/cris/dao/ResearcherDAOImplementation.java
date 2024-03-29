package es.upm.dit.apsv.webLab.cris.dao;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

import es.upm.dit.apsv.webLab.cris.model.Researcher;

public class ResearcherDAOImplementation implements ResearcherDAO{

	private static ResearcherDAOImplementation instance;

	private ResearcherDAOImplementation() {
	}

	public static ResearcherDAOImplementation getInstance() {
		if (null == instance) {
			instance = new ResearcherDAOImplementation();
		}
		return instance;
	}
	@Override
	public Researcher create(Researcher researcher) {
		ofy().save().entity(researcher).now();
		return researcher;
	}

	@Override
	public Researcher read(String researcherId) {
		return ofy().load().type(Researcher.class).id(researcherId).now();
	}

	@Override
	public Researcher update(Researcher researcher) {
		ofy().save().entity(researcher).now();
		return researcher;
	}

	@Override
	public Researcher delete(Researcher researcher) {
		ofy().delete().entity(researcher).now();
		return researcher;
	}

	@Override
	public List<Researcher> readAll() {
		return ofy().load().type(Researcher.class).list();
	}

	@Override
	public List<Researcher> parseResearchers(Collection<String> ids) {
		ArrayList<Researcher> list = new ArrayList<>();
		list.addAll(ofy().load().type(Researcher.class).ids(ids).values());
		return list;
	}

	@Override
	public Researcher readAsUser(String email, String password) {
		return ofy().load().type(Researcher.class).filter("email", email).first().now();
	}

}
