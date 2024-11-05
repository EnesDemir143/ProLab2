import java.util.ArrayList;
import java.util.Random;

public class Oyun {
    public static void main(String[] args) {

        Oyuncu insan = new Oyuncu("123","Enes",0);
        Oyuncu pc = new Oyuncu();
        pc.kartSec(insan);
        pc.kartListesi();


    }
}
