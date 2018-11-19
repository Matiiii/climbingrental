package pl.sda.javapoz.model;

/**
 * Created by RENT on 2017-03-27.
 */
public class Info {
    private String message;
    private Boolean status;

    public Info() {
    }

    public Info(String message, Boolean status) {
        this.message = message;
        this.status = status;
    }

    public Info(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
