package XMLStorage.logic.service.impl;

import XMLStorage.logic.service.CDService;
import XMLStorage.model.CDModel;
import org.springframework.stereotype.Service;

import javax.xml.stream.*;
import javax.xml.stream.events.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class handel's CDModel functionality
 */
@Service("CDServiceImpl")
public class CDServiceImpl implements CDService {

    private static final String FILE_NAME = "target.xml";

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

            for(String key : elementNodes){
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
     *
     * Adding end-of-lines and tab information to your XML file to provide
     * functionality to format the XML file automatically.
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

        List<CDModel> items = new ArrayList<>();

        try {
            //create xml reader event with input stream
            XMLInputFactory inputFactory = XMLInputFactory.newFactory();

            // Setup a new eventReader
            InputStream input = new FileInputStream(FILE_NAME);

            // Setup a new eventReader
            XMLEventReader inputEventReader = inputFactory.createXMLEventReader(input);

            CDModel cdModel = new CDModel();

            while (inputEventReader.hasNext()) {

                //xml reader event to get event and determine start element and end element
                XMLEvent event = inputEventReader.nextEvent();

                if (event.isStartElement()) {

                    // If we have a item element we create a new item
                    StartElement startElement = event.asStartElement();
                    String startElementName = startElement.getName().getLocalPart();

                    if (startElementName.equals("title")) {
                        event = inputEventReader.nextEvent();
                        cdModel.setTitle(event.asCharacters().getData());
                    }

                    if (startElementName.equals("artist")) {
                        event = inputEventReader.nextEvent();
                        cdModel.setArtist(event.asCharacters().getData());
                    }

                    if (startElementName.equals("country")) {
                        event = inputEventReader.nextEvent();
                        cdModel.setCountry(event.asCharacters().getData());
                    }

                    if (startElementName.equals("company")) {
                        event = inputEventReader.nextEvent();
                        cdModel.setCompany(event.asCharacters().getData());
                    }

                    if (startElementName.equals("price")) {
                        event = inputEventReader.nextEvent();
                        cdModel.setPrice(Double.parseDouble(event.asCharacters().getData()));
                    }

                    if (startElementName.equals("year")) {
                        event = inputEventReader.nextEvent();
                        cdModel.setYear(Integer.valueOf(event.asCharacters().getData()));
                    }
                }

                // If we reach the end of an item element we add it to the list
                if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    String endElementName = endElement.asEndElement().getName().getLocalPart();
                    if (endElementName.equals("item")) {
                        items.add(cdModel);
                    }
                }
            }
        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }
        return items;
    }

}
