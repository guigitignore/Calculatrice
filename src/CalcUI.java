import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalcUI {
    private PileRPL pile;
    private PrintStream out;


    CalcUI(PileRPL pile,BufferedReader in,PrintStream out){
        this.pile=pile;
        this.out=out;

        boolean quit=false;
        String command=null;

        out.println("Welcome to RPL Calculator");
        help();

        while(quit!=true){
            if (!pile.isEmpty()) affichePile();
            try{
                out.print("Enter a command: "); 
                command=in.readLine();
                if (command==null || command.equals("exit")) throw new IOException();
                if (command.equals("help")) help();
                else evaluer(command);
            }catch(IOException e){
                quit=true;
            }  
        }
    }


    CalcUI(BufferedReader in,PrintStream out){
        this(new PileRPL(),in,out);
    }

    CalcUI(){
        this(new PileRPL(),new BufferedReader(new InputStreamReader(System.in)),System.out);
    }


    private void help(){
        out.println("Press CTRL+D or type \"exit\" command to quit");
        out.println("Type \"help\" command for help");
        out.println("Supported operations: add,substract,multiply,divide,pop");
        out.println("Supported types: integer,imaginary,vector3d");
    }

    private void affichePile(){
        IObjEmp[] dump;
        synchronized(pile){
            dump=pile.dump();
        }
        
        out.println("----------------------");
        for (int i=dump.length;i>0;){
            out.println("["+i+"]"+"=>\t"+dump[--i]);
        }
        out.println("----------------------");
    }

    private ObjEmpImaginaire parseComplexPrompt(String input){
        Pattern pattern = Pattern.compile("^(?=[iI.\\d+-])([+-]?(?:\\d+(?:\\.\\d*)?|\\.\\d+)(?:[eE][+-]?\\d+)?(?![iI.\\d]))?([+-]?(?:(?:\\d+(?:\\.\\d*)?|\\.\\d+)(?:[eE][+-]?\\d+)?)?[iI])?$");
        Matcher matcher = pattern.matcher(input);

        ObjEmpImaginaire result=null;
        double real=0;
        double imaginary=0;
        String groupString;

        if (matcher.matches()){
            try{
                if ((groupString=matcher.group(1))!=null) real=Double.parseDouble(groupString);
                if ((groupString=matcher.group(2))!=null){
                    groupString=groupString.replace("+", "");
                    switch (groupString){
                        case "i":
                            imaginary=1;
                            break;
                        case "-i":
                            imaginary=-1;
                            break;
                        default:
                            groupString=groupString.replace("i", "");
                            imaginary=Double.parseDouble(groupString);
                    }
                }
                result=new ObjEmpImaginaire(real, imaginary);
            }catch(Exception e){}
        }


        return result;
    }

    private ObjEmpEntier parseIntegerPrompt(String input){
        ObjEmpEntier result=null;

        Pattern pattern=Pattern.compile("(\\d+)");
        Matcher matcher=pattern.matcher(input);
        
        if (matcher.matches()){
            try{
            result=new ObjEmpEntier(Integer.parseInt(matcher.group(1)));
            }catch(Exception e){}
        }

        return result;
    }

    private ObjEmpVecteur3D parseVector3DPrompt(String input){
        ObjEmpVecteur3D result=null;

        Pattern pattern=Pattern.compile("\\(([+-]?([0-9]*[.])?[0-9]+),([+-]?([0-9]*[.])?[0-9]+),([+-]?([0-9]*[.])?[0-9]+)\\)");

        Matcher matcher=pattern.matcher(input);
        
        if (matcher.matches()){
            try{
            result=new ObjEmpVecteur3D(Double.parseDouble(matcher.group(1)), Double.parseDouble(matcher.group(3)),Double.parseDouble(matcher.group(5)));
            }catch(Exception e){}
        }

        return result;
    }


    private void evaluer(String command){
        IObjEmp emp;

        if (command.isEmpty()) return;

        try{
            synchronized(pile){
                if((emp=parseIntegerPrompt(command))!=null){
                pile.push(emp);
                }else if ((emp=parseComplexPrompt(command))!=null){
                    pile.push(emp);
                }else if ((emp=parseVector3DPrompt(command))!=null){
                    pile.push(emp);
                }else{
                    pile.doOperation(command);
                }
            }   
        }catch(Exception e){
            out.println("An error occured: "+e.getMessage());
        }

    }
}
