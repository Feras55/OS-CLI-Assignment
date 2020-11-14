import java.util.ArrayList;
import java.util.Scanner;
public class Main {
static Parser parser;
    public boolean commandsValidator(String cmd) throws Exception{
        switch (cmd) {
            case "mkdir":
                return Terminal.mkdir(parser.getArgument().size() == 0 ? "" : parser.getArgument().get(0));
            case "rmdir":
                return Terminal.rmdir(parser.getArgument().size() == 0 ? "" : parser.getArgument().get(0));
            case "cat":
                var read = Terminal.cat(parser.getArgument());
                for (var line : read) {
                    System.out.println(line);
                }
                return true;
            case "ls":
                if (parser.getArgument().size() == 0)
                    Terminal.ls();
                else
                    Terminal.ls(parser.getArgument().get(0));
               return true;
            case "cd":
                return Terminal.cd(parser.getArgument().size() == 0 ? "" : parser.getArgument().get(0));
            case "pwd":
                Terminal.pwd();
                return true;
            case "touch":
                return Terminal.touch(parser.getArgument().size() == 0 ? "" : parser.getArgument().get(0));
            case "rm":
                return Terminal.rm(parser.getArgument());
        }
        System.out.println("bash:" + cmd +": command not found");
        return false;
    }
/*
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

        return true;
    }
*/
    public static void main(String[] args) {
        parser = new Parser();
        Scanner sc = new Scanner(System.in);
        ArrayList<String> arguments;
        String cmd ;

        while (true) {
            System.out.println("%");
            String command = sc.nextLine();
            if (command.equals("exit")) {
                break;
            }
            try {

                parser.parse(command);
                arguments = parser.getArgument();
                command = parser.getCmd();


            } catch (Exception e) {
                System.out.println(e);

            }


        }

    }

}
