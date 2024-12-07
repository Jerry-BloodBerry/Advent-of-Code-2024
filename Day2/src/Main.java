import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        URL url = Main.class.getResource("input.txt");
        assert url != null;
        Scanner sc = new Scanner(new File(url.getPath()));
        int safeReports = 0;
        while (sc.hasNextLine()) {
            var line = sc.nextLine();
            var splitLine = line.split(" ");
            int[] reportNumbers = new int[splitLine.length];
            for (int i = 0; i < splitLine.length; i++) {
                reportNumbers[i] = Integer.parseInt(splitLine[i]);
            }
            safeReports += isReportSafe2(reportNumbers) ? 1 : 0;
        }
        System.out.printf("Number of safe reports: %d", safeReports);
    }

    public static boolean isReportSafe(int[] reportNumbers) {
        if (reportNumbers.length == 1) return true;
        int difference = reportNumbers[1] - reportNumbers[0];
        if (!safeDifference(difference)) return false;
        int initialSign = sign(difference);
        for (int i = 1; i < reportNumbers.length - 1; i++) {
            difference = reportNumbers[i+1] - reportNumbers[i];
            if (!safeDifference(difference) || sign(difference) != initialSign) return false;
        }
        return true;
    }

    public static boolean isReportSafe2(int[] reportNumbers) {
        if (isReportSafe(reportNumbers)) return true;

        for (int i = 0; i < reportNumbers.length; i++) {
            if (isReportSafe(modifiedReport(reportNumbers, i))) {
                return true;
            }
        }

        return false;
    }

    public static int[] modifiedReport(int[] reportNumbers, int violatingIndex)
    {
        int[] modifiedReport = new int[reportNumbers.length - 1];
        int i = 0;
        while (i < violatingIndex) {
            modifiedReport[i] = reportNumbers[i];
            i++;
        }
        int j = violatingIndex + 1;
        while (j < reportNumbers.length)
        {
            modifiedReport[i] = reportNumbers[j];
            j++;
            i++;
        }
        return modifiedReport;
    }

    public static int sign(int number) {
        return Integer.compare(number, 0);
    }

    public static boolean safeDifference(int difference) {
        int absDifference = Math.abs(difference);
        return absDifference >= 1 && absDifference <= 3;
    }
}
