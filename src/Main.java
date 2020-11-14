import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    static Parser parser;

    public boolean commandsValidator(String cmd) throws Exception {
        var args = parser.getArgument();
        switch (cmd) {
            case "mkdir":
                return Terminal.mkdir(args.size() == 0 ? "" : args.get(0));
            case "rmdir":
                return Terminal.rmdir(args.size() == 0 ? "" : args.get(0));
            case "cat":
                var read = Terminal.cat(args);
                for (var line : read) {
                    System.out.println(line);
                }
                return true;
            case "ls":
                if (args.size() == 0)
                    Terminal.ls();
                else
                    Terminal.ls(args.get(0));
                return true;
            case "cd":
                return Terminal.cd(args.size() == 0 ? "" : args.get(0));
            case "pwd":
                Terminal.pwd();
                return true;
            case "touch":
                return Terminal.touch(args.size() == 0 ? "" : args.get(0));
            case "rm":
                return Terminal.rm(args);
            case "mv":
                return Terminal.mv(args);
            case "more":
                return Terminal.more(args.size() == 0 ? "" : args.get(0));

        }
        System.out.println("bash:" + cmd + ": command not found");
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
        String cmd;

        while (true) {
            System.out.print("%");
            String command = sc.nextLine();
            if (command.equals("exit")) {
                break;
            }
            try {
                String[] commands;
                if (command.contains("|")) {
                    commands = command.split(Pattern.quote("|"));
                } else {
                    commands = new String[1];
                    commands[0] = command;
                }
                for (String str : commands) {
                    parser.parse(str);
                    arguments = parser.getArgument();
                    command = parser.getCmd();
                }

            } catch (Exception e) {
                System.out.println(e);

            }


        }

    }

}
