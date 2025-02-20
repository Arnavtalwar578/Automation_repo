package plivo_send_sms_automation;

import com.plivo.api.Plivo;
import com.plivo.api.exceptions.PlivoRestException;
import com.plivo.api.exceptions.PlivoValidationException;
import com.plivo.api.models.message.MessageCreateResponse;
import com.plivo.api.models.message.Message;
import java.util.*;
import java.io.FileWriter;
import java.io.IOException;

public class PlivoUtil {
    static {
//    	Since not able to signup new plivo account just entered random strings for auth_id and auth_token
        Plivo.init("your_auth_id", "your_auth_token");
    }

    public static void sendSMS(List<String[]> messages) throws PlivoValidationException {
        for (String[] message : messages) {
            try {
                MessageCreateResponse response = Message.creator(
                        message[1], // src
                        message[2], // dst
                        message[3]  // text
                ).create();
                System.out.println("Message UUID: " + response.getMessageUuid());
                verifyMessageStatus(response.getMessageUuid().get(0));
            } catch (IOException | PlivoRestException e) {
                e.printStackTrace();
            }
        }
    }

    public static void verifyMessageStatus(String messageUuid) throws PlivoValidationException {
        try {
            Message message = Message.getter(messageUuid).get();
            String result = "Message UUID: " + message.getMessageUuid() +
                            ", Status: " + message.getMessageState();
            saveResult(result);
        } catch (IOException | PlivoRestException e) {
            e.printStackTrace();
        }
    }

    public static void saveResult(String result) {
        try (FileWriter writer = new FileWriter("result.txt", true)) {
            writer.write(result + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
