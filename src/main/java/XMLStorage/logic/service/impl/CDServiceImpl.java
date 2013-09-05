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
import java.util.LinkedList;
import java.util.List;

/**
 * Class handel's CDModel functionality
 */
@Service("CDServiceImpl")
public class CDServiceImpl implements CDService {

    private static final String FILE_NAME = "target.xml";

    /**
     * Method to save Object to XML file
     */
    public void convertFromObjectToXML() {

        // Create a XMLOutputFactory
        XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
        // Create XMLEventWriter
        XMLEventWriter eventWriter = null;

        try {

            eventWriter = outputFactory.createXMLEventWriter(new FileOutputStream(FILE_NAME));

            // Create a EventFactory
            XMLEventFactory eventFactory = XMLEventFactory.newInstance();
            XMLEvent end = eventFactory.createDTD("\n");

            // Create and write Start Tag
            StartDocument startDocument = eventFactory.createStartDocument();
            eventWriter.add(startDocument);

            // Create config open tag
            StartElement configStartElement = eventFactory.createStartElement("", "", "cd");
            eventWriter.add(configStartElement);
            eventWriter.add(end);

            // Write the different nodes
            createNode(eventWriter, "title", "");

            eventWriter.add(eventFactory.createEndElement("", "", "cd"));
            eventWriter.add(end);
            eventWriter.add(eventFactory.createEndDocument());
            eventWriter.close();

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
    private void createNode(XMLEventWriter eventWriter, String name,
                            String value) throws XMLStreamException {

        XMLEventFactory eventFactory = XMLEventFactory.newInstance();

        XMLEvent end = eventFactory.createDTD("\n");
        XMLEvent tab = eventFactory.createDTD("\t");

        // Create Start node
        StartElement sElement = eventFactory.createStartElement("", "", name);
        eventWriter.add(tab);
        eventWriter.add(sElement);

        // Create Content
        Characters characters = eventFactory.createCharacters(value);
        eventWriter.add(characters);

        // Create End node
        EndElement eElement = eventFactory.createEndElement("", "", name);
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

        List<CDModel> items = new LinkedList<>();

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
