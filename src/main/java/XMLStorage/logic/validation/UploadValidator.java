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
        if(!uploadedItem.getMultipartFile().isEmpty()) {

            /*
             * validating size of file
             */
            if(uploadedItem.getMultipartFile().getSize() > 100000) {
                errors.rejectValue("file", "file.max_size", "file size to big");
            }

            /*
             * validating type of file
             */
            if(!uploadedItem.getMultipartFile().getContentType().equals("xml")) {
                  errors.rejectValue("file", "file.wrong_format", "select XML type only");
            }
        } else {
            errors.rejectValue("file", "file.required", "select a file");
        }
    }
}
