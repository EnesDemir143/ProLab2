public class Sida extends Deniz_araclari{
    private static int sidaSayisi = 0;

    public Sida() {
        super(0, 15, 10,10,10,"sida"+(++sidaSayisi));
    }

    @Override
    public void kartPuanGoster() {
        System.out.println("Kart Puan Goster:"+getSeviye_puani());
        System.out.println("Dayaniklik:"+getDayaniklilik());
    }

    @Override
    public void durumGuncelle() {

    }


}
