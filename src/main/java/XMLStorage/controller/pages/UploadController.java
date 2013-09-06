package XMLStorage.controller.pages;

import XMLStorage.controller.AbstractController;
import XMLStorage.logic.service.CDService;
import XMLStorage.logic.validation.UploadValidator;
import XMLStorage.model.UploadedItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Controller handles upload page functionality
 */
@Controller
@RequestMapping("/upload_file")
public class UploadController extends AbstractController {

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
    public ModelAndView loadUploadPage() {

        return new ModelAndView("pages/upload", "uploadedItem", new UploadedItem());
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
        return loadUploadPage();
    }

    /**
     * Handles upload page on submit
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView onSubmit(
            @ModelAttribute("uploadedItem") UploadedItem uploadedItem, BindingResult errors) {

        // validating file
        validator.validate(uploadedItem, errors);

        // retrieving fileName
        String fileName = uploadedItem.getMultipartFile().getOriginalFilename();
        String filePath = System.getProperty("java.io.tmpdir") + "/"
                + uploadedItem.getMultipartFile().getOriginalFilename();

        if (!errors.hasErrors()) {

            FileOutputStream outputStream;
            try {
                // uploading provided file to outputStream
                outputStream = new FileOutputStream(new File(filePath));
                outputStream.write(uploadedItem.getMultipartFile().getFileItem().get());

                // writing upload stream to existing XML file
                //cdService.convertFromObjectToXML(outputStream);

                outputStream.close();
            } catch (Exception e) {
                return new ModelAndView("redirect:/confirm_upload/upload_failed", "filename", fileName);
            }

            return new ModelAndView("redirect:/confirm_upload/upload_success", "filename", fileName);

        } else {
            return new ModelAndView("pages/upload", "uploadedItem", uploadedItem);
        }
    }

}
