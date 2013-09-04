package XMLStorage.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Abstract controller for common pages functionality.
 */
@Controller
public abstract class AbstractController {

    protected static Logger logger = Logger.getLogger(AbstractController.class);

    /**
     * customised logger
     */
    public void showRequestLog(String str) {
        logger.debug("Received request to show " + str + " page");
    }

    /**
     * Action on button "Cancel" pressed.
     * @return redirect to view page
     */
    @RequestMapping(params = "cancel", method = RequestMethod.POST)
    protected ModelAndView onCancel(HttpServletRequest request,
                                    HttpServletResponse response) {
        return new ModelAndView("redirect:/view");
    }
}
