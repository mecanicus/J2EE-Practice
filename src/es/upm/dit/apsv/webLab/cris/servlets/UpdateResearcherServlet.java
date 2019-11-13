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
@WebServlet({"/UpdateResearcherServlet"})
public class UpdateResearcherServlet extends HttpServlet{
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ResearcherDAO rdao = ResearcherDAOImplementation.getInstance();
		Researcher researcher = (Researcher) req.getSession().getAttribute("researcher");
		researcher.setName(req.getParameter("name"));
		researcher.setEmail(req.getParameter("email"));
		researcher.setLastName(req.getParameter("last_Name"));
		researcher.setScopusUrl(req.getParameter("scopusUrl"));
		researcher.setEid(req.getParameter("eid"));
		rdao.update(researcher);
		req.getSession().setAttribute("researcher", researcher);
        getServletContext().getRequestDispatcher("/ResearcherView.jsp")
        .forward(req, resp);
	}
}
