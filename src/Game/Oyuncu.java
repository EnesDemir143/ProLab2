package Game;

import Veri_Modelleri.Savas_Araclari_Modeli.Deniz_Araclari_Modeli.Firkateyn;
import Veri_Modelleri.Savas_Araclari_Modeli.Deniz_Araclari_Modeli.Sida;
import Veri_Modelleri.Savas_Araclari_Modeli.EkstraVurusOzellikleri;
import Veri_Modelleri.Savas_Araclari_Modeli.Hava_Araclari_Modeli.Siha;
import Veri_Modelleri.Savas_Araclari_Modeli.Hava_Araclari_Modeli.Ucak;
import Veri_Modelleri.Savas_Araclari_Modeli.Kara_Araclari_Modeli.KFS;
import Veri_Modelleri.Savas_Araclari_Modeli.Kara_Araclari_Modeli.Obus;
import Veri_Modelleri.Savas_Araclari_Modeli.Savas_Araclari;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Oyuncu implements Dosya_Islemleri {
    private  String  oyuncu_id;
    private  String oyuncu_adi;
    private int insanSkor;
    private int pcSkor;
    private static int i=0;
   private  ArrayList<Savas_Araclari> insanKart = new ArrayList<>();
   private  ArrayList<Savas_Araclari> bilgisayarKart = new ArrayList<>();
   private ArrayList<Savas_Araclari>kullanilmisKartlarInsan = new ArrayList<>();
   private ArrayList<Savas_Araclari>kullanilmisKartlarPc = new ArrayList<>();

    public ArrayList<Savas_Araclari> getKullanilmisKartlarInsan() {
        return kullanilmisKartlarInsan;
    }

    public ArrayList<Savas_Araclari> getKullanilmisKartlarPc() {
        return kullanilmisKartlarPc;
    }

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

    public void setOyuncu_adi(String oyuncu_adi) {
        this.oyuncu_adi = oyuncu_adi;
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

    public String getOyuncu_adi() {
        return oyuncu_adi;
    }

    public ArrayList<Savas_Araclari> getInsanKart() {
        return insanKart;
    }

    public ArrayList<Savas_Araclari> getBilgisayarKart() {
        return bilgisayarKart;
    }


    public void insanKartListesi(Oyuncu insan){
        for(int i=0;i<6;i++){
            insanKart.add(kartEkleme(insan));
        }
        int i=0;
        System.out.println("İnsan kart listesi");
        while (i<insanKart.size()) {
            System.out.println(insanKart.get(i).getKartID());
            i++;
        }
    }
    public void bilgisyarKartListesi(Oyuncu bilgisayar){
        for(int i=0;i<6;i++){
            bilgisayarKart.add(kartEkleme(bilgisayar));
        }
        int i=0;
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

    public  int kartSec(Oyuncu oyuncu,ArrayList<Savas_Araclari> pcseckart,ArrayList<Savas_Araclari> insanseckart){
        if(oyuncu.oyuncu_adi.equals("Bilgisayar")){
            Random rand = new Random();
            int secim;
            do {
                secim = rand.nextInt(oyuncu.getBilgisayarKart().size());
            } while (oyuncu.getKullanilmisKartlarPc().contains(oyuncu.getBilgisayarKart().get(secim)) && kullanilmisKartlarPc.size() != getBilgisayarKart().size());

            while (pcseckart.contains(oyuncu.getBilgisayarKart().get(secim))){
                secim = rand.nextInt(oyuncu.getBilgisayarKart().size());
            }
            return secim;
        }else{
            return -1;
        }
    }
    public static void kartSavaslari(Oyuncu insan,Oyuncu pc,ArrayList<Savas_Araclari> insanseckart, ArrayList<Savas_Araclari> pcSeckart) {
        //insanın saldırısı

        for(int i = 0; i < 3; i++) {
            System.out.println("size bro:"+insanseckart.size());
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
                    pc.getKullanilmisKartlarPc().remove(pcSeckart.get(i));
                    System.out.println(pcSeckart.get(i).getKartID()+"öldü"+"   i="+i);
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
                    System.out.println(insanseckart.get(i).getKartID()+"öldü");
                    insan.getInsanKart().remove(insanseckart.get(i));
                    insan.getKullanilmisKartlarInsan().remove(insanseckart.get(i));
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
        i=0;
    }

    public static void secilenKartlar(Oyuncu insan, Oyuncu pc, ArrayList<Savas_Araclari> insanseckart, ArrayList<Savas_Araclari> pcSeckart) {
            if (!insan.getKullanilmisKartlarInsan().contains(insanseckart.get(i))){
                insan.getKullanilmisKartlarInsan().add(insanseckart.get(i));
            }
            pcSeckart.add(pc.getBilgisayarKart().get(pc.kartSec(pc,pcSeckart,insanseckart)));
            if (pc.getKullanilmisKartlarPc().contains(pcSeckart.get(i))){
                pc.getKullanilmisKartlarPc().add(pcSeckart.get(i));
            }
        i++;
    }

    public static int savasSonuclari(Oyuncu insan, Oyuncu pc, int tur, int kontrol){
            if( tur==8 || (insan.getInsanKart().size()==1 || pc.getBilgisayarKart().size()==1) || (kontrol==1) ) {
                if(insan.getInsanSkor()>pc.getPcSkor()){
                    System.out.println("sen kazandın");
                    return 2;
                }else if(insan.getInsanSkor()==pc.getPcSkor()){
                    int toplam_dayaniklilik1=0;
                    int toplam_dayaniklilik2=0;
                    for (int j = 0; j < insan.getInsanKart().size(); j++) {
                        toplam_dayaniklilik1+=insan.getInsanKart().get(j).getDayaniklilik();
                    }
                    for (int j = 0; j < pc.getBilgisayarKart().size(); j++) {
                        toplam_dayaniklilik2+=pc.getBilgisayarKart().get(j).getDayaniklilik();
                    }
                    if(toplam_dayaniklilik1>toplam_dayaniklilik2){
                        System.out.println("Sen kazandın");
                        insan.setInsanSkor(toplam_dayaniklilik1-toplam_dayaniklilik2);
                        System.out.println((toplam_dayaniklilik1-toplam_dayaniklilik2)+"eklendi");
                        return 3;
                    }else if (toplam_dayaniklilik1<toplam_dayaniklilik2){
                        System.out.println("pc kazandı");
                        pc.setPcSkor(toplam_dayaniklilik2-toplam_dayaniklilik1);
                        System.out.println((toplam_dayaniklilik2-toplam_dayaniklilik1)+"eklendi");
                        return 4;
                    } else {
                        System.out.println("Berabere");
                        return 5;
                    }

                } else{
                    System.out.println("pc kazandı");
                    return 6;
                }
            }
            if ( kontrol==0 && (insan.getInsanKart().size()==2 || pc.getBilgisayarKart().size()==2))  {
                System.out.println("ekstra kartttttt");
                int x=0;
                if(pc.getBilgisayarKart().size()==2){
                    pc.getBilgisayarKart().add(pc.kartEkleme(pc));
                    x++;
                }
                if (insan.getInsanKart().size()==2){
                    insan.getInsanKart().add(insan.kartEkleme(insan));
                    x++;
                }
                if(x>0){
                    System.out.println("finall");
                    return 7;
                }
            }
            System.out.println("devamm");
            return -1;
        }

}
