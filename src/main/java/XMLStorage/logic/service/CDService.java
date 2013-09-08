package XMLStorage.logic.service;

import XMLStorage.model.CDModel;

import java.io.InputStream;
import java.util.List;

/**
 * Interface for CDModel functionality
 */
public interface CDService {

    /**
     * Method to save CDModel
     */
    public void convertFromObjectToXML(InputStream inputStream);

    /**
     * @return List of DCModels for view page
     */
    public List<CDModel> getViewObject();

    /**
     * Method to parse CDModel List from InputStream
     */
    public List<CDModel> convertFromXMLToObject(InputStream inputStream);

}
