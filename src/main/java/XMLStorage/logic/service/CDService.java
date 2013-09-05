package XMLStorage.logic.service;

import XMLStorage.model.CDModel;

import java.util.List;

/**
 * Interface for CDModel functionality
 */
public interface CDService {

    /**
     * Method to save CDModel
     */
    public void convertFromObjectToXML();

    /**
     * Method to load CDModel
     */
    public List<CDModel> convertFromXMLToObject();

}
