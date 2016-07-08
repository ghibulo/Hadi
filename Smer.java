

public enum Smer {
    SEVER, VYCHOD, JIH, ZAPAD;
    public static Smer cislo(int i) {
        return Smer.values()[i % 4];
    }
}