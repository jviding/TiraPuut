import java.util.*;
public class Binaarikeko {
    private Scanner lukija = new Scanner(System.in);
    public boolean valmista = false;
    public boolean uusiLisatty;
    public Solmu head;   //Keon aloitussilmukka.
    public Binaarikeko(){     
        System.out.println("Anna lukuja binäärikekoon, 0 lopettaa:");
        int luku = lukija.nextInt();
        Solmu paa = new Solmu(luku); //Luo keon head silmukan.
        head=paa;
        if(luku!=0)
            while(valmista==false) //Looppi loppuu kun syötetään arvo 0.
                rekursio(paa);     //Rekursio muodostaa keon luetelluista arvoista.
    }
    public void pienimmanAlkionPoisto(){
        poistaPienin(head);
    }
    public void yhdenUudenAlkionLisays(){
        lisaaUusi(head);
    }
    /**
     * Pyytää lisättävän solmun arvon. Jos keko on tyhjä, niin asettaa suoraan head
     * solmun arvoksi käyttäjän antaman arvon. Laskee myös keon korkeuden ja antaa
     * sen metodin etsiEkaVapaaKohta käyttöön.
     * Jos etsiEkaVapaaKohta metodi ei löydä kohtaa johon silmukka tulee lisätä,
     * on tällöin alin kerros täydellinen ja lisättävä silmukka aloittaa seuraavan kerroksen.
     * @param solmu
     */
    public void lisaaUusi(Solmu solmu){
        System.out.print("Anna lisättävän solmun arvo: ");
        if(solmu.arvo==0){
            solmu.arvo=lukija.nextInt();
            return;
        }
        int korkeus=0;
        while(solmu.vasenLapsi!=null){
            solmu=solmu.vasenLapsi;
            korkeus+=1;
        }
        etsiEkaVapaaKohta(solmu, korkeus, korkeus);
        if(uusiLisatty==false){
            asetaLapsi(solmu, lukija.nextInt());
        }
        uusiLisatty=false;
    }
    /**
     * Käy rekursiivisesti silmukoita vasemmalta alhaalta kohti oikeaa reunaa.
     * Käyttää apunaan tietoa alimman rivin korkeudesta.
     * Jos löytää ensimmäisen vapaan kohdan solmulle, suorittaa metodin asetaLapsi,
     * ja lopettaa suorituksen asettamalla parametrin uusiLisatty true arvoon.
     * Jos ei löydä, niin alin rivi on täydellinen.
     * @param solmu
     * @param korkeus Jos silmukalla ei ole lapsia ja sen korkeus on rajaa pienempi,
     * niin seuraava silmukka asetetaan sen lapseksi.
     * @param raja
     */
    public void etsiEkaVapaaKohta(Solmu solmu, int korkeus, int raja){
        if(uusiLisatty==true)
            return;
        if(solmu.vasenLapsi==null && korkeus<raja){
            uusiLisatty=true;
            asetaLapsi(solmu, lukija.nextInt());
        }
        while(solmu.vasenLapsi!=null && uusiLisatty==false){
            solmu=solmu.vasenLapsi;
        }
        if(solmu.vanhempi!=null && solmu.vanhempi.oikeaLapsi==null){
            uusiLisatty=true;
            asetaLapsi(solmu.vanhempi, lukija.nextInt());
        }
        while(solmu.vanhempi!=null && solmu.vanhempi.oikeaLapsi!=solmu && uusiLisatty==false){
            solmu=solmu.vanhempi;
            etsiEkaVapaaKohta(solmu.oikeaLapsi, korkeus, raja);
            korkeus-=1;
        }
    }
    /**
     * Jos keossa on enää head solmu jäljellä, muuntaa tämän arvon nollaksi ja
     * ilmoittaa poiston. Jos keon head solmun arvo jo nolla, niin ilmoittaa keon
     * olevan jo tyhjä.
     * @param solmu
     * @return
     */
    public boolean onkoJoTyhja(Solmu solmu){ 
        if(solmu.vasenLapsi==null && solmu.oikeaLapsi==null){
            if(solmu.arvo!=0)
                System.out.println("Poistettu: "+solmu.arvo);
            else
                System.out.println("Puu on jo tyhjä!");
            solmu.arvo=0;
            return true;
        }
        return false;
    }
    /**
     * Tarkistaa onko keko jo tyhjä. Jos ei ole, niin keon viimeisen solmun arvo
     * siirretään head solmun päälle. Poistaa keon viimeisen solmun, josta on tullut
     * nyt head solmu, keon lopusta. Tämän jälkeen järjestää solmut oikeaan järjestykseen.
     * @param solmu
     */
    public void poistaPienin(Solmu solmu){
        if(onkoJoTyhja(solmu)==true)
           return;
        System.out.print("Poistettava: "+solmu.arvo);
        Solmu paa = solmu;
        solmu = viimeinenSolmu(solmu);
        System.out.println(" Viiminen: "+solmu.arvo);
        paa.arvo=solmu.arvo;
        tuhoaViimeinen(solmu);
        jarjestaSolmut(paa);
    }
    public Solmu viimeinenSolmu(Solmu solmu){ //Palauttaa arvonaan viimeisen solmun.
        Solmu vika = etsiViimeinen(solmu);
        if(vika==null){                       //Jos null, niin keon alin rivi on täydellinen
            while(solmu.oikeaLapsi!=null)     //  ja viimeinen silmukka siis tämän oikeanpuoleisin.
                solmu=solmu.oikeaLapsi;
            return solmu;
        }
        return vika;
    }
    public void tuhoaViimeinen(Solmu solmu){ //Poistaa solmun puusta.
        if(solmu.vanhempi.vasenLapsi==solmu)
            solmu.vanhempi.vasenLapsi=null;
        else
            solmu.vanhempi.oikeaLapsi=null;
    }
    /**
     * Vertaa solmua lapsiensa arvoihin ja tarvittaessa vaihtaa solmut oikein päin.
     * Jos vaihto tapahtuu, niin tekee saman vertailun samalla solmulla uusien lasten suhteen.
     * @param solmu
     */
    public void jarjestaSolmut(Solmu solmu){
        if(solmu.vasenLapsi!=null && (solmu.vasenLapsi.arvo<solmu.arvo || (solmu.oikeaLapsi!=null && solmu.oikeaLapsi.arvo<solmu.arvo))){
            if(solmu.oikeaLapsi==null || solmu.vasenLapsi.arvo<=solmu.oikeaLapsi.arvo){
                int apuEka = solmu.arvo;
                solmu.arvo = solmu.vasenLapsi.arvo;
                solmu.vasenLapsi.arvo = apuEka;
                jarjestaSolmut(solmu.vasenLapsi);
            }
            else{
                int apuToka = solmu.arvo;
                solmu.arvo = solmu.oikeaLapsi.arvo;
                solmu.oikeaLapsi.arvo = apuToka;
                jarjestaSolmut(solmu.oikeaLapsi);
            }
        }
    }
    public Solmu etsiViimeinen(Solmu solmu){ //Laskee keon korkeuden ja antaa sen viimeinenSolmu metodin käyttöön.
        int korkeus = 0;
        while(solmu.oikeaLapsi!=null){
            korkeus+=1;
            solmu=solmu.oikeaLapsi;
        }
        return viimeinenSolmu(korkeus, solmu, korkeus);        
    }
    /**
     * Etsii rekursiivisesti keon viimeistä solmua. Käyttää apunaan tietoa keon alimman täyden
     * rivin korkeudesta. Aloittaa tutkimisen keon alimman täydellisen rivin oikeasta reunasta ja
     * käy silmukoita läpi yksi kerrallaan kohti vasenta reunaa. Palauttaa arvonaan null, jos
     * alin rivi onkin täydellinen ja poistettava alimman rivin oikeanpuoleisin.
     * @param korkeus Alimman täydellisen rivin korkeus.
     * @param solmu
     * @param raja Jos korkeus on suurempi kuin raja, niin silmukka on alimmalla rivillä.
     * @return Palauttaa arvonaan keon viimeisen silmukan.
     */
    public Solmu viimeinenSolmu(int korkeus, Solmu solmu, int raja){
        while(solmu.oikeaLapsi!=null){
            korkeus+=1;
            solmu=solmu.oikeaLapsi;
            if(korkeus>raja)
                return solmu;
        }
        if(solmu.vasenLapsi!=null && solmu.oikeaLapsi==null)
            return solmu.vasenLapsi;
        while(solmu.vanhempi!=null && solmu.vanhempi.vasenLapsi!=solmu){
            solmu=solmu.vanhempi;
            Solmu loydetty = viimeinenSolmu(korkeus, solmu.vasenLapsi, raja);
            if(loydetty!=null)
                return loydetty;
            korkeus-=1;
        }
        return null;
    }
    /**
     * Lisää kekoon solmuja asetaLapsi(solmu) metodin avulla ja kutsuu itseään
     * rekursiivisesti kunnes käyttäjä syöttää arvon 0 ja keon rakentaminen lopetetaan.
     * @param solmu
     */
    public void rekursio(Solmu solmu){
        while(solmu.vasenLapsi!=null)
            solmu=solmu.vasenLapsi;
        if(valmista==false)
            asetaLapsi(solmu, lukija.nextInt());
        if(valmista==false)
            asetaLapsi(solmu, lukija.nextInt());
        while(solmu.vanhempi!=null && solmu.vanhempi.oikeaLapsi!=solmu){
            solmu=solmu.vanhempi;
            rekursio(solmu.oikeaLapsi);
            }
    }
    /**
     * Kysyy käyttäjältä arvon uudelle solmulle ja asettaa sen paikoilleen.
     * Tarkistaa myös vaatiiko uuden solmun asettaminen mahdollisia uudelleenjärjestelyitä keon rakenteessa
     * ja tarpeen tullen kutsuu metodia solmuvertailu(solmu).
     * @param solmu Se solmu jolle ollaan asettamassa uutta lasta.
     */
    public void asetaLapsi(Solmu solmu, int luku){
        if(luku!=0){
            if(solmu.vasenLapsi==null){
                solmu.asetaVasenLapsi(new Solmu(luku));
                solmu.vasenLapsi.vanhempi=solmu;
            }
            else{
                solmu.asetaOikeaLapsi(new Solmu(luku));
                solmu.oikeaLapsi.vanhempi=solmu;
            }
            if(solmu.vasenLapsi.arvo<solmu.arvo || (solmu.oikeaLapsi!=null && solmu.oikeaLapsi.arvo<solmu.arvo))
                solmuvertailu(solmu);
        }
        else
            kekoValmis();
    }
    public void kekoValmis(){ //Ilmoittaa, että keko on nyt saatu valmiiksi ja sitä voidaan alkaa käsittelemään.
        valmista = true;
    }
    /**
     * Metodia kutsutaan jos toinen lapsista on pienempi kuin vanhempansa.
     * Metodi vaihtaa pienemmän lapsen arvon vanhemman arvon kanssa ristiin.
     * @param solmu
     */
    public void solmuvertailu(Solmu solmu){   
        if(solmu.vasenLapsi.arvo<solmu.arvo && (solmu.oikeaLapsi==null ||
                solmu.vasenLapsi.arvo<=solmu.oikeaLapsi.arvo)){
            int apuArvoEka = solmu.arvo;
            solmu.arvo=solmu.vasenLapsi.arvo;
            solmu.vasenLapsi.arvo=apuArvoEka;
            if(solmu.vanhempi!=null)
                solmuvertailu(solmu.vanhempi);
        }
        else{
            if(solmu.oikeaLapsi!=null && solmu.oikeaLapsi.arvo<solmu.arvo){
                int apuArvoToka = solmu.arvo;
                solmu.arvo=solmu.oikeaLapsi.arvo;
                solmu.oikeaLapsi.arvo=apuArvoToka;
                if(solmu.vanhempi!=null)
                    solmuvertailu(solmu.vanhempi);
            }
        }
    }
}
