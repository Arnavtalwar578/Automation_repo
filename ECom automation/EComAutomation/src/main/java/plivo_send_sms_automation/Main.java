package plivo_send_sms_automation;
import java.util.List;

import com.opencsv.exceptions.CsvValidationException;
import com.plivo.api.exceptions.PlivoValidationException;

public class Main {
	public static void main(String[] args) throws CsvValidationException, PlivoValidationException {
//    	Since not able to signup new plivo account just entered random strings for auth_id and auth_token

        CSVUtil.createCSV();
        String[] customerIDs = InputUtil.getCustomerIDs();
        List<String[]> messages = MessageUtil.readCSV(customerIDs);
        PlivoUtil.sendSMS(messages);
    }

}
