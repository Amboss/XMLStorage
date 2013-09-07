package XMLStorage.logic.service.impl;

import XMLStorage.logic.service.CDService;
import XMLStorage.model.CDModel;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.xml.stream.*;
import javax.xml.stream.events.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class handel's CDModel functionality
 */
@Service("CDServiceImpl")
public class CDServiceImpl implements CDService {

    private Resource myData = new ClassPathResource("/storage/target.xml");


    /**
     * Method to save Object to XML file
     */
    public void convertFromObjectToXML(String fileName,
                                       String rootElement,
                                       Map<String, String> elementsMap) {

        // Create a XMLOutputFactory
        XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();

        try {

            XMLEventWriter xmlEventWriter = xmlOutputFactory.createXMLEventWriter(
                    new FileOutputStream(fileName), "UTF-8");

            // Create a EventFactory
            XMLEventFactory eventFactory = XMLEventFactory.newInstance();

            XMLEvent end = eventFactory.createDTD("\n");

            StartDocument startDocument = eventFactory.createStartDocument();

            xmlEventWriter.add(startDocument);
            xmlEventWriter.add(end);

            StartElement configStartElement = eventFactory.createStartElement("", "", rootElement);

            xmlEventWriter.add(configStartElement);
            xmlEventWriter.add(end);

            // Write the element nodes
            Set<String> elementNodes = elementsMap.keySet();

            for (String key : elementNodes) {
                createNode(xmlEventWriter, key, elementsMap.get(key));
            }

            xmlEventWriter.add(eventFactory.createEndElement("", "", rootElement));
            xmlEventWriter.add(end);
            xmlEventWriter.add(eventFactory.createEndDocument());
            xmlEventWriter.close();

        } catch (XMLStreamException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adding end-of-lines and tab information to your XML file to provide
     * functionality to format the XML file automatically.
     *
     * @throws XMLStreamException
     */
    private static void createNode(XMLEventWriter eventWriter, String element,
                                   String value) throws XMLStreamException {

        XMLEventFactory xmlEventFactory = XMLEventFactory.newInstance();
        XMLEvent end = xmlEventFactory.createDTD("\n");
        XMLEvent tab = xmlEventFactory.createDTD("\t");

        //Create Start node
        StartElement sElement = xmlEventFactory.createStartElement("", "", element);
        eventWriter.add(tab);
        eventWriter.add(sElement);

        //Create Content
        Characters characters = xmlEventFactory.createCharacters(value);
        eventWriter.add(characters);

        // Create End node
        EndElement eElement = xmlEventFactory.createEndElement("", "", element);
        eventWriter.add(eElement);
        eventWriter.add(end);
    }


    /**
     * Method to convert from XML to Object
     *
     * @return List of DCModels
     */
    @SuppressWarnings("unchecked")
    public List<CDModel> convertFromXMLToObject() {

        List<CDModel> cdModelList = new ArrayList<>();

        CDModel cdModel = null;

        //create xml reader event with input stream
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        try {

            // retrieving target file
            File xmlFile = myData.getFile();

            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(
                    new FileInputStream(xmlFile));

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

                // if </CD> element is reached, add employee object to list
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
