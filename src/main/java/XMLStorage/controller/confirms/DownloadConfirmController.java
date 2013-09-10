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
@RequestMapping("/confirm_download")
public class DownloadConfirmController extends AbstractController{

    /**
     * @return confirmations/confirm page with download success message
     */
    @RequestMapping(value = "/download_success", method = RequestMethod.GET)
    public ModelAndView loadDownloadSuccessPage() {

        ConfirmMessage confirm = new ConfirmMessage();
        confirm.setLabel("Download successful");
        confirm.setMessage("Your download is successful!");
        return new ModelAndView("confirmation/confirm_download", "confirm", confirm);
    }

    /**
     * Action on button "Ok" pressed after successful download.
     *
     * @return redirect to view page
     */
    @RequestMapping(value = "/download_success", params = "ok", method = RequestMethod.POST)
    protected ModelAndView onSuccessSubmit(HttpServletRequest request,
                                           HttpServletResponse response) {
        return new ModelAndView("redirect:/view");
    }

    /**
     * @return confirmations/confirm page with download failed message
     */
    @RequestMapping(value = "/download_failed", method = RequestMethod.GET)
    public ModelAndView loadDownloadFailedPage() {

        ConfirmMessage confirm = new ConfirmMessage();
        confirm.setLabel("Download failed");
        confirm.setMessage("Your download is failed!");
        return new ModelAndView("confirmation/confirm_download", "confirm", confirm);
    }

    /**
     * Action on button "Ok" pressed after failure of download.
     *
     * @return redirect to download_file page
     */
    @RequestMapping(value = "/download_failed", params = "ok", method = RequestMethod.POST)
    protected ModelAndView onFailerSubmit(HttpServletRequest request,
                                    HttpServletResponse response) {
        return new ModelAndView("redirect:/download_file");
    }

}
