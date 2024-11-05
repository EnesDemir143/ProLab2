public class KFS extends Kara_araclari{
    public KFS() {
        super(0, 10, 10,10,20);
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
