package XMLStorage.logic.service.impl;

import XMLStorage.logic.service.CDService;
import XMLStorage.model.CDModel;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
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
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
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

    protected static Logger logger = Logger.getLogger(CDServiceImpl.class);

    private Resource myData = new ClassPathResource("/storage/target.xml");

    /**
     * Method to parse CDModel List from InputStream by STax parser
     *
     * @return ArrayList<CDModel>
     * @throws RuntimeException
     */
    @SuppressWarnings("unchecked, null")
    public ArrayList<CDModel> getStoredList() {

        logger.debug("retrieving stored list of CD from " + myData.toString() + " file");

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
            throw new RuntimeException(e);
        }
        return cdModelList;
    }

    /**
     * Method to save Uploaded XML file with DOM parser
     *
     * @param inputStream must contain stream of XML type with XMLStorage.model.CDModel format
     * @throws RuntimeException
     */
    public void saveUploadedXmlFile(InputStream inputStream) {

        logger.debug("initiating saveUploadedXmlFile method");

        try {
            // initiating new Instance of DocumentBuilderFactory
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // initiating new TransformerFactory
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            // building stored Node List by CD tag
            Document storedDoc = docBuilder.parse(myData.getFile());
            storedDoc.getDocumentElement().normalize();
            NodeList storedNodeList = storedDoc.getDocumentElement().getChildNodes();

            // building upload Node List by CD tag
            Document uploadedDoc = docBuilder.parse(inputStream);
            uploadedDoc.getDocumentElement().normalize();
            NodeList uploadMainNodeList = uploadedDoc.getDocumentElement().getChildNodes();

            // iterating upload main Node List
            for (int i = 0; i < uploadMainNodeList.getLength(); i++) {

                // working with current "CD"
                Node cdNode = uploadMainNodeList.item(i);
                if (cdNode.getNodeType() == Node.ELEMENT_NODE && cdNode.getNodeName().equalsIgnoreCase("CD")) {
                    Element element = (Element) cdNode;
                    NodeList nodeList = element.getChildNodes();

                    // casting node list to object of CDModel type
                    CDModel cdModel = castToModel(nodeList);

                    // searching for similarity of title value
                    String xPathString = "./CD[TITLE = '" + cdModel.getTitle() + "']";
                    Node targetNode = findNode(storedNodeList, xPathString);

                    /* If similarity exists, then replacing with new content.
                    If now, then insert new "CD" node at the end of stored file. */
                    if (targetNode != null) {
                        logger.debug("Title duplicated: replacing existing node");
                        replaceTextContent(storedDoc, targetNode, cdModel, transformer);
                    } else {
                        logger.debug("Title not found in storage: adding new node to file");
                        appendToStoredFile(storedDoc, cdModel, transformer);
                    }
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException | TransformerConfigurationException e) {
            throw new RuntimeException();
        }
    }

    /**
     * Appending new "CD" element to stored XML file
     *
     * @param storedDoc target document buildup from storage file.
     * @param cdModel object that store new content.
     * @param transformer to handel save of changes.
     * @throws RuntimeException
     */
    private void appendToStoredFile(Document storedDoc, CDModel cdModel, Transformer transformer) {

        logger.debug("initiating appendToStoredFile method");

        try {
            Element rootElement = storedDoc.createElement("CD");

            Element titleElement = storedDoc.createElement("TITLE");
            titleElement.setTextContent(cdModel.getTitle());
            rootElement.appendChild(titleElement);

            Element artistElement = storedDoc.createElement("ARTIST");
            artistElement.setTextContent(cdModel.getArtist());
            rootElement.appendChild(artistElement);

            Element countryElement = storedDoc.createElement("COUNTRY");
            countryElement.setTextContent(cdModel.getCountry());
            rootElement.appendChild(countryElement);

            Element companyElement = storedDoc.createElement("COMPANY");
            companyElement.setTextContent(cdModel.getCompany());
            rootElement.appendChild(companyElement);

            Element priceElement = storedDoc.createElement("PRICE");
            priceElement.setTextContent(String.valueOf(cdModel.getPrice()));
            rootElement.appendChild(priceElement);

            Element yearElement = storedDoc.createElement("YEAR");
            yearElement.setTextContent(String.valueOf(cdModel.getYear()));
            rootElement.appendChild(yearElement);

            // adding new node to document
            Element root = storedDoc.getDocumentElement();
            root.appendChild(rootElement);

            transformer.transform(new DOMSource(root), new StreamResult(myData.getFile()));

        } catch (TransformerException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Replacing text content of target node, target node have to be at the storage file
     *
     * @param storedDoc target document buildup from storage file.
     * @param targetNode node where changes have to be made.
     * @param cdModel object that store new content.
     * @param transformer to handel save of changes.
     *
     * @return node with replaced content of child nodes
     * @throws RuntimeException
     */
    private void replaceTextContent(Document storedDoc, Node targetNode,
                                    CDModel cdModel, Transformer transformer) {

        logger.debug("initiating replaceTextContent method");

        try {

            NodeList targetList = targetNode.getChildNodes();
            for (int k = 0; k < targetList.getLength(); k++) {

                Node child = targetNode.getChildNodes().item(k);
                if (child.getNodeName().equals("TITLE")) {
                    child.setTextContent(cdModel.getTitle());
                } else if (child.getNodeName().equals("ARTIST")) {
                    child.setTextContent(cdModel.getArtist());
                } else if (child.getNodeName().equals("COUNTRY")) {
                    child.setTextContent(cdModel.getCountry());
                } else if (child.getNodeName().equals("COMPANY")) {
                    child.setTextContent(cdModel.getCompany());
                } else if (child.getNodeName().equals("PRICE")) {
                    child.setTextContent(String.valueOf(cdModel.getPrice()));
                } else if (child.getNodeName().equals("YEAR")) {
                    child.setTextContent(String.valueOf(cdModel.getYear()));
                }
            }

            // adding new node to document
            Element root = storedDoc.getDocumentElement();
            root.appendChild(targetNode);

            transformer.transform(new DOMSource(root), new StreamResult(myData.getFile()));

        } catch (TransformerException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Casting NodeList to CDModel
     *
     * @param nodeList contain list to be casted
     * @return Object with type CDModel
     */
    private CDModel castToModel(NodeList nodeList) {

        logger.debug("initiating castToModel method");

        CDModel cdModel = new CDModel();
        for (int j = 0; j < nodeList.getLength(); j++) {
            if (nodeList.item(j).getNodeName().equalsIgnoreCase("TITLE")) {
                cdModel.setTitle(nodeList.item(j).getTextContent());
            } else if (nodeList.item(j).getNodeName().equalsIgnoreCase("ARTIST")) {
                cdModel.setArtist(nodeList.item(j).getTextContent());
            } else if (nodeList.item(j).getNodeName().equalsIgnoreCase("COUNTRY")) {
                cdModel.setCountry(nodeList.item(j).getTextContent());
            } else if (nodeList.item(j).getNodeName().equalsIgnoreCase("COMPANY")) {
                cdModel.setCompany(nodeList.item(j).getTextContent());
            } else if (nodeList.item(j).getNodeName().equalsIgnoreCase("PRICE")) {
                cdModel.setPrice(Double.parseDouble(nodeList.item(j).getTextContent()));
            } else if (nodeList.item(j).getNodeName().equalsIgnoreCase("YEAR")) {
                cdModel.setYear(Integer.parseInt(nodeList.item(j).getTextContent()));
            }
        }
        return cdModel;
    }

    /**
     * Searching obj for similarity by xPathString
     *
     * @param obj contain target NodeList
     * @param xPathString contain path with searched value of "TITLE" tag
     * @return list of target child nodes
     * @throws RuntimeException
     */
    private Node findNode(Object obj, String xPathString) {

        logger.debug("initiating findNode method");

        Node node = null;
        try {
            XPath xpath = XPathFactory.newInstance().newXPath();
            XPathExpression expr = xpath.compile(xPathString);
            node = (Node) expr.evaluate(obj, XPathConstants.NODE);
        } catch (XPathExpressionException e) {
            throw new RuntimeException(e);
        }
        return node;
    }
}
