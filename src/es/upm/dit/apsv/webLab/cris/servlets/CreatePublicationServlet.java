package es.upm.dit.apsv.webLab.cris.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.upm.dit.apsv.webLab.cris.dao.PublicationDAO;
import es.upm.dit.apsv.webLab.cris.dao.PublicationDAOImplementation;
import es.upm.dit.apsv.webLab.cris.dao.ResearcherDAO;
import es.upm.dit.apsv.webLab.cris.dao.ResearcherDAOImplementation;
import es.upm.dit.apsv.webLab.cris.model.Publication;
import es.upm.dit.apsv.webLab.cris.model.Researcher;
@WebServlet({"/CreatePublicationServlet"})
public class CreatePublicationServlet extends HttpServlet{
	  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		  String title= req.getParameter("title");
		  String name= req.getParameter("name");
		  String date= req.getParameter("date");
		  String eid = req.getParameter("eid");
		  String id = req.getParameter("id");

		  Researcher researcher = (Researcher) req.getSession().getAttribute("researcher");
		  String firstAuthor = researcher.getName();
		  PublicationDAO pdao = PublicationDAOImplementation.getInstance();
		  ResearcherDAO rdao = ResearcherDAOImplementation.getInstance();

			  Publication publication = new Publication();
			  publication.setFirstAuthor(firstAuthor);
			  publication.setId(id);
			  publication.setTitle(title);
			  publication.setPublicationName(name);
			  publication.setPublicationDate(date);
			  publication.setEid(eid);
			  if (pdao.read(id)== null) {
				  pdao.create(publication);
				  researcher.getPublications().add(id);
				  rdao.update(researcher);
				  resp.sendRedirect(req.getContextPath() 
                      + "/PublicationServlet" + "?id=" + publication.getId());
		  
			  }

	}
}
