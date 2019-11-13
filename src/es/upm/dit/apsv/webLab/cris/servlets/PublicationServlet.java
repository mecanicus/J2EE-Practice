package es.upm.dit.apsv.webLab.cris.servlets;

import java.io.IOException;
import java.util.List;

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
@WebServlet({"/PublicationServlet"})
public class PublicationServlet extends HttpServlet{
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
		String publicationID = req.getParameter("id"); //TODO averiguar el metodo
		  PublicationDAO pdao = PublicationDAOImplementation.getInstance();
		  ResearcherDAO rdao = ResearcherDAOImplementation.getInstance();
		  Publication publication = pdao.read(publicationID);
		  try {
		  List<String> authors = publication.getAuthors();
		  List<Researcher>  researchers = rdao.parseResearchers(authors);
		  req.getSession().setAttribute("authors",researchers);
		  }catch (Exception e) {
			// TODO: handle exception
		}
		  finally {
		  String firstAuthorID = publication.getFirstAuthor();
		  Researcher firstAuthor = rdao.read(firstAuthorID);
		  req.getSession().setAttribute("publication", publication);

		  req.getSession().setAttribute("firstAuthor",firstAuthor);
         getServletContext().getRequestDispatcher("/PublicationView.jsp")
         .forward(req, resp);
		  }
    }
}
