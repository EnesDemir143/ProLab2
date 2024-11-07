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
            if (insan.getInsanKart().isEmpty() || pc.getBilgisayarKart().isEmpty()) {
                System.out.println("Game over");
                break;
            }
            if(i==5 && !insan.getInsanKart().isEmpty() && !pc.getBilgisayarKart().isEmpty() ) {
                if(insan.getInsanSkor()>pc.getPcSkor()){
                    System.out.println("sen kazandın");
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
                    }else if (toplam_dayaniklilik1<toplam_dayaniklilik2){
                        System.out.println("pc kazandın");
                        pc.setPcSkor(toplam_dayaniklilik2-toplam_dayaniklilik1);
                    } else {
                        System.out.println("Berabere");
                    }

                } else{
                    System.out.println("pc kazandı");
                }
            }
        }

    }
}
