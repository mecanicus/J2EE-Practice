package es.upm.dit.apsv.webLab.cris.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.element.ListItem;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import es.upm.dit.apsv.webLab.cris.dao.PublicationDAO;
import es.upm.dit.apsv.webLab.cris.dao.PublicationDAOImplementation;
import es.upm.dit.apsv.webLab.cris.model.Publication;
import es.upm.dit.apsv.webLab.cris.model.Researcher;
@WebServlet({"/GenerateCVServlet"})
public class GenerateCVServlet extends HttpServlet {
	 protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		 ServletOutputStream sout = resp.getOutputStream();
		 ByteArrayOutputStream baos = new ByteArrayOutputStream();
		 
		 Researcher researcher = (Researcher) req.getSession().getAttribute("researcher");
		 PublicationDAO pdao = PublicationDAOImplementation.getInstance();	
		 //Create pdf object
		PdfDocument pdf = new PdfDocument(new PdfWriter(baos));
		Document document = new Document(pdf);
		//Add paragraphs
		Paragraph p = new Paragraph("Curriculum Vitae").setFontSize(20);
		document.add(p);
	
		//Add list with attributes
		List list = new List();
		ListItem item = new ListItem("Full name: " + researcher.getName()+ " " + researcher.getLastName());
		list.add(item);
		item = new ListItem("Email: "+ researcher.getEmail());
		list.add(item);
		item = new ListItem("Scopus Url: "+ researcher.getScopusUrl());
		list.add(item);
		document.add(list);
	
		//Add a table with publications
		//The table should be initialized with an array of floats indicating the relative width of each column
		Table table = new Table(new float[]{14, 4,4,4});
		table.addHeaderCell("Publication title");
		table.addHeaderCell("Citations");
		table.addHeaderCell("Publication name");
		table.addHeaderCell("Publication date");
		for(Publication pub : pdao.parsePublications(researcher.getPublications())){
		    table.addCell(pub.getTitle());
		    table.addCell(Integer.toString(pub.getCiteCount()));
		    table.addCell(pub.getPublicationName());
		    table.addCell(pub.getPublicationDate());
		}
		document.add(table);
	
		//Close the document
		document.close();
		pdf.close();
	
		//Write the file
		resp.setContentType("application/pdf");
		resp.setContentLength(baos.size());
		baos.writeTo(sout);
	 }
}
