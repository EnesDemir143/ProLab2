import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Veri_Modelleri.Savas_Araclari_Modeli.*;

import javax.print.attribute.HashPrintJobAttributeSet;
import java.util.ArrayList;
import java.util.Objects;
//Mainde pc. ve insan. ile olan erişimleri kapat(Methodları kastediyorum)
//Debug yap birden fazla kez kodda!!!

public class Oyun  implements Dosya_Islemleri{

//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        // FXML dosyasını yüklüyoruz
//        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("sample/sample.fxml")));
//
//        // Sahneyi oluşturuyoruz ve başlatıyoruz
//        primaryStage.setTitle("JavaFX Grafik Uygulaması");
//        primaryStage.setScene(new Scene(root, 500, 500));
//        primaryStage.show();
//    }

    public static void main(String[] args) {

        Oyuncu insan = new Oyuncu("123","Enes",0);
        Oyuncu pc = new Oyuncu();

        ArrayList<Savas_Araclari> pcSeckart = new ArrayList<>();
        ArrayList<Savas_Araclari> insanSeckart = new ArrayList<>();

        insan.dosyayiSifirla();
        insan.insanKartListesi(insan);
        pc.bilgisyarKartListesi(pc);
        insan.ilkKartlar(insan,pc,insan.getInsanKart(),pc.getBilgisayarKart());
        for (int i = 1; i < 6; i++) {
            Oyuncu.secilenKartlar(insan, pc, insanSeckart, pcSeckart);
            insan.savas(insan,pc,insanSeckart,pcSeckart,i);
            Oyuncu.kartSavaslari(insan, pc, insanSeckart, pcSeckart);
            int a=Oyuncu.savasSonuclari(insan,pc,i,0);
            insan.destendekiKartlar(insan, pc, insan.getInsanKart(), pc.getBilgisayarKart());
            pcSeckart.clear();
            insanSeckart.clear();
            if(a==7 || a==8 ){
                Oyuncu.secilenKartlar(insan, pc, insanSeckart, pcSeckart);
                insan.savas(insan,pc,insanSeckart,pcSeckart,i+1);
                Oyuncu.kartSavaslari(insan, pc, insanSeckart, pcSeckart);
                Oyuncu.savasSonuclari(insan,pc,i+1,1);
                insan.destendekiKartlar(insan, pc, insan.getInsanKart(), pc.getBilgisayarKart());
                insan.savasSonucu(insan,pc);
                break;
            }
            if(a==3 || a==4 || a==5 || a==6 || a==2 ){
                insan.savasSonucu(insan,pc);
                break;
            }
        }
     //   launch(args);
    }
}

