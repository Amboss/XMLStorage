package XMLStorage.logic.validation;

import XMLStorage.model.FileUploadForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validator for uploaded files
 */
@Component
public class UploadValidator implements Validator {

    @Override
    public boolean supports(Class clazz) {
        // validate the Employee instances
        return FileUploadForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        FileUploadForm fileUploadForm = (FileUploadForm) target;

        /*
         * validating for empty file
         */
        if (!fileUploadForm.getMultipartFile().isEmpty()) {

            /*
             * validating size of file (must be less than 100000 bytes)
             */
            if (fileUploadForm.getMultipartFile().getSize() > 100000) {
                errors.rejectValue("multipartFile", "file.max_size", "file size to big");
            }

            /*
             * validating type of file (must be XMK only)
             */
            if (!(fileUploadForm.getMultipartFile().getOriginalFilename().substring(
                    fileUploadForm.getMultipartFile().getOriginalFilename().lastIndexOf('.'))
                    .equalsIgnoreCase(".xml"))) {
                errors.rejectValue("multipartFile", "file.wrong_format", "select XML type only");
            }
        } else {
            errors.rejectValue("multipartFile", "file.required", "select a file");
        }
    }
}
