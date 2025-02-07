package plivo_send_sms_automation;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MessageUtil {
    public static List<String[]> readCSV(String[] ids) throws CsvValidationException {
        List<String[]> messages = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader("customer_message.csv"))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                for (String id : ids) {
                    if (line[0].equals(id)) {
                        messages.add(line);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return messages;
    }
}

