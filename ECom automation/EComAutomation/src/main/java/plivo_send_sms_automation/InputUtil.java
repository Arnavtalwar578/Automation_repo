package plivo_send_sms_automation;

import java.util.Scanner;

public class InputUtil {
    public static String[] getCustomerIDs() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter customer IDs (e.g., 1 or 1,3 or 1,2,3): ");
        String input = scanner.nextLine();
        scanner.close();
        return input.split(",");
    }
}
