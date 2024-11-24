package Game;

import Veri_Modelleri.Savas_Araclari_Modeli.Savas_Araclari;
import javafx.application.Application;
import sample.UI;

import java.util.ArrayList;
//Mainde pc. ve insan. ile olan erişimleri kapat(Methodları kastediyorum)
//Debug yap birden fazla kez kodda!!!

public class Oyun implements Dosya_Islemleri{

    public static void main(String[] args) {

        Game.Oyuncu insan = new Game.Oyuncu("123","",0);
        Game.Oyuncu pc = new Game.Oyuncu();
        UI.setInitialOyuncu(insan);
        Application.launch(UI.class, args);
        System.out.println(insan.getInsanSkor());
        System.out.println(insan.getOyuncu_adi());

        ArrayList<Savas_Araclari> pcSeckart = new ArrayList<>();
        ArrayList<Savas_Araclari> insanSeckart = new ArrayList<>();

        insan.dosyayiSifirla();
        insan.insanKartListesi(insan);
        pc.bilgisyarKartListesi(pc);
        insan.ilkKartlar(insan,pc,insan.getInsanKart(),pc.getBilgisayarKart());
        for (int i = 1; i < 6; i++) {
            Game.Oyuncu.secilenKartlar(insan, pc, insanSeckart, pcSeckart);
            insan.savas(insan,pc,insanSeckart,pcSeckart,i);
            Game.Oyuncu.kartSavaslari(insan, pc, insanSeckart, pcSeckart);
            int a=Game.Oyuncu.savasSonuclari(insan,pc,i,0);
            insan.destendekiKartlar(insan, pc, insan.getInsanKart(), pc.getBilgisayarKart());
            pcSeckart.clear();
            insanSeckart.clear();
            if(a==7 || a==8 ){
                Game.Oyuncu.secilenKartlar(insan, pc, insanSeckart, pcSeckart);
                insan.savas(insan,pc,insanSeckart,pcSeckart,i+1);
                Game.Oyuncu.kartSavaslari(insan, pc, insanSeckart, pcSeckart);
                Game.Oyuncu.savasSonuclari(insan,pc,i+1,1);
                insan.destendekiKartlar(insan, pc, insan.getInsanKart(), pc.getBilgisayarKart());
                insan.savasSonucu(insan,pc);
                break;
            }
            if(a==3 || a==4 || a==5 || a==6 || a==2 ){
                insan.savasSonucu(insan,pc);
                break;
            }
        }
    }
}

