public class Obus extends Kara_araclari{
    public Obus() {
        super(0, 20, 10,5,0);
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
