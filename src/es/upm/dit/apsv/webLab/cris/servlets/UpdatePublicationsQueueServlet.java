/*package es.upm.dit.apsv.webLab.cris.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.cloud.pubsub.v1.stub.GrpcSubscriberStub;
import com.google.cloud.pubsub.v1.stub.SubscriberStub;
import com.google.cloud.pubsub.v1.stub.SubscriberStubSettings;
import com.google.pubsub.v1.AcknowledgeRequest;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.PullRequest;
import com.google.pubsub.v1.PullResponse;
import com.google.pubsub.v1.ReceivedMessage;

import es.upm.dit.apsv.webLab.cris.dao.PublicationDAO;
import es.upm.dit.apsv.webLab.cris.dao.PublicationDAOImplementation;
import es.upm.dit.apsv.webLab.cris.dao.ResearcherDAO;
import es.upm.dit.apsv.webLab.cris.dao.ResearcherDAOImplementation;
import es.upm.dit.apsv.webLab.cris.model.Publication;
import es.upm.dit.apsv.webLab.cris.model.Researcher;

@WebServlet("/UpdatePublicationsQueueServlet")
public class UpdatePublicationsQueueServlet extends HttpServlet{
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");
		ResearcherDAO rdao = ResearcherDAOImplementation.getInstance();
		PublicationDAO pdao = PublicationDAOImplementation.getInstance();
		Researcher researcher = rdao.read(id);
		String projectId = "cris-223316";
		String subscriptionId = "cris";
		SubscriberStubSettings subscriberStubSettings = SubscriberStubSettings.newBuilder().build();
		SubscriberStub subscriber = GrpcSubscriberStub.create(subscriberStubSettings);
		String subscriptionName = ProjectSubscriptionName.format(projectId, subscriptionId);
		PullRequest pullRequest = PullRequest.newBuilder()
		        .setMaxMessages(100)
		        .setReturnImmediately(true)
		        .setSubscription(subscriptionName)
		        .build();
		PullResponse pullResponse = subscriber.pullCallable().call(pullRequest);
		List<String> ackIds = new ArrayList<>();
	
		for (ReceivedMessage message : pullResponse.getReceivedMessagesList()) {
			try {
				JSONObject jsonPublication = (JSONObject) new JSONParser().parse(message.getMessage().getData().toStringUtf8());
				Publication publication = new Publication();
				System.out.println(jsonPublication);
				publication.setId((Long) jsonPublication.get("id")+"");
				publication.setFirstAuthor((String)jsonPublication.get("firstAuthor"));
				publication.setEid((String)jsonPublication.get("eid"));
				String authors = ((String) jsonPublication.get("authors"));
				publication.setAuthors(Arrays.asList(authors.split(";")));
				
			    if(researcher.getId().equals(publication.getFirstAuthor())){
					if (pdao.read(publication.getId()) ==null) {
						pdao.create(publication);	
						researcher.getPublications().add(id);
						rdao.update(researcher);
					}
			        ackIds.add(message.getAckId());
			    }
				if (id.equals(publication.getFirstAuthor())) {
					pdao.create(publication);
					researcher.getPublications().add(publication.getId());
					rdao.update(researcher);
					ackIds.add(message.getAckId());
				}
			}catch(Exception e) {
			
			}
		}
		if(!ackIds.isEmpty()) {
		    AcknowledgeRequest acknowledgeRequest = AcknowledgeRequest.newBuilder()
		            .setSubscription(subscriptionName)
		            .addAllAckIds(ackIds)
		            .build();
		    subscriber.acknowledgeCallable().call(acknowledgeRequest);
		}
		resp.sendRedirect(req.getContextPath()+"/ResearcherServlet?id="+id);
	}
}
*/
package es.upm.dit.apsv.webLab.cris.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.cloud.pubsub.v1.stub.GrpcSubscriberStub;
import com.google.cloud.pubsub.v1.stub.SubscriberStub;
import com.google.cloud.pubsub.v1.stub.SubscriberStubSettings;
import com.google.pubsub.v1.AcknowledgeRequest;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.PullRequest;
import com.google.pubsub.v1.PullResponse;
import com.google.pubsub.v1.ReceivedMessage;

import es.upm.dit.apsv.webLab.cris.dao.PublicationDAO;
import es.upm.dit.apsv.webLab.cris.dao.PublicationDAOImplementation;
import es.upm.dit.apsv.webLab.cris.dao.ResearcherDAO;
import es.upm.dit.apsv.webLab.cris.dao.ResearcherDAOImplementation;
import es.upm.dit.apsv.webLab.cris.model.Publication;
import es.upm.dit.apsv.webLab.cris.model.Researcher;

@WebServlet("/UpdatePublicationsQueueServlet")
public class UpdatePublicationsQueueServlet extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String id = req.getParameter("id");
		
		ResearcherDAO rdao = ResearcherDAOImplementation.getInstance();
		PublicationDAO pdao = PublicationDAOImplementation.getInstance();
		
		Researcher researcher = rdao.read(id);
		
		//Conectar con la cola
		
		String projectId = "cris-223316";
		String subscriptionId = "cris";
		SubscriberStubSettings subscriberStubSettings = SubscriberStubSettings.newBuilder().build();
		SubscriberStub subscriber = GrpcSubscriberStub.create(subscriberStubSettings);
		String subscriptionName = ProjectSubscriptionName.format(projectId, subscriptionId);
		PullRequest pullRequest = PullRequest.newBuilder()   //LLamada para pedir los mensakes
		        .setMaxMessages(100)
		        .setReturnImmediately(true)
		        .setSubscription(subscriptionName)
		        .build();
		
		//Iterar mensajes
		PullResponse pullResponse = subscriber.pullCallable().call(pullRequest);
		List<String> ackIds = new ArrayList<>();
		for (ReceivedMessage message : pullResponse.getReceivedMessagesList()) {
			try {
				JSONObject jsonPublication = (JSONObject) new JSONParser().parse(
				        message.getMessage().getData().toStringUtf8());
				Publication publication = new Publication();
				publication.setId((Long) jsonPublication.get("id")+""); //Para que sea string
				publication.setEid((String) jsonPublication.get("eid"));
				publication.setTitle((String) jsonPublication.get("title"));
				publication.setFirstAuthor((Long) jsonPublication.get("firstAuthor")+"");
				String authors = ((String) jsonPublication.get("authors"));
				publication.setAuthors(Arrays.asList(authors.split(";")));
				
				if (id.equals(publication.getFirstAuthor())) {
					pdao.create(publication);
					researcher.getPublications().add(publication.getId());
					rdao.update(researcher);
					ackIds.add(message.getAckId());
				}
			}catch (Exception e) {}
			
		}
		//Mandar acks
		if(!ackIds.isEmpty()) {
		    AcknowledgeRequest acknowledgeRequest = AcknowledgeRequest.newBuilder()
		            .setSubscription(subscriptionName)
		            .addAllAckIds(ackIds)
		            .build();
		    subscriber.acknowledgeCallable().call(acknowledgeRequest);
		}
		
		    
		
		
		resp.sendRedirect(req.getContextPath()+"/ResearcherServlet?id="+id);
		
	}

}