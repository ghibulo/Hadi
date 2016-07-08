//import java.lang.Thread.*;
import java.awt.*;
import java.util.Random;
import javax.swing.*;
import java.awt.event.*;

public class PoleKresleni extends JPanel implements ActionListener
{


    //Random rand;
    Timer timer;
    Smer nasSmer;
    Souradnice aktPozice, novaPozice;
    final static int DELAY = 100;
   
    Klavesnik kl;
    int posledniStisknutaKlavesa;
    Graphics offgc;
    Image offscreen = null;
    Dimension rozmery;

    boolean zaciname;
    
    public PoleKresleni()
    {
        

        

        
        
        //rand = new Random();
        aktPozice = new Souradnice(250,100);
        novaPozice = new Souradnice(250,100);
        nasSmer = Smer.SEVER;
        timer = new Timer(DELAY,this);
        posledniStisknutaKlavesa=0;
        kl = new Klavesnik();
        this.setFocusable(true);
        addKeyListener(kl);
        zaciname=true;
        repaint();
        timer.start();
           
    }
    

    
    private void posunHadu() {

        int x = aktPozice.sX;
        int y = aktPozice.sY;
        switch (nasSmer) {
            case SEVER: y--;break;
            case JIH: y++;break;
            case ZAPAD: x--;break;
            case VYCHOD: x++;break;
        }
        novaPozice = new Souradnice(x,y);
        
    }
    
    private void zakresliInit() {
        rozmery = this.getSize();
    
        // create the offscreen buffer and associated Graphics
        offscreen = createImage(rozmery.width, rozmery.height);
        offgc = offscreen.getGraphics();
        offgc.setColor(getBackground());
        offgc.fillRect(0, 0, rozmery.width, rozmery.height);
        offgc.setColor(getForeground());
        offgc.drawRect(10, 10, 200, 200);
    }
    
    
    
    private void zakresli() {
        offgc.drawLine(aktPozice.sX, aktPozice.sY, novaPozice.sX,novaPozice.sY);
        
    }
    
    
        // Prekryti metody paintComponent().
    protected void paintComponent(Graphics g) {

            // Vzdy je nutne nejprve zavolat metodu nadtridy.
            super.paintComponent(g);


            if (zaciname) {zakresliInit();zaciname=false;}
            g.drawImage(offscreen, 0, 0, this);
        


         }
                
    @Override
    public void actionPerformed(ActionEvent e) {

        posunHadu();
        zakresli();

        repaint();
    }


    public class Klavesnik extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent event) {
            posledniStisknutaKlavesa = event.getKeyCode();
    
           
        }

        @Override
        public void keyReleased(KeyEvent event) {
            posledniStisknutaKlavesa = 0;
        }
    }
    
    
    
    
}
