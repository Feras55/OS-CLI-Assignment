import java.util.ArrayList;

public class Parser {
    ArrayList<String> args = new ArrayList<String>();
    String cmd;

    public boolean parse (String input){
        args.clear();
        try {
            if (input.isEmpty()) return false;
            String[] strings = input.trim().split("\\s+");

            cmd = strings[0];
            boolean first = true;
            for (String str : strings) {
                if (first) {
                    first = false;
                    continue;
                }
                args.add(str);
            }
            return true;
        }
        catch(Exception e){
            System.out.println(e);
            return false;
        }
    }

    public String getCmd(){
        return cmd;
    }
    public ArrayList<String> getArgument(){
        return args;
    }
    public static void main(String[] args){
        for (String str : "    hello world   ".trim().split(" ")) System.out.println(str);
    }
}