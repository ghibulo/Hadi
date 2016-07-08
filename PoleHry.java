//import java.lang.Thread.*;
import java.awt.*;
import java.util.Random;
import javax.swing.*;
import java.awt.event.*;

public class PoleHry extends JPanel implements ActionListener
{

    public int[][] planSituace;
    public int[][]  planHracu;
    final static int POCET_SLOUPCU = 50;
    final static int POCET_RADKU = 30;
    final static int VELIKOST_POLE = 20; //pocet pixelu
    final static int LEVY_OKRAJ = 10;
    final static int HORNI_OKRAJ = 10; 
    final static int MAX_HADU = 5;
    final static int DELAY = 100;
    final static Color BARVA_POTRAVY = Color.red;
    final static Color BARVA_NIC = Color.white;
    final static int ZADNY_HRAC = -1;
    final static int POTRAVA = -1;
    final static int NIC = 0;
    int pocetHadu, odpocet;
    Random rand;
    Timer timer;
    
    Had[] poleHadu;
    Klavesnik kl;
    int posledniStisknutaKlavesa;

    public PoleHry()
    {
        planSituace  = new int[POCET_SLOUPCU][POCET_RADKU];
        planHracu  = new int[POCET_SLOUPCU][POCET_RADKU];
        vynulujPole();
        //setSize(new Dimension(2*LEVY_OKRAJ+VELIKOST_POLE*POCET_SLOUPCU+50,2*HORNI_OKRAJ+VELIKOST_POLE*POCET_RADKU+50 ));
        rand = new Random();
        
        poleHadu = new Had[MAX_HADU];
        poleHadu[0] = new Had(10,5, this,BarvaHadu.dejBarvu(0));
        poleHadu[1] = new Had(20,5, this,BarvaHadu.dejBarvu(1));

        poleHadu[2] = new HadHrac(20,5, this,BarvaHadu.dejBarvu(2));
        odpocet=pocetHadu=3;
        umistiHady();
        umistitPotravu(35);
        timer = new Timer(DELAY,this);
        posledniStisknutaKlavesa=0;
        kl = new Klavesnik();
        setFocusable(true);
        //requestFocusInWindow();
        this.addKeyListener(kl);
        repaint();
        timer.start();
           
    }
    
    // kvuli prijeti zpravy o stisku
public boolean isFocusTraversable ( ) {
return true ;
}
    
    private void umistiHady() {
        
        for (int i=0;i<pocetHadu;i++) {
            planSituace[poleHadu[i].pozice.sX][poleHadu[i].pozice.sY]=1;
            planHracu[poleHadu[i].pozice.sX][poleHadu[i].pozice.sY]=i;
        }
            
    }
    
    private void vynulujPole() {
        for(int i=0; i<POCET_SLOUPCU; i++)
                for(int j=0; j<POCET_RADKU; j++) {
                        planSituace[i][j]=NIC;planHracu[i][j]=ZADNY_HRAC;
                       
                }             
        
    }
    
    
    private void umistitPotravu(int pocet) {
        int x,y;
        for (int i=0;i<pocet;i++) {
            do {
                 x = rand.nextInt(POCET_SLOUPCU);
                 y = rand.nextInt(POCET_RADKU);
                 //System.out.println("x="+x+", y="+y);
            } while (planSituace[x][y]!=0);
            planSituace[x][y] = POTRAVA;
        }
        
    }
    
    private int prevodX(int psloupec) {
        return LEVY_OKRAJ+psloupec*VELIKOST_POLE;
    }
    
    private int prevodY(int pradek) {
        return HORNI_OKRAJ+pradek*VELIKOST_POLE;
    }
    
    private void vykresliOval(Graphics g,int x, int y, Color barva) {
        
        g.setColor(barva);
        g.fillOval(x=prevodX(x), y=prevodY(y), VELIKOST_POLE, VELIKOST_POLE);
    }
    
    private void nakresliPole(Graphics g) {
        int x, y, x2, y2;


        int xmin = LEVY_OKRAJ;
        int ymin = HORNI_OKRAJ;
        int xmax = xmin + POCET_SLOUPCU*VELIKOST_POLE;
        int ymax = ymin + POCET_RADKU*VELIKOST_POLE;

        
        Graphics offgc;
        Image offscreen = null;
        Dimension d = this.getSize();
    
        // create the offscreen buffer and associated Graphics
        offscreen = createImage(d.width, d.height);
        offgc = offscreen.getGraphics();
        // clear the exposed area
        offgc.setColor(getBackground());
        offgc.fillRect(0, 0, d.width, d.height);
        offgc.setColor(getForeground());
        // do normal redraw
        
        offgc.drawLine(xmin,ymin, xmax, ymin);
        offgc.drawLine(xmax,ymin, xmax, ymax);
        offgc.drawLine(xmax, ymax, xmin, ymax);
        offgc.drawLine(xmin, ymax, xmin, ymin);
        
        
        for(int i=0; i<POCET_SLOUPCU; i++)
            for(int j=0; j<POCET_RADKU; j++) {
                    if (planSituace[i][j]==POTRAVA) vykresliOval(offgc, i,j, BARVA_POTRAVY); 
                    if (planSituace[i][j]>0) vykresliOval(offgc,i,j, BarvaHadu.dejBarvu(planHracu[i][j]));
            }      
        
        
        
        // transfer offscreen to window
        g.drawImage(offscreen, 0, 0, this);
        
        
        
        
    }

    public Souradnice dejSouradnice (Souradnice pozice, Smer smer) {
        int x = pozice.sX;
        int y = pozice.sY;
        
        switch (smer) {
            case SEVER: y--;break;
            case JIH: y++;break;
            case ZAPAD: x--;break;
            case VYCHOD: x++;break;
        }
        if (y<0) y+=POCET_RADKU; else
        if (x<0) x+=POCET_SLOUPCU; else
        if (x==POCET_SLOUPCU) x=0; else
        if (y==POCET_RADKU) y=0;
        
        //System.out.println("sx="+x+" sy="+y);
        return new Souradnice(x,y);
        
    }
    
    
    public int dejPoleSituace(Souradnice odkud) {
        //System.out.println("sx="+odkud.sX+" sy="+odkud.sY);
        return planSituace[odkud.sX][odkud.sY];
    }
    
    
    
    private void aktualizujPole(int pocetNovePotravy) {
             for(int i=0; i<POCET_SLOUPCU; i++)
                for(int j=0; j<POCET_RADKU; j++) {
                        
                    if (planSituace[i][j]>NIC) {
                        switch (poleHadu[planHracu[i][j]].mod) {
                            case Had.NARUST: break;
                            case Had.POSUN: if ((--planSituace[i][j])==0) planHracu[i][j]=ZADNY_HRAC;break;
                            case Had.MRTEV: planSituace[i][j]=NIC;planHracu[i][j]=ZADNY_HRAC;break;
                            
                        }
                    }
                }
             umistitPotravu(pocetNovePotravy);   
        
    }
    
    
    private void posunHadu() {
        Smer kam;
        Souradnice novaPozice;
        int pocetNovePotravy=0;

        for (int i=0;i<pocetHadu;i++) {
            if (poleHadu[i].mod!=Had.MRTEV) {
                kam = poleHadu[i].hrej();
                novaPozice = dejSouradnice(poleHadu[i].pozice,kam);
                if (dejPoleSituace(novaPozice)==POTRAVA) {
                    poleHadu[i].mod = Had.NARUST;
                    planSituace[novaPozice.sX][novaPozice.sY] = ++(poleHadu[i].delka);
                    planHracu[novaPozice.sX][novaPozice.sY] = i;
                    pocetNovePotravy++;
                } else
                if (dejPoleSituace(novaPozice)==NIC) {
                    poleHadu[i].mod = Had.POSUN;
                    planSituace[novaPozice.sX][novaPozice.sY] = poleHadu[i].delka+1;
                    planHracu[novaPozice.sX][novaPozice.sY] = i;
                } else { //nejaky had
                    poleHadu[i].mod = Had.MRTEV;odpocet--;
                }   
                poleHadu[i].nastavPozici(novaPozice);
            }
        }
        if (odpocet==0) System.exit(0);
        //System.out.println(posledniStisknutaKlavesa);
        aktualizujPole(pocetNovePotravy);

        
    }
    
        // Prekryti metody paintComponent().
    protected void paintComponent(Graphics g) {

            // Vzdy je nutne nejprve zavolat metodu nadtridy.
            super.paintComponent(g);
           
            nakresliPole(g);

         }
                
    @Override
    public void actionPerformed(ActionEvent e) {

        posunHadu();

        repaint();
    }


    public class Klavesnik extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent event) {
            posledniStisknutaKlavesa = event.getKeyCode();
    
           
        }

        @Override
        public void keyReleased(KeyEvent event) {
            System.out.println("uvolnena klavesa");
        }
    }
    
    
    
    
}
