package XMLStorage.model;

import org.springframework.web.multipart.MultipartFile;

/**
 * MultipartFile variable to store the uploaded file
 */
public class FileUploadForm {

    private MultipartFile multipartFile;

    public FileUploadForm() { }

    public FileUploadForm(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }
}
