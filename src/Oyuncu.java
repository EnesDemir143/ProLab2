import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Oyuncu {
    private String oyuncu_id;
    private String oyuncu_adi;
    private int insanSkor;
    private int pcSkor;

   private  ArrayList<Savas_Araclari> insanKart = new ArrayList<>();
   private  ArrayList<Savas_Araclari> bilgisayarKart = new ArrayList<>();
   private ArrayList<Integer> kullanilmisSayilar = new ArrayList<>();

    public Oyuncu() {
        oyuncu_id = "0";
        oyuncu_adi = "Bilgisayar";
        pcSkor = 0;
    }
    public Oyuncu(String oyuncu_id, String oyuncu_adi,int insanSkor) {
        this.oyuncu_id = oyuncu_id;
        this.oyuncu_adi = oyuncu_adi;
        this.insanSkor = insanSkor;
    }

    public void setInsanSkor(int insanSkor) {
        this.insanSkor += insanSkor;
    }

    public void setPcSkor(int pcSkor) {
        this.pcSkor += pcSkor;
    }

    public int getInsanSkor() {
        return insanSkor;
    }

    public int getPcSkor() {
        return pcSkor;
    }

    public void insanKartListesi(Oyuncu insan){
        for(int i=0;i<3;i++){
            insanKart.add(kartEkleme(insan));
        }
        int i=0;
        System.out.println("Oyuncu kart listesi");
        System.out.println("İnsan kart listesi");
        while (i<insanKart.size()) {
            System.out.println(insanKart.get(i).getKartID());
            i++;
        }
    }
    public void bilgisyarKartListesi(Oyuncu bilgisayar){
        for(int i=0;i<3;i++){
            bilgisayarKart.add(kartEkleme(bilgisayar));
        }
        int i=0;
        System.out.println("Oyuncu kart listesi");
        System.out.println("pc kart listesi");
        while (i<bilgisayarKart.size()) {
            System.out.println(bilgisayarKart.get(i).getKartID());
            i++;
        }
    }

    public Savas_Araclari kartEkleme(Oyuncu oyuncu){
        Random rand = new Random();
        int sinir;

        if((oyuncu.oyuncu_adi.equals("Bilgisayar") && oyuncu.pcSkor < 20) ||
                (!oyuncu.oyuncu_adi.equals("Bilgisayar") && oyuncu.insanSkor < 20)) {
            // 20 puandan az ise sadece ilk 3 karttan birini seç
            sinir = rand.nextInt(3);
        } else {
            // 20 puan ve üzeri ise tüm kartlardan birini seç
            sinir = rand.nextInt(6);
        }
       // System.out.println("Kart ekleniyor....");
        return switch (sinir) {
            case 0 -> new Ucak();
            case 1 -> new Obus();
            case 2 -> new Firkateyn();
            case 3 -> new Sida();
            case 4 -> new KFS();
            case 5 -> new Siha();
            default -> throw new IllegalStateException("Unexpected value: " + sinir);
        };
    }


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
                secim = rand.nextInt(oyuncu.getBilgisayarKart().size());
            } while (kullanilmisSayilar.contains(secim));

            kullanilmisSayilar.add(secim);
            return secim;
        }else{
            Scanner sc = new Scanner(System.in);
            System.out.print("KART SEÇİN:");
            int secim= sc.nextInt();
            return secim;
        }
    }

    public static void kartSavaslari(Oyuncu insan,Oyuncu pc,ArrayList<Savas_Araclari> insanseckart, ArrayList<Savas_Araclari> pcSeckart) {
        //insanın saldırısı
        for(int i = 0; i < 3; i++) {
            if(insanseckart.get(i) instanceof EkstraVurusOzellikleri ekstralar) {
                int hasar = insanseckart.get(i).getVurus(); // Temel vuruş hasarı
                // Rakip kartın sınıfına göre avantaj hesaplama
                String rakipSinif = pcSeckart.get(i).getSinif();
                if(!insanseckart.get(i).getSinif().trim().equals(rakipSinif.trim())) {
                    switch (rakipSinif) {
                        case "Kara" -> hasar += ekstralar.getKara_vurus_avantaji();
                        case "Hava" -> hasar += ekstralar.getHava_vurus_avantaji();
                        case "Deniz" -> hasar += ekstralar.getDeniz_vurus_avantaji();
                    }
                }
                pcSeckart.get(i).durumGuncelle(hasar);
                if(pcSeckart.get(i).getDayaniklilik()==0){
                    pc.getBilgisayarKart().remove(pcSeckart.get(i));
                    if (pcSeckart.get(i).getSeviye_puani()==0){
                        insan.setInsanSkor(10);
                    }else if (pcSeckart.get(i).getSeviye_puani()==20){
                        insan.setInsanSkor(20);
                    }
                }
            }
            // PC'nin saldırısı
            if(pcSeckart.get(i) instanceof EkstraVurusOzellikleri ekstralar) {
                int hasar = pcSeckart.get(i).getVurus(); // Temel vuruş hasarı

                // Rakip kartın sınıfına göre avantaj hesaplama
                String rakipSinif = insanseckart.get(i).getSinif();
                if(!pcSeckart.get(i).getSinif().trim().equals(rakipSinif.trim())) {
                    switch (rakipSinif) {
                        case "Kara" -> hasar += ekstralar.getKara_vurus_avantaji();
                        case "Hava" -> hasar += ekstralar.getHava_vurus_avantaji();
                        case "Deniz" -> hasar += ekstralar.getDeniz_vurus_avantaji();
                    }
                }
                insanseckart.get(i).durumGuncelle(hasar);
                if(insanseckart.get(i).getDayaniklilik()==0){
                    insan.getInsanKart().remove(insanseckart.get(i));
                    if (insanseckart.get(i).getSeviye_puani()==0){
                        pc.setPcSkor(10);
                    }else if (insanseckart.get(i).getSeviye_puani()==20){
                        pc.setPcSkor(20);
                    }
                }
            }
        }
        insan.getInsanKart().add(insan.kartEkleme(insan));
        pc.getBilgisayarKart().add(pc.kartEkleme(pc));
    }
    public static void secilenKartlar(Oyuncu insan,Oyuncu pc,ArrayList<Savas_Araclari> insanseckart,ArrayList<Savas_Araclari> pcSeckart) {
        for(int i=0;i<3;i++){
            insanseckart.add(insan.getInsanKart().get(insan.kartSec(insan)));
        }
        for(int i=0;i<3;i++){
            pcSeckart.add(pc.getBilgisayarKart().get(pc.kartSec(pc)));
        }
    }//insan ve pc ayırılıp arka tarafa alınıcak.

}
