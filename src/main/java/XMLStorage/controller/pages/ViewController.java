package XMLStorage.controller.pages;

import XMLStorage.controller.AbstractController;
import XMLStorage.logic.service.CDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller handles view page functionality
 */
@Controller
@RequestMapping("/view")
public class ViewController extends AbstractController {

    private CDService service;

    @Autowired
    public void setService(CDService service) {
        this.service = service;
    }

    /**
     * Retrieving freeMarker/upload.ftl template
     *
     * @return upload page
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView loadViewPage() {

        return new ModelAndView("pages/view", "modelList", service.getStoredList());
    }
}
