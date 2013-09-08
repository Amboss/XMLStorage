package XMLStorage.logic.service.impl;

import XMLStorage.logic.service.CDService;
import XMLStorage.model.CDModel;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Class handel's CDModel functionality
 */
@Service("CDServiceImpl")
public class CDServiceImpl implements CDService {

    private Resource myData = new ClassPathResource("/storage/target.xml");


    /**
     * Method to save Object to XML file
     */
    public void convertFromObjectToXML(InputStream inputStream) {

        // Create a XMLOutputFactory
        XMLOutputFactory xmlof = XMLOutputFactory.newInstance();

        List<CDModel> uploadList = convertFromXMLToObject(inputStream);
    }

    /**
     *
     * @return List of DCModels for view page
     */
    public List<CDModel> getViewObject() {

        FileInputStream inputStream = null;

        try {

            File xmlFile = myData.getFile();
            inputStream = new FileInputStream(xmlFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return convertFromXMLToObject(inputStream);
    }

    /**
     * Method to parse CDModel List from InputStream
     *
     * @return List of DCModels
     */
    @SuppressWarnings("unchecked, null")
    public List<CDModel> convertFromXMLToObject(InputStream inputStream) {

        List<CDModel> cdModelList = new ArrayList<>();

        CDModel cdModel = null;

        //create xml reader event with input stream
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();

        try {

//            // retrieving target file
//            File xmlFile = myData.getFile();

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

                // if </CD> element is reached, add employee object to list
                if (xmlEvent.isEndElement()) {

                    EndElement endElement = xmlEvent.asEndElement();
                    if (endElement.getName().getLocalPart().toLowerCase().equals("cd")) {
                        cdModelList.add(cdModel);
                        cdModel = null;
                    }
                }
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return cdModelList;

    }
}
