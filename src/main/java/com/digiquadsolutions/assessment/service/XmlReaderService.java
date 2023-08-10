package com.digiquadsolutions.assessment.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

@Service
public class XmlReaderService {
	String filePath = "C:\\Users\\tapan\\OneDrive\\Desktop\\New folder\\Student.xml";
	Document doc;

	public void parsXml(InputStream is, String tagname, String attributename, String attributevalue) {
		// Instantiate the Factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try (is) {
			DocumentBuilder db = dbf.newDocumentBuilder();
			doc = db.parse(is);

			System.out.println("Root Element :" + doc.getDocumentElement().getNodeName());
			System.out.println("------");

			if (doc.hasChildNodes()) {

				printNote(doc.getChildNodes(), tagname, attributename, attributevalue);
			}
			if (!(tagname.equals("") && attributename.equals("") && attributevalue.equals(""))) {
				updateXml();
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}

	}

	private static void printNote(NodeList nodeList, String tagname, String attributename, String attributevalue) {

		for (int count = 0; count < nodeList.getLength(); count++) {

			Node tempNode = nodeList.item(count);

			// make sure it's element node.
			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
				// get node name and value
				if (!(tagname.equals("") && attributename.equals("") && attributevalue.equals(""))) {
					if (tempNode.getNodeName().equals(tagname)) {

						((Element) tempNode).setAttribute(attributename, attributevalue);
					}
				}
				System.out.println("\nNode Name =" + tempNode.getNodeName() + " [OPEN]");
				System.out.println("Node Value =" + tempNode.getTextContent());

				if (tempNode.hasAttributes()) {
					// get attributes names and values
					NamedNodeMap nodeMap = tempNode.getAttributes();
					for (int i = 0; i < nodeMap.getLength(); i++) {

						Node node = nodeMap.item(i);
						System.out.println("attr name : " + node.getNodeName());
						System.out.println("attr value : " + node.getNodeValue());
					}

				}

				if (tempNode.hasChildNodes()) {
					// loop again if has child nodes
					printNote(tempNode.getChildNodes(), tagname, attributename, attributevalue);
				}

				// System.out.println("Node Name =" + tempNode.getNodeName() + " [CLOSE]");

			}

		}

	}

	public void updateXml() {
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filePath));
			transformer.transform(source, result);
			// For console Output.
			StreamResult consoleResult = new StreamResult(System.out);
			transformer.transform(source, consoleResult);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

// read file from project resource's folder.
	/*
	 * private static InputStream readXmlFileIntoInputStream(final String fileName)
	 * { return
	 * ReadXmlDomParserLoop.class.getClassLoader().getResourceAsStream(fileName); }
	 */
}
