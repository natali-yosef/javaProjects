import java.util.Scanner;
import java.util.ArrayList;
class NonNullArrayList<E> extends ArrayList<E>{
    public NonNullArrayList(){
        super();
    }
    @Override
    public boolean add(E a){
        if (a!=null){
            return super.add(a);
        }
        return false;
    }
}

//class Chat {
//    public static void main(String[] args) {
//
//        ChatterBot[] botsArr = new ChatterBot[2];
//        botsArr[0] = new ChatterBot("Kermit", new String[] {ChatterBot.REQUESTED_PHRASE_PLACEHOLDER +" okay: "+ChatterBot.REQUESTED_PHRASE_PLACEHOLDER } ,new String[] {"what ", "say I should say ", ChatterBot.ILLEGAL_REQUEST_PLACEHOLDER + " ha? say say" });
//        botsArr[1] = new ChatterBot("Ruthy", new String[] {"say "+ ChatterBot.REQUESTED_PHRASE_PLACEHOLDER +"? why?? okay: "+ChatterBot.REQUESTED_PHRASE_PLACEHOLDER } ,new String[] {"whaaat ", "say say "});
//
//        Scanner scanner = new Scanner(System.in);
//        String statement = scanner.nextLine();
//
//        while (true){
//            for(ChatterBot bot : botsArr){
//                statement = bot.replyTo(statement);
//                System.out.print(bot.getName() + ": " + statement);
//                scanner.nextLine();
//            }
//
//        }

//    }
//}



