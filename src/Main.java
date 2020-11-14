import jdk.jshell.spi.ExecutionControl;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public boolean commandsValidator(String cmd) {
        return true;
    }

    public boolean ArgumentsValidator(ArrayList<String> arguments, String cmd) {
        if (arguments.size() == 1) {
            if (cmd == "mkdir" || cmd == "rmdir" || cmd == "touch" ||
                    cmd == "ls" || cmd == "cd" || cmd == "inputRedirect" || cmd == "cat") {
                return true;
            }
        } else if (arguments.size() == 0) {
            if (cmd == "clear" || cmd == "ls" || cmd == "pwd") {
                return true;
            }
        } else if (arguments.size() > 1) {
            if (cmd == "cat")
                return true;

        }


    }

    public static void main(String[] args) {
        boolean terminate = false;
        Terminal terminal = new Terminal();
        Parser parser = new Parser();
        Scanner sc = new Scanner(System.in);
        ArrayList<String> arguments = new ArrayList<String>();
        String cmd = new String();

        while (!terminate) {
            System.out.println("%");
            String command = sc.nextLine();
            if (command == "exit") {
                terminate = true;
                break;
            }
            try {

                parser.parse(command);
                arguments = parser.getArgument();
                command = parser.getCmd();


            } catch (Exception e) {
                System.out.println(e);
                continue;


            }


        }

    }

}
