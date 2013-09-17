package XMLStorage.controller;

import XMLStorage.logic.service.CDService;
import XMLStorage.logic.validation.UploadValidator;
import XMLStorage.model.FileUploadForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;

/**
 * Controller handles upload page functionality
 */
@Controller
@RequestMapping("/upload_file")
public class UploadController {

    private UploadValidator validator;

    private CDService cdService;

    @Autowired
    public void setCdService(CDService cdService) {
        this.cdService = cdService;
    }

    @Autowired
    public void setValidator(UploadValidator validator) {
        this.validator = validator;
    }

    /**
     * Retrieving freeMarker/upload.ftl template
     *
     * @return upload page
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getUploadPage() {

        return new ModelAndView("pages/upload", "fileUploadForm", new FileUploadForm());
    }

    /**
     * Handles upload page on submit
     * BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()
     */
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView save(HttpServletRequest request,
                             @ModelAttribute("fileUploadForm") FileUploadForm fileUploadForm,
                             BindingResult errors) {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        fileUploadForm.setMultipartFile(multipartRequest.getFile("file"));

        // validating file
        validator.validate(fileUploadForm, errors);

        if (!errors.hasErrors()) {
            try {
                InputStream inputStream = fileUploadForm.getMultipartFile().getInputStream();

                //writing upload stream to existing XML file
                cdService.saveUploadedXmlFile(inputStream);
                inputStream.close();
                return new ModelAndView("redirect:/view");

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else {
            return new ModelAndView("pages/upload", "fileUploadForm", fileUploadForm);
        }
    }
}
