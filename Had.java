import java.awt.Point;
import java.awt.Color;
import java.util.*;

public class Had
{
    // instance variables - replace the example below with your own
    public Souradnice pozice;
    public int delka;
    protected PoleHry herniPole;
    public Color mojeBarva;
    public int mod;
    public final static int NARUST = 1;
    public final static int POSUN = 2;
    public final static int MRTEV = 3;

    protected Random rand;
    private Souradnice nejSouradnicePotravy;
    private double vzdalenostNejPotravy;

    
    
    
    public Had(int startPoziceX, int startPoziceY, PoleHry pole, Color barva)
    {
        delka = 1;
        rand = new Random();
        nastavPozici(startPoziceX, startPoziceY);
        herniPole = pole;
        mojeBarva = barva;
        mod = 0;
        
        
    }
    
    
    public void nastavPozici(int jakouX, int jakouY) {
        pozice = new Souradnice(jakouX,jakouY);
    }
    
    public void nastavPozici(Souradnice jakou) {
        pozice = jakou;
    }
    
    private void SouradniceVyhodnotVzdalenost(int kamx, int kamy) {
        double vzd = Math.sqrt((kamx-pozice.sX)*(kamx-pozice.sX)+(kamy-pozice.sY)*(kamy-pozice.sY));
        if (vzd<vzdalenostNejPotravy) {
            nejSouradnicePotravy = new Souradnice(kamx,kamy);
            vzdalenostNejPotravy=vzd;
        }
    }
    
    private void hledejNejblizsiPotravu() {
              vzdalenostNejPotravy=1000;
              for(int i=0; i<herniPole.POCET_SLOUPCU; i++)
                for(int j=0; j<herniPole.POCET_RADKU; j++) {
                        if (herniPole.planSituace[i][j]==herniPole.POTRAVA) {
                            SouradniceVyhodnotVzdalenost(i,j);
                        }
                       
                }     
        
    }
    
    public Smer hrej() {
        Smer kamjit = Smer.SEVER;
        Souradnice kampujdu;
        hledejNejblizsiPotravu();
        if (nejSouradnicePotravy.sX>pozice.sX) {
            kamjit = Smer.VYCHOD;
            kampujdu = herniPole.dejSouradnice(pozice,kamjit);
            if (herniPole.dejPoleSituace(kampujdu)<=0) return kamjit;
        }
            
            
        if (nejSouradnicePotravy.sX<pozice.sX) {
          kamjit = Smer.ZAPAD;
          kampujdu = herniPole.dejSouradnice(pozice,kamjit);
          if (herniPole.dejPoleSituace(kampujdu)<=0) return kamjit;
        }
          
        if (nejSouradnicePotravy.sY<pozice.sY) {
            kamjit = Smer.SEVER;
            kampujdu = herniPole.dejSouradnice(pozice,kamjit);
            if (herniPole.dejPoleSituace(kampujdu)<=0) return kamjit;
        }
            
            
        if (nejSouradnicePotravy.sY>pozice.sY) {
            kamjit = Smer.JIH;
            kampujdu = herniPole.dejSouradnice(pozice,kamjit);
          if (herniPole.dejPoleSituace(kampujdu)<=0) return kamjit;
        }
            
        for (int i=0;i<4;i++) {
            kamjit = Smer.cislo(i);
            kampujdu = herniPole.dejSouradnice(pozice,kamjit);
            if (herniPole.dejPoleSituace(kampujdu)<=0) return kamjit;
        }
        
     return kamjit;   
    }
}
