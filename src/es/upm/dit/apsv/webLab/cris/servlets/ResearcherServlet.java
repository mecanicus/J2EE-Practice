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

@WebServlet({"/ResearcherServlet"})
public class ResearcherServlet extends HttpServlet{
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String researcherID = req.getParameter("id"); //TODO averiguar el metodo
				  ResearcherDAO rdao = ResearcherDAOImplementation.getInstance();
				  Researcher researcher = rdao.read(researcherID);
				  List<String>  listOfId = researcher.getPublications();
				  PublicationDAO pdao = PublicationDAOImplementation.getInstance();
				  List<Publication>  publications = pdao.parsePublications(listOfId);
				  
				  req.getSession().setAttribute("researcher", researcher);
				  req.getSession().setAttribute("publications",publications );
	               getServletContext().getRequestDispatcher("/ResearcherView.jsp")
                   .forward(req, resp);
	}
}
