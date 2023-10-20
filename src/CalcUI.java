import java.io.InputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalcUI {
    private PileRPL pile;
    private Scanner scanner;
    private PrintStream out;


    CalcUI(PileRPL pile,InputStream in,PrintStream out){
        this.pile=pile;
        this.out=out;

        boolean quit=false;
        scanner=new Scanner(in);
        String command=null;

        out.println("Welcome to RPL Calculator");
        help();

        while(quit!=true){
            if (!pile.isEmpty()) affichePile();
            try{
                command=prompt();
                if (command.equals("exit")) throw new NoSuchElementException();
                if (command.equals("help")) help();
                else evaluer(command);
            }catch(NoSuchElementException e){
                quit=true;
            }  
        }
    }

    CalcUI(InputStream in,PrintStream out){
        this(new PileRPL(),in,out);
    }

    CalcUI(){
        this(System.in,System.out);
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

    private String prompt() throws NoSuchElementException{
        out.print("Enter a command: ");  
        return scanner.nextLine();     
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
