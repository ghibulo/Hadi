import java.awt.Color;

    public final class BarvaHadu {
        
        private static final Color[]  barva = {Color.black, Color.blue, Color.cyan, Color.gray, Color.green, Color.yellow, Color.orange};

        static Color dejBarvu(int index) {
            return barva[index];   
        }
        
    }