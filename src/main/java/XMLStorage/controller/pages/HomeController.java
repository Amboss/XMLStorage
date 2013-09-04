package XMLStorage.controller.pages;

import XMLStorage.controller.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller handles home page functionality
 */
@Controller
@RequestMapping("/home")
public class HomeController extends AbstractController {

    /**
     * Retrieving freeMarker/upload.ftl template
     *
     * @return upload page
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView loadHomePage() {

        return new ModelAndView("upload");
    }
}
