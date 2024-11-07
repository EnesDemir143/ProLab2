import java.util.ArrayList;
import java.util.Random;
import java.util.List;

public class Oyun {
    public static void main(String[] args) {

        Oyuncu insan = new Oyuncu("123","Enes",0);
        Oyuncu pc = new Oyuncu();

        ArrayList<Savas_Araclari> pcSeckart = new ArrayList<>();
        ArrayList<Savas_Araclari> insanSeckart = new ArrayList<>();

        insan.insanKartListesi();
        pc.bilgisyarKartListesi();

        secilenKartlar(insan,pc,insanSeckart,pcSeckart);
        System.out.println("ÖNCE");
        for (int i = 0; i < insan.getInsanKart().size(); i++) {
            insan.getInsanKart().get(i).kartPuanGoster();
        }
        for (int i = 0; i < pc.getBilgisayarKart().size(); i++) {
            pc.getBilgisayarKart().get(i).kartPuanGoster();
        }
        kartSavaslari(insan,pc,insanSeckart,pcSeckart);
        System.out.println("Sonra");
        for (int i = 0; i < insan.getInsanKart().size(); i++) {
            insan.getInsanKart().get(i).kartPuanGoster();
        }
        for (int i = 0; i < pc.getBilgisayarKart().size(); i++) {
            pc.getBilgisayarKart().get(i).kartPuanGoster();
        }

    }

    public static void secilenKartlar(Oyuncu insan,Oyuncu pc,ArrayList<Savas_Araclari> insanseckart,ArrayList<Savas_Araclari> pcSeckart) {
        for(int i=0;i<3;i++){
           switch (insan.kartSec(insan)){
                case 0->insanseckart.add(insan.getInsanKart().get(0));
                case 1->insanseckart.add(insan.getInsanKart().get(1));
                case 2->insanseckart.add(insan.getInsanKart().get(2));
                case 3->insanseckart.add(insan.getInsanKart().get(3));
                case 4->insanseckart.add(insan.getInsanKart().get(4));
                case 5->insanseckart.add(insan.getInsanKart().get(5));
                case 6->insanseckart.add(insan.getInsanKart().get(6));
               default -> throw new IllegalStateException("Unexpected value: " + insan.kartSec(insan));
           }
        }
        for(int i=0;i<3;i++){
            switch (pc.kartSec(pc)){
                case 0->pcSeckart.add(pc.getBilgisayarKart().get(0));
                case 1->pcSeckart.add(pc.getBilgisayarKart().get(1));
                case 2->pcSeckart.add(pc.getBilgisayarKart().get(2));
                case 3->pcSeckart.add(pc.getBilgisayarKart().get(3));
                case 4->pcSeckart.add(pc.getBilgisayarKart().get(4));
                case 5->pcSeckart.add(pc.getBilgisayarKart().get(5));
                case 6->pcSeckart.add(pc.getBilgisayarKart().get(6));
                default -> throw new IllegalStateException("Unexpected value: " + pc.kartSec(pc));
            }
        }
    }//insan ve pc ayırılıp arka tarafa alınıcak.

    public static void kartSavaslari(Oyuncu insan,Oyuncu pc,ArrayList<Savas_Araclari> insanseckart, ArrayList<Savas_Araclari> pcSeckart) {
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
                }
            }
        }
    }
}
