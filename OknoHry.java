
import java.awt.*;
import java.util.*;
import javax.swing.*;

public class OknoHry extends JFrame {

    public OknoHry() {
       

        //setLayout(new FlowLayout());

        PoleHry ph = new PoleHry();
        
        ph.setFocusable(true);
        ph.requestFocusInWindow();
        add(ph);
        
        setTitle("Hadi");
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }


    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
        
            @Override
            public void run() {
                OknoHry ex = new OknoHry();
                ex.setVisible(true);
            }
        });
    }
    
    
}