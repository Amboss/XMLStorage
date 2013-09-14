package XMLStorage.controller.pages;

import XMLStorage.controller.AbstractController;
import XMLStorage.util.CountInputStream;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

/**
 * Controller handles download page functionality
 */
@Controller
@RequestMapping(value = "download_file")
public class DownLoadController extends AbstractController {

    /**
     * Path of the file to be downloaded, relative to application's directory
     */
    private String filePath = "/storage/target.xml";

    /**
     * @return download page
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView loadUploadPage() throws ParserConfigurationException, IOException, SAXException {

        /*
            retrieving size of stored xml file
            size in MB is : " + (double)fileSize/(1024*1024));
         */
        URL url = getClass().getResource(filePath);
        CountInputStream counter = new CountInputStream(url.openStream());
        DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(counter);

        return new ModelAndView("pages/download", "fileinfo", (int) counter.getCount() / 1024);
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
    public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response) {

        try {

            URL url = getClass().getResource(filePath);
            File file = new File(url.toURI());

            if (file.exists()) {
                response.setContentType("application/xls");
                response.setContentLength(new Long(file.length()).intValue());
                response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
                FileCopyUtils.copy(new FileInputStream(file), response.getOutputStream());

                return new ModelAndView("redirect:/confirm_download/download_success");
            } else {
                return new ModelAndView("redirect:/confirm_download/download_failed");
            }
        } catch (Exception e) {
            return new ModelAndView("redirect:/confirm_download/download_failed");
        }
    }
}
