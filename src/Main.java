import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    static Parser parser;

    public static boolean commandsValidator(String cmd) throws Exception {
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
                File[] toPrint;
                if (args.size() == 0)
                   toPrint = Terminal.ls();
                else
                   toPrint = Terminal.ls(args.get(0));
                for(var file : toPrint)
                    System.out.println(file.getName());
                return true;
            case "cd":
                return Terminal.cd(args.size() == 0 ? "" : args.get(0));
            case "pwd":
                System.out.println( Terminal.pwd());
                return true;
            case "touch":
                return Terminal.touch(args.size() == 0 ? "" : args.get(0));
            case "rm":
                return Terminal.rm(args);
            case "mv":
                return Terminal.mv(args);
            case "more":
                return Terminal.more(args.size() == 0 ? "" : args.get(0));
            case "date":
                System.out.println(Terminal.date());
                return true;

        }
        System.out.println("bash:" + cmd + ": command not found");
        return false;
    }


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
                command +="|";
                String[] commands;
                commands = command.split(Pattern.quote("|"));
                for (String str : commands) {
                    parser.parse(str);
                    for (var xxx  : parser.getArgument())
                        System.out.println(xxx);
                    commandsValidator(parser.cmd);

                }

            } catch (Exception e) {
                e.printStackTrace();

            }


        }

    }

}
