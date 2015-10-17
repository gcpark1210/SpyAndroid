package info.peoce.phonespy.model;

/**
 * Created by peoce on 15/10/17.
 */
public class CallLog {
    String phoneNumber;
    String callType;
    String duration;
    String contactsName;
    String date;

    public CallLog(String phoneNumber, String callType, String duration, String contactsName, String date) {
        this.phoneNumber = phoneNumber;
        this.callType = callType;
        this.duration = duration;
        this.contactsName = contactsName;
        this.date = date;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getContactsName() {
        return contactsName;
    }

    public void setContactsName(String contactsName) {
        this.contactsName = contactsName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "CallLog{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", callType='" + callType + '\'' +
                ", duration='" + duration + '\'' +
                ", contactsName='" + contactsName + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
