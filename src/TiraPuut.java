
import java.util.Scanner;


public class TiraPuut {
    private static Scanner lukija = new Scanner(System.in);

    public static void main(String[] args) {
        Binaarikeko keko;

        System.out.println("Valitse muodostettava keko:");
        System.out.println("1.Binäärikeko");

        System.out.print("Valinta: ");
        int valinta = lukija.nextInt();

        if(valinta==1){
            keko = new Binaarikeko();
            while(valinta!=3){
                System.out.println("--------Valinnat--------\n1. Pienimmän alkion poisto.\n"
                         + "2. Yhden uuden alkion lisäys.\n3. Poistu.");
                System.out.print("Valinta: ");
                valinta = lukija.nextInt();
                if(valinta==1)
                    keko.pienimmanAlkionPoisto();
                if(valinta==2)
                    keko.yhdenUudenAlkionLisays();
            }
        }


    }

}
