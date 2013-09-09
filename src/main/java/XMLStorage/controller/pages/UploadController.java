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
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller handles upload page functionality
 */
@Controller
@RequestMapping("/upload_file")
public class UploadController extends AbstractController  {

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
        return new ModelAndView("redirect:/view");
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

        if (!errors.hasErrors()) {

            // retrieving fileName
            String fileName = uploadedItem.getMultipartFile().getOriginalFilename();

            try {

                CommonsMultipartFile file = uploadedItem.getMultipartFile();
                InputStream inputStream = file.getInputStream();

                // copying input to output
//                IOUtils.copy(inputStream, outputStream);
                // writing upload stream to existing XML file
                cdService.convertFromObjectToXML(inputStream);
                inputStream.close();

            } catch (Exception e) {
                return new ModelAndView("redirect:/confirm_upload/upload_failed", "filename", fileName);
            }
            return new ModelAndView("redirect:/confirm_upload/upload_success", "filename", fileName);

        } else {
            return new ModelAndView("pages/upload", "uploadedItem", uploadedItem);
        }
    }

//    @Override
//    public ModelAndView resolveException(HttpServletRequest arg0,
//                                         HttpServletResponse arg1,
//                                         Object arg2,
//                                         Exception exception) {
//
//        Map<Object, Object> model = new HashMap<Object, Object>();
//
//        if (exception instanceof MaxUploadSizeExceededException) {
//            model.put("errors", "File size should be less then " +
//                    ((MaxUploadSizeExceededException) exception).getMaxUploadSize() + " byte.");
//        } else {
//            model.put("errors", "Unexpected error: " + exception.getMessage());
//        }
//
//        return new ModelAndView("pages/upload", "uploadedItem", new UploadedItem());
//    }
}
