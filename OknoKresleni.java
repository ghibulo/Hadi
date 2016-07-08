
import java.awt.*;
import java.util.*;
import javax.swing.*;

public class OknoKresleni extends JFrame {

    public OknoKresleni() {
       

        //setLayout(new FlowLayout());

        add(new PoleKresleni());
        setTitle("Honička");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }


    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
        
            @Override
            public void run() {
                OknoKresleni ex = new OknoKresleni();
                ex.setVisible(true);
            }
        });
    }
    
    
}