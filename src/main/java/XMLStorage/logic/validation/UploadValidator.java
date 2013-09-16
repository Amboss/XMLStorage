package XMLStorage.logic.validation;

import XMLStorage.model.UploadedItem;
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
        return UploadedItem.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        UploadedItem uploadedItem = (UploadedItem) target;

        /*
         * validating for empty file
         */
        if (uploadedItem.getFile() != null) {

            /*
             * validating size of file (must be less than 100000 bytes)
             */
            if (uploadedItem.getFile().getSize() > 100000) {
                errors.rejectValue("multipartFile", "file.max_size", "file size to big");
            }

            /*
             * validating type of file (must be XMK only)
             */
            if (!(uploadedItem.getFile().getOriginalFilename().substring(
                    uploadedItem.getFile().getOriginalFilename()
                            .lastIndexOf('.')).equalsIgnoreCase(".xml"))) {
                errors.rejectValue("multipartFile", "file.wrong_format", "select XML type only");
            }
//            String mimeType = determineMimeType(myModelAttribute.getFile().getBytes());
//            if (mimeType.equalsIgnoreCase("application/pdf")){
//                result.addError(new ObjectError("file", "pdf not accepted"));
//            }
        } else {
            errors.rejectValue("multipartFile", "file.required", "select a file");
        }
    }
}
