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

    private void evaluer(String command){
        String regex = "([-+]?(\\d*\\.?\\d+)?)([ij])";
        
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(command);
        
        if (matcher.find()) {
            System.out.println("match");
            try{
                int realPart = Integer.parseInt(matcher.group(0));
                System.out.println("scan "+realPart);
                int imaginaryPart = Integer.parseInt(matcher.group(1));
                System.out.println("scan "+imaginaryPart);

            }catch(Exception e){}
            
        }else{
            
        }

    }
}
