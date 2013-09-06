package XMLStorage.model;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.Serializable;

/**
 * MultipartFile variable to store the uploaded file
 */
public class UploadedItem implements Serializable {

    private String name;

    private CommonsMultipartFile multipartFile;

    public UploadedItem() {}

    public UploadedItem(String name, CommonsMultipartFile multipartFile) {
        this.name = name;
        this.multipartFile = multipartFile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CommonsMultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(CommonsMultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }

    @Override
    public String toString() {
        return "UploadedItem{" +
                "name='" + name + '\'' +
                ", multipartFile=" + multipartFile +
                '}';
    }
}
