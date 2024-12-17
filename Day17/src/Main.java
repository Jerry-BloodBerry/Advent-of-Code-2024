import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        var url = Main.class.getResource("input.txt");
        assert url != null;
        Scanner sc = new Scanner(new File(url.getPath()));
        var computer = initializeComputerFromScannerInput(sc);
        computer.runProgram();
        computer.printOutput();
    }

    private static Computer initializeComputerFromScannerInput(Scanner sc) {
        int aRegister = readRegisterValueFromDebuggerLine(sc.nextLine());
        int bRegister = readRegisterValueFromDebuggerLine(sc.nextLine());
        int cRegister = readRegisterValueFromDebuggerLine(sc.nextLine());
        sc.nextLine();
        int[] instructions = readProgramArrayFromDebuggerLine(sc.nextLine());
        return new Computer(instructions, aRegister, bRegister, cRegister);
    }

    private static int readRegisterValueFromDebuggerLine(String line) {
        return Integer.parseInt(line.split(": ")[1]);
    }

    private static int[] readProgramArrayFromDebuggerLine(String line) {
        var programStringArray = line.split(": ")[1].split(",");
        int[] programArray = new int[programStringArray.length];
        int i = 0;
        for (String s : programStringArray) {
            int parseInt = Integer.parseInt(s);
            programArray[i] = parseInt;
            i++;
        }
        return programArray;
    }
}
