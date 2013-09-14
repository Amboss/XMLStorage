package XMLStorage.logic.service.impl;

import XMLStorage.logic.service.CDService;
import XMLStorage.model.CDModel;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Class handel's CDModel functionality
 */
@Service("CDServiceImpl")
public class CDServiceImpl implements CDService {

    private Resource myData = new ClassPathResource("/storage/target.xml");

    /**
     * Method to save Uploaded XML file
     *
     * @param inputStream must contain stream of XML type with XMLStorage.model.CDModel format
     */
    public void saveUploadedXmlFile(InputStream inputStream) {

        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();

            // building stored Node List by CD tag
            File xmlFile = myData.getFile();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document storedDoc = docBuilder.parse(xmlFile);
            NodeList storedNodeList = storedDoc.getElementsByTagName("CD");

            // building upload Node List by CD tag
            Document uploadedDoc = docBuilder.parse(inputStream);
            NodeList uploadMainNodeList = uploadedDoc.getElementsByTagName("CD");

            // iterating upload main Node List
            for (int i = 0; i < uploadMainNodeList.getLength(); i++) {

                NodeList listOfCurrentCD = uploadMainNodeList.item(i).getChildNodes();
                CDModel cdModel = parseToModel(listOfCurrentCD);

                // searching for similarity of title value
                String xPathString = "./CD[./TITLE = '" + cdModel.getTitle() + "']";
                Node targetNode = findNode(storedNodeList, xPathString);

                /* If similarity exists, then replacing with new content.
                   If now, then insert new "CD" node at the end of stored file. */
                if (targetNode != null) {
                    // replacing
                } else {
                    // adding new node
                }

            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param obj must contain target NodeList
     * @param xPathString must contain path with searched value of "TITLE" tag
     * @return list of target child nodes
     */
    private Node findNode(Object obj, String xPathString) {
        Node node = null;
        try {
            XPath xPath = XPathFactory.newInstance().newXPath();
            node = (Node) xPath.compile(xPathString).evaluate(obj, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return node;
    }

    /**
     * Method to parse CDModel from nodeList
     * @param nodeList must contain CDModel xml nodeList
     * @return CDModel
     */
    private CDModel parseToModel(NodeList nodeList) {
        assert nodeList != null;

        CDModel cdModel = new CDModel();
        for (int j = 0; j < nodeList.getLength(); j++) {
            if (nodeList.item(j).getNodeName().equals("TITLE")) {
                cdModel.setTitle(nodeList.item(j).getNodeValue());
            } else if (nodeList.item(j).getNodeName().equals("ARTIST")) {
                cdModel.setArtist(nodeList.item(j).getNodeValue());
            } else if (nodeList.item(j).getNodeName().equals("COUNTRY")) {
                cdModel.setCountry(nodeList.item(j).getNodeValue());
            } else if (nodeList.item(j).getNodeName().equals("COMPANY")) {
                cdModel.setCompany(nodeList.item(j).getNodeValue());
            } else if (nodeList.item(j).getNodeName().equals("PRICE")) {
                cdModel.setPrice(Double.parseDouble(nodeList.item(j).getNodeValue()));
            } else if (nodeList.item(j).getNodeName().equals("YEAR")) {
                cdModel.setYear(Integer.parseInt(nodeList.item(j).getNodeValue()));
            }
        }
        return cdModel;
    }

    /**
     * Method to parse CDModel List from InputStream
     *
     * @return ArrayList<CDModel>
     */
    @SuppressWarnings("unchecked, null")
    public ArrayList<CDModel> getStoredList() {

        ArrayList<CDModel> cdModelList = new ArrayList<>();
        CDModel cdModel = null;

        //create xml reader event with input stream
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();

        try {

            // generating reader of existing file
            File xmlFile = myData.getFile();
            FileInputStream inputStream = new FileInputStream(xmlFile);
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(inputStream);

            while (xmlEventReader.hasNext()) {

                // xml reader event to get event and determine start element and end element
                XMLEvent xmlEvent = xmlEventReader.nextEvent();

                if (xmlEvent.isStartElement()) {

                    // creating a new item
                    StartElement startElement = xmlEvent.asStartElement();
                    String startElementName = startElement.getName().getLocalPart().toLowerCase();

                    // reading variables from xml elements
                    if (startElementName.equals("cd")) {
                        cdModel = new CDModel();
                        xmlEvent = xmlEventReader.nextEvent();
                    } else if (startElementName.equals("title")) {
                        xmlEvent = xmlEventReader.nextEvent();
                        cdModel.setTitle(xmlEvent.asCharacters().getData());
                    } else if (startElementName.equals("artist")) {
                        xmlEvent = xmlEventReader.nextEvent();
                        cdModel.setArtist(xmlEvent.asCharacters().getData());
                    } else if (startElementName.equals("country")) {
                        xmlEvent = xmlEventReader.nextEvent();
                        cdModel.setCountry(xmlEvent.asCharacters().getData());
                    } else if (startElementName.equals("company")) {
                        xmlEvent = xmlEventReader.nextEvent();
                        cdModel.setCompany(xmlEvent.asCharacters().getData());
                    } else if (startElementName.equals("price")) {
                        xmlEvent = xmlEventReader.nextEvent();
                        cdModel.setPrice(Double.parseDouble(xmlEvent.asCharacters().getData()));
                    } else if (startElementName.equals("year")) {
                        xmlEvent = xmlEventReader.nextEvent();
                        cdModel.setYear(Integer.parseInt(xmlEvent.asCharacters().getData()));
                    }
                }

                // if "/CD" tag is reached, add employee object to list
                if (xmlEvent.isEndElement()) {

                    EndElement endElement = xmlEvent.asEndElement();
                    if (endElement.getName().getLocalPart().toLowerCase().equals("cd")) {
                        cdModelList.add(cdModel);
                        cdModel = null;
                    }
                }
            }
        } catch (XMLStreamException | IOException e) {
            e.printStackTrace();
        }
        return cdModelList;
    }
}
