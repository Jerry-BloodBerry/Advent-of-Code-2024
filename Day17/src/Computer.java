import java.util.ArrayList;
import java.util.List;

public class Computer {
    private int instructionPointer = 0;
    private final int[] instructions;
    private int aRegister;
    private int bRegister;
    private int cRegister;
    private final List<String> outputList = new ArrayList<>();

    public Computer(int[] instructions, int aRegister, int bRegister, int cRegister) {
        this.instructions = instructions;
        this.aRegister = aRegister;
        this.bRegister = bRegister;
        this.cRegister = cRegister;
    }

    public void runProgram() {
        while (instructionPointer < instructions.length - 1) {
            runNextInstruction();
        }
    }

    private void runNextInstruction() {
        var instruction = instructions[instructionPointer];
        var operand = instructions[instructionPointer + 1];
        switch (instruction) {
            case 0 -> adv(operand);
            case 1 -> bxl(operand);
            case 2 -> bst(operand);
            case 3 -> jnz(operand);
            case 4 -> bxc(operand);
            case 5 -> out(operand);
            case 6 -> bdv(operand);
            case 7 -> cdv(operand);
        }
    }

    private void cdv(int operand) {
        cRegister = aRegister / (int)(Math.pow(2, combo(operand)));
        instructionPointer += 2;
    }

    private void bdv(int operand) {
        bRegister = aRegister / (int)(Math.pow(2, combo(operand)));
        instructionPointer += 2;
    }

    private void out(int operand) {
        int value = combo(operand) % 8;
        outputList.add("" + value);
        instructionPointer += 2;
    }

    private void bxc(int operand) {
        bRegister = bRegister ^ cRegister;
        instructionPointer += 2;
    }

    private void jnz(int operand) {
        if (aRegister == 0) {
            instructionPointer += 2;
            return;
        }
        instructionPointer = operand;
    }

    private void bst(int operand) {
        bRegister = combo(operand) % 8;
        instructionPointer += 2;
    }

    private void bxl(int operand) {
        bRegister = bRegister ^ operand;
        instructionPointer += 2;
    }

    private void adv(int operand) {
        aRegister /= (int)(Math.pow(2, combo(operand)));
        instructionPointer += 2;
    }

    public int combo(int operand) {
        if (operand >= 0 && operand <= 3) {
            return operand;
        } else if (operand == 4) {
            return aRegister;
        } else if (operand == 5) {
            return bRegister;
        } else if (operand == 6) {
            return cRegister;
        }
        throw new IllegalStateException("Unexpected value: " + operand);
    }

    public void printOutput() {
        System.out.println(String.join(",", outputList));
    }
}
