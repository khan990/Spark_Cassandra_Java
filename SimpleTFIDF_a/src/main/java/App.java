
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

/**
 * Hello world!
 *
 */
public class App {
/*
	public static void main(String[] args) {

		
		System.out.println("Num of parameters: " + args.length);
		
		Cluster cluster;
		Session session;

		cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
		session = cluster.connect("wiki_db");

		File inputFile = new File(args[args.length - 1]);

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = null;

		Document doc = null;

		try {
			dBuilder = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			doc = dBuilder.parse(inputFile);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		doc.getDocumentElement().normalize();

		// System.out.println("Root element :" +
		// doc.getDocumentElement().getNodeName());
		NodeList nList = doc.getElementsByTagName("doc");

		System.out.println("----------------------------");
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				Integer id = Integer.parseInt(eElement.getAttribute("id"));
				String link = eElement.getAttribute("url");
				String title = eElement.getAttribute("title");
				String text = eElement.getTextContent();
				// System.out.print(text);
				System.out.println("Article being sent :" + title);

				// session.execute(" insert into wiki_data (wiki_id, article_id, article_link, article_title, article_text) values ("
				// + id + " ," + id + " ,'" + link + "' ,'" + title + "' ,'" +
				// text + "') ");
				session.execute(
						" insert into wiki_data_reduced (wiki_id, article_id, article_link, article_title, article_text) values (?, ?, ?, ?, ?)",
						id, id, link, title, text);
			}

		}
		System.out.println("execution done.");
		System.exit(0);
	}
*/
}
