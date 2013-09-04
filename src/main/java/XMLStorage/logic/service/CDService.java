package XMLStorage.logic.service;

import XMLStorage.model.CDModel;

import java.util.List;

/**
 * Interface for CDModel functionality
 */
public interface CDService {

    /**
     * Method to create CDModel entity
     */
    public void create();

    /**
     * Method to delete CDModel entity
     */
    public void delete();

    /**
     * Method to edit CDModel entity
     */
    public void edit();

    /**
     * @return DCModel entity
     */
    public List<CDModel> getCDModel();
}
