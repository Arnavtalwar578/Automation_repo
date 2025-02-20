package plivo_send_sms_automation;

import java.io.FileWriter;
import java.io.IOException;
import com.opencsv.CSVWriter;

public class CSVUtil {
    public static void createCSV() {
        String csvFile = "customer_message.csv";
        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFile))) {
            String[] header = {"ID", "SourceNumber", "DestinationNumber", "Message"};
            writer.writeNext(header);

            String[] data1 = {"1", "src", "dst1", "Sending the SMS to customer ID 1"};
            String[] data2 = {"2", "src", "dst2", "Sending the SMS to customer ID 2"};
            String[] data3 = {"3", "src", "dst3", "Sending the SMS to customer ID 3"};
            String[] data4 = {"4", "src", "dst4", "Sending the SMS to customer ID 4"};

            writer.writeNext(data1);
            writer.writeNext(data2);
            writer.writeNext(data3);
            writer.writeNext(data4);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        createCSV();
    }
}
