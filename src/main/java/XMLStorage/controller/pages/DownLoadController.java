package XMLStorage.controller.pages;

import XMLStorage.controller.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller handles download page functionality
 */
@Controller
@RequestMapping(value = "download_file")
public class DownLoadController extends AbstractController {

    /**
     * @return download page
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView loadUploadPage() {

        return new ModelAndView("pages/download");
    }

    /**
     * Action on button "Cancel" pressed.
     *
     * @return redirect to view page
     */
    @Override
    @RequestMapping(params = "cancel", method = RequestMethod.POST)
    protected ModelAndView onCancel(HttpServletRequest request,
                                    HttpServletResponse response) {
        return new ModelAndView("redirect:/view");
    }

    /**
     * Handel's download action
     */
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView onSubmit(HttpServletRequest request,
                                 HttpServletResponse response,
                                 BindingResult errors) throws Exception {

        if (!errors.hasErrors()) {

//        int id = ServletRequestUtils.getRequiredIntParameter(request, "id");
//
//        Files file = this.filesService.find(id);
//
//        response.setContentType(file.getType());
//        response.setContentLength(file.getFile().length);
//        response.setHeader("Content-Disposition","attachment; filename=\"" + file.getFilename() +"\"");
//
//        FileCopyUtils.copy(file.getFile(), response.getOutputStream());

            return new ModelAndView("redirect:/confirm_download/download_success");
        } else {
            return new ModelAndView("redirect:/confirm_download/download_failed");
        }
    }
}
