package es.upm.dit.apsv.webLab.cris.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import es.upm.dit.apsv.webLab.cris.dao.ResearcherDAO;
import es.upm.dit.apsv.webLab.cris.dao.ResearcherDAOImplementation;
import es.upm.dit.apsv.webLab.cris.model.Researcher;

/**
 * Servlet implementation class PopulateResearcherServlet
 */
@WebServlet("/PopulateResearcherServlet")
@MultipartConfig
public class PopulateResearcherServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
    	Part filePart = req.getPart("file");
    	InputStream fileContent = filePart.getInputStream();
    	BufferedReader bReader = new BufferedReader(new InputStreamReader(fileContent, "UTF8"));
    	String line = bReader.readLine();

    	while (null != (line = bReader.readLine())) {

    	    String[] lSplit = line.split(",");
		    ResearcherDAO rdao = ResearcherDAOImplementation.getInstance();
		    Researcher researcher = rdao.read(lSplit[0]);
		    if(researcher == null) {
	    	    Researcher r = new Researcher();
	    	    r.setId(lSplit[0]);
	    	    r.setName(lSplit[1]);
	    	    r.setLastName(lSplit[2]);
	    	    r.setScopusUrl(lSplit[3]);
	    	    r.setEid(lSplit[4]);
	    	    rdao.create(r);
		    }
    	}
        resp.sendRedirect(req.getContextPath() + "/ResearchersListServlet");
    }

}
