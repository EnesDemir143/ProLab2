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
        kartSavaslari();

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
    }

    public static int saldiriHesapla(Oyuncu insan,Oyuncu pc) {
            int secim=pc.kartSec(pc);
            return  pc.getBilgisayarKart().get(secim).getVurus();
    }

    public static void kartSavaslari(){

    }
}
