import java.lang.reflect.Array;
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

   private  ArrayList<Savas_Araclari> insanKart = new ArrayList<>();
   private  ArrayList<Savas_Araclari> bilgisayarKart = new ArrayList<>();
   private ArrayList<Integer> kullanilmisSayilar = new ArrayList<>();

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

    public void setSkor(int skor) {
        this.skor = skor;
    }


    public void insanKartListesi(){
        for(int i=0;i<3;i++){
            insanKart.add(ilkAtamalar());
        }
        int i=0;
        System.out.println("Oyuncu kart listesi");
        System.out.println("İnsan kart listesi");
        while (i<insanKart.size()) {
            System.out.println(insanKart.get(i).getKartID());
            i++;
        }
    }
    public void bilgisyarKartListesi(){
        for(int i=0;i<3;i++){
            bilgisayarKart.add(ilkAtamalar());
        }
        int i=0;
        System.out.println("Oyuncu kart listesi");
        System.out.println("pc kart listesi");
        while (i<bilgisayarKart.size()) {
            System.out.println(bilgisayarKart.get(i).getKartID());
            i++;
        }
    }

    public Savas_Araclari ilkAtamalar(){
            Random rand = new Random();
            int sinir=rand.nextInt(3);
        System.out.println("sinir:"+sinir);
                return switch (sinir){
                    case 0->(new Ucak());
                    case 1->(new Obus());
                    case 2->(new Firkateyn());
                    default -> throw new IllegalStateException("Unexpected value: " + sinir);
                };
    }

//    public Savas_Araclari kartAtama(Oyuncu oyuncu){
//
//    }


    public ArrayList<Savas_Araclari> getInsanKart() {
        return insanKart;
    }

    public ArrayList<Savas_Araclari> getBilgisayarKart() {
        return bilgisayarKart;
    }

    public  int kartSec(Oyuncu oyuncu){
        if(oyuncu.oyuncu_adi.equals("Bilgisayar")){
            Random rand = new Random();
            int secim;

            if (kullanilmisSayilar.size() >= 3) {
                kullanilmisSayilar.clear();
            }

            do {
                secim = rand.nextInt(3);
            } while (kullanilmisSayilar.contains(secim));

            kullanilmisSayilar.add(secim);
            return secim;
        }else{
            Scanner sc = new Scanner(System.in);
            int secim= sc.nextInt();
            return secim;
        }
    }

}
