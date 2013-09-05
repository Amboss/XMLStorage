package XMLStorage.controller.pages;

import XMLStorage.controller.AbstractController;
import XMLStorage.logic.validation.UploadValidator;
import XMLStorage.model.UploadedItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller handles upload page functionality
 */
@Controller
@RequestMapping("/upload_file")
public class UploadController extends AbstractController {

    private UploadValidator validator;

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

        return new ModelAndView("pages/upload");
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
            @RequestParam(value = "file", required = false) UploadedItem uploadedItem,
            HttpServletResponse response,
            BindingResult errors) {

        validator.validate(uploadedItem, errors);

        String orgName = uploadedItem.getMultipartFile().getOriginalFilename();
        if (!errors.hasErrors()) {

//            String filePath = "/my_uploads/" + orgName;
//            File dest = new File(filePath);
//            try {
//                multipartFile.transferTo(dest);
//            } catch (IllegalStateException e) {
//                e.printStackTrace();
//                return "File uploaded failed:" + orgName;
//            } catch (IOException e) {
//                e.printStackTrace();
//                return "File uploaded failed:" + orgName;
//            }
            return new ModelAndView("redirect:/confirm_upload/upload_success");
        } else {
            return new ModelAndView("redirect:/confirm_upload/upload_failed");
        }
    }




//    public ModelAndView upload(HttpServletRequest request,
//                               HttpServletResponse response) throws Exception {
//
//        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//        MultipartFile multipartFile = multipartRequest.getFile("file");
//
//        Files file = new Files();
//        file.setFilename(multipartFile.getOriginalFilename());
//        file.setNotes(ServletRequestUtils.getStringParameter(request, "notes"));
//        file.setType(multipartFile.getContentType());
//        file.setFile(multipartFile.getBytes());
//
//        this.filesService.save(file);
//
//        return new ModelAndView("");
//    }
}
