package XMLStorage.model;

/**
 * Class for confirmation model
 */
public class ConfirmMessage {

    private String label;

    private String message;

    public ConfirmMessage(){}

    public ConfirmMessage(String label, String message) {
        this.label = label;
        this.message = message;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
