package info.peoce.phonespy.model;

/**
 * Created by peoce on 15/10/17.
 */
public class ShortMessage {
    String name;
    String body;
    String date;
    String type;
    String phoneNumber;

    public ShortMessage(String name, String body, String date, String type, String phoneNumber) {
        this.name = name;
        this.body = body;
        this.date = date;
        this.type = type;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "ShortMessage{" +
                "name='" + name + '\'' +
                ", body='" + body + '\'' +
                ", date='" + date + '\'' +
                ", type='" + type + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
