import jdk.jshell.spi.ExecutionControl;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
public class Main {
static Parser parser;
    public boolean commandsValidator(String cmd) throws Exception{
        if(cmd.equals("mkdir")){
            return Terminal.mkdir(parser.getArgument().size()==0? "" : parser.getArgument().get(0));
        }
        else if(cmd.equals("rmdir")){
            return Terminal.rmdir(parser.getArgument().size()==0? "" : parser.getArgument().get(0));
        }
        else if(cmd.equals("cat")){
            var read = Terminal.cat(parser.getArgument());
            for (var line:read) {
                System.out.println(line);
            }
            return true;
        }
        else if(cmd.equals("ls")){
            if(parser.getArgument().size()==0)
                Terminal.ls();
            else
                Terminal.ls(parser.getArgument().get(0));
            return true;
        }
        else if(cmd.equals("cd")){
            return Terminal.cd(parser.getArgument().size()==0? "" : parser.getArgument().get(0));
        }
        else if(cmd.equals("pwd")){
            Terminal.pwd();
            return true;
        }else if(cmd.equals("")){
            return Terminal.touch(parser.getArgument().size()==0? "" : parser.getArgument().get(0));
        }
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
        boolean terminate = false;

        parser = new Parser();
        Scanner sc = new Scanner(System.in);
        ArrayList<String> arguments = new ArrayList<String>();
        String cmd = new String();

        while (!terminate) {
            System.out.println("%");
            String command = sc.nextLine();
            if (command.equals("exit")) {
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
