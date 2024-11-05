import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Oyuncu {
    private String oyuncu_id;
    private String oyuncu_adi;
    private int skor;

    Ucak ucak;
    Siha siha;
    Firkateyn firkateyn;
    Sida sida;
    KFS kfs;
    Obus obus;

    List<Savas_Araclari> insanKart = new ArrayList<>();
    List<Savas_Araclari> bilgisayarKart = new ArrayList<>();

    public Oyuncu() {
        oyuncu_id = "0";
        oyuncu_adi = "Bilgisayar";
        skor = 0;
    }
    public Oyuncu(String oyuncu_id, String oyuncu_adi,int skor) {
        this.oyuncu_id = oyuncu_id;
        this.oyuncu_adi = oyuncu_adi;
        this.skor = skor;
    }

    public int getSkor() {
        return skor;
    }


    public void kartListesi(){
        int i=0;
        System.out.println("Oyuncu kart listesi");
        System.out.println("İnsan kart listesi");
        while (i<insanKart.size()) {
            System.out.println(insanKart.get(i).getKartID());
            System.out.println(insanKart.get(i).getSinif());
            i++;
        }
        i=0;
        System.out.println("Bilgisayar kart listesi");
        while (i<bilgisayarKart.size()) {
            System.out.println(insanKart.get(i).getKartID());
            System.out.println(insanKart.get(i).getSinif());
            i++;
        }
    }

    public Savas_Araclari ilkAtamalar(){
            Random rand = new Random();
            int sinir=rand.nextInt(3);
                return switch (sinir){
                    case 0->(new Ucak());
                    case 1->(new Obus());
                    case 2->(new Firkateyn());
                    default -> throw new IllegalStateException("Unexpected value: " + sinir);
                };
    }

    public Savas_Araclari kartAtama(Oyuncu oyuncu){
        if(oyuncu.oyuncu_adi.equals("Bilgisayar")){
            Random rand = new Random();
            int sinir=rand.nextInt(6);
            return switch (sinir){
                case 0->(new Ucak());
                case 1->(new Obus());
                case 2->(new Firkateyn());
                case 3->(new Siha());
                case 4->(new KFS());
                case 5->(new Sida());
                default -> throw new IllegalArgumentException("Yanlış sayı");
            };
        }else{
            Scanner sc = new Scanner(System.in);
            int secim= sc.nextInt();
            return switch (secim){
                case 0->(new Ucak());
                case 1->(new Obus());
                case 2->(new Firkateyn());
                case 3->(new Siha());
                case 4->(new KFS());
                case 5->(new Sida());
                default -> throw new IllegalArgumentException("Yanlış sayı");
            };

        }
    }

    public void kartSec(Oyuncu oyuncu){
        for(int i=0;i<3;i++){
            insanKart.add(ilkAtamalar());
        }
        for(int i=0;i<3;i++){
            bilgisayarKart.add(ilkAtamalar());
        }

    }

}
