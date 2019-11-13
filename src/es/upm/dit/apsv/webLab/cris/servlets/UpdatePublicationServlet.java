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
@WebServlet("/UpdatePublicationServlet")
public class UpdatePublicationServlet extends HttpServlet {
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PublicationDAO pdao = PublicationDAOImplementation.getInstance();
		Publication publication = (Publication) req.getSession().getAttribute("publication");
		publication.setTitle(req.getParameter("title"));
		publication.setPublicationName(req.getParameter("name"));
		publication.setPublicationDate(req.getParameter("date"));
		publication.setEid(req.getParameter("eid"));
		pdao.update(publication);
		req.getSession().setAttribute("publication", publication);
        getServletContext().getRequestDispatcher("/PublicationView.jsp")
        .forward(req, resp);
	}
}
