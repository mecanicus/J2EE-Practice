package es.upm.dit.apsv.webLab.cris.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import es.upm.dit.apsv.webLab.cris.dao.PublicationDAOImplementation;
import es.upm.dit.apsv.webLab.cris.dao.ResearcherDAO;
import es.upm.dit.apsv.webLab.cris.dao.ResearcherDAOImplementation;
import es.upm.dit.apsv.webLab.cris.model.Publication;
import es.upm.dit.apsv.webLab.cris.model.Researcher;
@WebServlet("/UpdateCitationsAPIServlet")
public class UpdateCitationsAPIServlet extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");
		Publication publication = PublicationDAOImplementation.getInstance().read(id);
		String url = "https://api.elsevier.com/content/abstract/scopus_id/"+id+"?apiKey=4c4bd1d8f12a1b1d9359d0bc2f93d53c";
		
		JSONObject firstLevel = getAPI(url);
		JSONObject secondLevel = (JSONObject) firstLevel.get("abstracts-retrieval-response");
		JSONObject thirdLevel = (JSONObject) secondLevel.get("coredata");
		int citedBy = Integer.parseInt((String) thirdLevel.get("citedby-count"));
		publication.setCiteCount(citedBy);
		PublicationDAOImplementation.getInstance().update(publication);
		resp.sendRedirect(req.getContextPath()+"/PublicationServlet?id="+id);
		
	}
		private JSONObject getAPI(String targetUrl) {
		    JSONObject object = null;
		    try {
		        URL url = new URL(targetUrl);
		        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		        connection.setDoOutput(true);
		        connection.setInstanceFollowRedirects(false);
		        connection.setRequestMethod("GET");
		        connection.setRequestProperty("Content-Type", "application/json");
		        connection.setRequestProperty("Accept", "application/json");
		        int responseCode = connection.getResponseCode();
		        if(responseCode>=200 && responseCode<300) {
		            InputStream is = connection.getInputStream();
		            InputStreamReader isr = new InputStreamReader(is);
		            object =(JSONObject) new JSONParser().parse(isr);
		            is.close();
		        } else {
		            System.err.println("Request returned code "+ responseCode);
		            System.err.println(connection.getResponseMessage());
		        }
		        connection.getResponseCode();
		        connection.disconnect();
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    return object;
		}
		  
	}

