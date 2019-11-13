package es.upm.dit.apsv.webLab.cris.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import es.upm.dit.apsv.webLab.cris.dao.PublicationDAO;
import es.upm.dit.apsv.webLab.cris.dao.PublicationDAOImplementation;
import es.upm.dit.apsv.webLab.cris.dao.ResearcherDAO;
import es.upm.dit.apsv.webLab.cris.dao.ResearcherDAOImplementation;
import es.upm.dit.apsv.webLab.cris.model.Publication;
import es.upm.dit.apsv.webLab.cris.model.Researcher;



@WebServlet("/PopulatePublicationServlet")
@MultipartConfig
public class PopulatePublicationServlet extends HttpServlet {
	
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
    	Part filePart = req.getPart("file");
    	InputStream fileContent = filePart.getInputStream();
    	BufferedReader bReader = new BufferedReader(new InputStreamReader(fileContent, "UTF8"));
    	String line = bReader.readLine();
    

    	while (null != (line = bReader.readLine())) {
    	    String[] lSplit = line.split(",");
    	    PublicationDAO pdao = PublicationDAOImplementation.getInstance();
			Publication pCheck  = pdao.read(lSplit[0]);
			
			if(pCheck == null) {
	    	    Publication p = new Publication();
	    	    p.setId(lSplit[0]);
	    	    p.setTitle(lSplit[1]);
	    	    p.setEid(lSplit[2]);
	    	    p.setPublicationName(lSplit[3]);
	    	    p.setPublicationDate(lSplit[4]);
	    	    p.setFirstAuthor(lSplit[5]);
	    	    String[] splitAuthors = lSplit[6].split(";");

	    	    
			    ResearcherDAO rdao = ResearcherDAOImplementation.getInstance();
			    Researcher researcher = rdao.read(lSplit[5]);
			    if(researcher != null) {
		    	    List <String> listAuthors = new ArrayList<String>();
		    	    for(int i = 0; i < splitAuthors.length; i++) {
		    	    	listAuthors.add(splitAuthors[i]);
		    	    }
		    	    p.setAuthors(listAuthors);
		    	    pdao.create(p);
		    	    researcher.getPublications().add(lSplit[0]);
		    	    rdao.update(researcher);
			    	    
				 }
			}

    	}
        resp.sendRedirect(req.getContextPath() + "/ResearchersListServlet");
    }

}
