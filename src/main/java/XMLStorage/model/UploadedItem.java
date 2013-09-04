package XMLStorage.model;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.Serializable;

/**
 * MultipartFile variable to store the uploaded file
 */
public class UploadedItem implements Serializable {

    private CommonsMultipartFile multipartFile;

    public UploadedItem() {}

    public UploadedItem(CommonsMultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }

    public CommonsMultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(CommonsMultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }
}
