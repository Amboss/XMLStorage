package XMLStorage.model;

import org.springframework.web.multipart.MultipartFile;

/**
 * MultipartFile variable to store the uploaded file
 */
public class UploadedItem {


    private MultipartFile multipartFile;

    public UploadedItem() {}

    public UploadedItem(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }

    public MultipartFile getFile() {
        return multipartFile;
    }

    public void setFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }

    @Override
    public String toString() {
        return "UploadedItem{multipartFile=" + multipartFile + '}';
    }
}
