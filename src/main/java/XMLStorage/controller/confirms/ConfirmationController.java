package XMLStorage.controller.confirms;

import XMLStorage.controller.AbstractController;
import XMLStorage.model.ConfirmMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller handles upload confirmations
 */
@Controller
@RequestMapping("/confirm")
public class ConfirmationController extends AbstractController{

    /**
     * @return confirmations/confirm page with upload success message
     */
    @RequestMapping(value = "/upload_success",method = RequestMethod.GET)
    public ModelAndView loadUploadSuccessPage() {

        ConfirmMessage confirm = new ConfirmMessage();
        confirm.setLabel("Upload successful");
        confirm.setMessage("Your upload is successful!");
        return new ModelAndView("confirmation/confirm_alert", "confirm", confirm);
    }

    /**
     * @return confirmations/confirm page with upload failed message
     */
    @RequestMapping(value = "/upload_failed",method = RequestMethod.GET)
    public ModelAndView loadUploadFailedPage() {

        ConfirmMessage confirm = new ConfirmMessage();
        confirm.setLabel("Upload failed");
        confirm.setMessage("Your upload is failed!");
        return new ModelAndView("confirmation/confirm_alert", "confirm", confirm);
    }

    /**
     * @return confirmations/confirm page with download success message
     */
    @RequestMapping(value = "/download_success",method = RequestMethod.GET)
    public ModelAndView loadDownloadSuccessPage() {

        ConfirmMessage confirm = new ConfirmMessage();
        confirm.setLabel("Download successful");
        confirm.setMessage("Your download is successful!");
        return new ModelAndView("confirmation/confirm_alert", "confirm", confirm);
    }

    /**
     * @return confirmations/confirm page with download failed message
     */
    @RequestMapping(value = "/download_failed",method = RequestMethod.GET)
    public ModelAndView loadDownloadFailedPage() {

        ConfirmMessage confirm = new ConfirmMessage();
        confirm.setLabel("Download failed");
        confirm.setMessage("Your download is failed!");
        return new ModelAndView("confirmation/confirm_alert", "confirm", confirm);
    }
    /**
     * Action on button "Ok" pressed.
     *
     * @return redirect to view page
     */
    @RequestMapping(params = "ok", method = RequestMethod.POST)
    protected ModelAndView onSubmit(HttpServletRequest request,
                                    HttpServletResponse response) {
        return new ModelAndView("redirect:/view");
    }



}
