package es.upm.dit.apsv.webLab.cris.servlets;

import java.io.IOException;
import java.util.List;

import javax.naming.Context;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.upm.dit.apsv.webLab.cris.dao.ResearcherDAO;
import es.upm.dit.apsv.webLab.cris.dao.ResearcherDAOImplementation;
import es.upm.dit.apsv.webLab.cris.model.Researcher;

@WebServlet({"/ResearchersListServlet"})
public class ResearchersListServlet extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List <Researcher> researchers;  
		ResearcherDAO rdao = ResearcherDAOImplementation.getInstance();
		researchers = rdao.readAll();
		req.getSession().setAttribute("researcherList", researchers);
        getServletContext().getRequestDispatcher("/ResearchersListView.jsp")
        .forward(req, resp);
		  
	}
	
}
