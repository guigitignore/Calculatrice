import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalcUI {
    private PileRPL pile;
    private Scanner scanner;

    CalcUI(int taille_pile){
        pile=new PileRPL(taille_pile);
        scanner=new Scanner(System.in);
        String command;

        while(true){
            affichePile();
            command=prompt();
            if (command.equals("exit")) break;
            evaluer(command);
        }

    }

    CalcUI(){
        this(5);
    }

    private void affichePile(){
        for (IObjEmp elt:pile.dump()){
            System.out.println("[\t\t"+elt+"\t\t]");
        }
    }

    private String prompt(){
        System.out.print("Enter a command: ");  
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
            for (int i=0;i<=matcher.groupCount();i++) System.out.println(matcher.group(i));
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
            if((emp=parseIntegerPrompt(command))!=null){
                pile.push(emp);
            }else if ((emp=parseComplexPrompt(command))!=null){
                pile.push(emp);
            }else if ((emp=parseVector3DPrompt(command))!=null){
                pile.push(emp);
            }else{
                pile.doOperation(command);
            }
        }catch(Exception e){
            System.out.println("An error occured: "+e.getMessage());
        }

    }
}
