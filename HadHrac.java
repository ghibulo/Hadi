import java.awt.event.*;
import java.awt.Color;

public class HadHrac extends Had
{


  public HadHrac(int startPoziceX, int startPoziceY, PoleHry pole, Color barva)
    {
        super(startPoziceX, startPoziceY, pole,  barva);
        
        
    }
    
    
   public Smer hrej() {
        Smer kamjit = Smer.SEVER;
        System.out.println(herniPole.posledniStisknutaKlavesa);
        switch (herniPole.posledniStisknutaKlavesa) {
            case KeyEvent.VK_LEFT: kamjit = Smer.ZAPAD;break;
            case KeyEvent.VK_RIGHT: kamjit = Smer.VYCHOD;break;
            case KeyEvent.VK_UP: kamjit = Smer.SEVER;break;
            case KeyEvent.VK_DOWN: kamjit = Smer.JIH;break;
        }
        
         return kamjit;   
    }
}
