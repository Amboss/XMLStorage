package XMLStorage.controller.pages;

import XMLStorage.controller.AbstractController;
import org.springframework.stereotype.Controller;
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
     * Retrieving freeMarker/download.ftl template
     *
     * @return upload page
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView loadUploadPage() {

        return new ModelAndView("pages/download");
    }

    /**
     * download
     */
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView onSubmit(HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
//        int id = ServletRequestUtils.getRequiredIntParameter(request, "id");
//
//        Files file = this.filesService.find(id);
//
//        response.setContentType(file.getType());
//        response.setContentLength(file.getFile().length);
//        response.setHeader("Content-Disposition","attachment; filename=\"" + file.getFilename() +"\"");
//
//        FileCopyUtils.copy(file.getFile(), response.getOutputStream());

        return null;

    }


}
