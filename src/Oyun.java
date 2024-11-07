import java.util.ArrayList;

//Dosya çekmeye bugün bakıcalıcak!!!
//Mainde pc. ve insan. ile olan erişimleri kapat(Methodları kastediyorum)

public class Oyun {
    public static void main(String[] args) {

        Oyuncu insan = new Oyuncu("123","Enes",30);
        Oyuncu pc = new Oyuncu();

        ArrayList<Savas_Araclari> pcSeckart = new ArrayList<>();
        ArrayList<Savas_Araclari> insanSeckart = new ArrayList<>();

        insan.insanKartListesi(insan);
        pc.bilgisyarKartListesi(pc);


        for (int i = 1; i < 6; i++) {
            System.out.println("ADIM====> "+(i));
            System.out.println("ÖNCE");
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
            Oyuncu.secilenKartlar(insan, pc, insanSeckart, pcSeckart);
            Oyuncu.kartSavaslari(insan, pc, insanSeckart, pcSeckart);
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
        }

    }
}
