package XMLStorage.logic.service;

import XMLStorage.model.CDModel;

import java.util.List;
import java.util.Map;

/**
 * Interface for CDModel functionality
 */
public interface CDService {

    /**
     * Method to save CDModel
     */
    public void convertFromObjectToXML(String fileName, String rootElement, Map<String, String> elementsMap);

    /**
     * Method to load CDModel
     */
    public List<CDModel> convertFromXMLToObject();

}
