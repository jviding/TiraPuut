
public class Solmu {
    public int arvo;
    public Solmu oikeaLapsi;
    public Solmu vasenLapsi;
    public Solmu vanhempi;
    public Solmu(int luku){
        arvo=luku;
    }
    public void asetaOikeaLapsi(Solmu solmu){
        oikeaLapsi=solmu;
    }
    public void asetaVasenLapsi(Solmu solmu){
        vasenLapsi=solmu;
    }
    public void asetaVanhempi(Solmu solmu){
        vanhempi = solmu;
    }
}
