public class Firkateyn extends Deniz_araclari{

    public Firkateyn() {
        super(0, 25, 10,5,0);
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
