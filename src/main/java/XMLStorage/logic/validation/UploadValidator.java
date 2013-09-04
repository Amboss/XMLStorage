package XMLStorage.logic.validation;

import XMLStorage.model.CDModel;
import XMLStorage.model.UploadedItem;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * Validator for uploaded files
 */
@Component
public class UploadValidator implements Validator {

    private CDModel cdModel;

    private UploadedItem uploadedItem;

    @Override
    public boolean supports(Class clazz) {
        // validate the Employee instances
        return CommonsMultipartFile.class.isAssignableFrom(clazz);
    }

    @Override
        public void validate(Object target, Errors errors) {

        uploadedItem = (UploadedItem) target;

        /*
         * validating for empty file
         */
        if(!uploadedItem.getMultipartFile().isEmpty()) {

            /*
             * validating size of file
             */
            if(uploadedItem.getMultipartFile().getSize() > 100000) {
                errors.rejectValue("file", "file.max_size");
            }

            /*
             * validating type of file
             */
//            if(uploadedItem.getMultipartFile().getContentType().equals("xml")) {
//                  errors.rejectValue("file", "file.wrong_format");
//            }
        } else {
            errors.rejectValue("file", "file.required");
        }
    }
}
