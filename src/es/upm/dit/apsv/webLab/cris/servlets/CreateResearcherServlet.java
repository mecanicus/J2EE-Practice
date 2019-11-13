package es.upm.dit.apsv.webLab.cris.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.upm.dit.apsv.webLab.cris.dao.ResearcherDAO;
import es.upm.dit.apsv.webLab.cris.dao.ResearcherDAOImplementation;
import es.upm.dit.apsv.webLab.cris.model.Researcher;
@WebServlet({"/CreateResearcherServlet"})
public class CreateResearcherServlet extends HttpServlet{
	  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		  String uid= req.getParameter("uid");
		  String name= req.getParameter("name");
		  String last_name= req.getParameter("last_name");
		  String password = req.getParameter("password");
		  String email = req.getParameter("email");
		  String eid =req.getParameter("eid");
		  String scopusUrl = req.getParameter("scopusUrl");
		  
		  if (req.getSession().getAttribute("userAdmin").equals("true")) {
			  Researcher researcherToCreate = new Researcher();
			  researcherToCreate.setId(uid);
			  researcherToCreate.setEmail(email);
			  researcherToCreate.setName(name);
			  researcherToCreate.setLastName(last_name);
			  researcherToCreate.setPassword(password);
			  researcherToCreate.setEid(eid);
			  researcherToCreate.setScopusUrl(scopusUrl);
			  ResearcherDAO rdao = ResearcherDAOImplementation.getInstance();
			  rdao.create(researcherToCreate);
		  }
  		  resp.sendRedirect(req.getContextPath() + "/AdminServlet");
	  }
}
