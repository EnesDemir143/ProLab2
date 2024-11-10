import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Veri_Modelleri.Savas_Araclari_Modeli.*;
import java.util.ArrayList;
//Mainde pc. ve insan. ile olan erişimleri kapat(Methodları kastediyorum)
//Debug yap birden fazla kez kodda!!!

public class Oyun extends Application implements Dosya_Islemleri{

    @Override
    public void start(Stage primaryStage) throws Exception {
        // FXML dosyasını yüklüyoruz
        Parent root = FXMLLoader.load(getClass().getResource("sample/sample.fxml"));

        // Sahneyi oluşturuyoruz ve başlatıyoruz
        primaryStage.setTitle("JavaFX Grafik Uygulaması");
        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.show();
    }

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
            System.out.println("ADIM====> "+(i));
            System.out.println("ÖNCE");
            System.out.println("insanskor:"+insan.getInsanSkor());
            System.out.println("pcskor:"+pc.getPcSkor());
            System.out.println("İnsankartları");
            for (int j = 0; j < insan.getInsanKart().size(); j++) {
                System.out.print(j+1+"=>");
                insan.getInsanKart().get(j).kartPuanGoster();
            }
            System.out.println("pckartları");
            for (int j = 0; j < pc.getBilgisayarKart().size(); j++) {
                System.out.print(j+1+"=>");
                pc.getBilgisayarKart().get(j).kartPuanGoster();
            }
            Oyuncu.secilenKartlar(insan, pc, insanSeckart, pcSeckart);
            insan.savas(insan,pc,insanSeckart,pcSeckart,i);
            Oyuncu.kartSavaslari(insan, pc, insanSeckart, pcSeckart);
            insan.destendekiKartlar(insan, pc, insan.getInsanKart(), pc.getBilgisayarKart());
            System.out.println("Sonra");
            System.out.println("insanskor:"+insan.getInsanSkor());
            System.out.println("pcskor:"+pc.getPcSkor());
            System.out.println("İnsankartları");
            for (int j = 0; j < insan.getInsanKart().size(); j++) {
                insan.getInsanKart().get(j).kartPuanGoster();
            }
            System.out.println("pckartları");
            for (int j = 0; j < pc.getBilgisayarKart().size(); j++) {
                pc.getBilgisayarKart().get(j).kartPuanGoster();
            }
            int a=Oyuncu.savasSonuclari(insan,pc,i,0);
            pcSeckart.clear();
            insanSeckart.clear();
            if(a==7 || a==8 ){
                System.out.println("ADIM====> "+(i+1));
                System.out.println("ÖNCE");
                System.out.println("insanskor:"+insan.getInsanSkor());
                System.out.println("pcskor:"+pc.getPcSkor());
                System.out.println("İnsankartları");
                for (int j = 0; j < insan.getInsanKart().size(); j++) {
                    System.out.print(j+1+"=>");
                    insan.getInsanKart().get(j).kartPuanGoster();
                }
                System.out.println("pckartları");
                for (int j = 0; j < pc.getBilgisayarKart().size(); j++) {
                    System.out.print(j+1+"=>");
                    pc.getBilgisayarKart().get(j).kartPuanGoster();
                }
                Oyuncu.secilenKartlar(insan, pc, insanSeckart, pcSeckart);
                insan.savas(insan,pc,insanSeckart,pcSeckart,i+1);
                Oyuncu.kartSavaslari(insan, pc, insanSeckart, pcSeckart);
                Oyuncu.savasSonuclari(insan,pc,i+1,1);
                insan.destendekiKartlar(insan, pc, insan.getInsanKart(), pc.getBilgisayarKart());
                System.out.println("Sonra");
                System.out.println("insanskor:"+insan.getInsanSkor());
                System.out.println("pcskor:"+pc.getPcSkor());
                System.out.println("İnsankartları");
                for (int j = 0; j < insan.getInsanKart().size(); j++) {
                    insan.getInsanKart().get(j).kartPuanGoster();
                }
                System.out.println("pckartları");
                for (int j = 0; j < pc.getBilgisayarKart().size(); j++) {
                    pc.getBilgisayarKart().get(j).kartPuanGoster();
                }
                System.out.println("bitti");
                break;
            }
            if(a==3 || a==4 || a==5 || a==6 || a==2){
                System.out.println("bitttttttttti");
                break;
            }
        }
        launch(args);
    }
}

