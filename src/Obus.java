public class Obus extends Kara_araclari {
    private static int obusSayisi = 0;

    public Obus() {
        super(0, 20, 10,5,0,"obus"+(++obusSayisi));
    }

    @Override
    public void kartPuanGoster() {
        System.out.print(getKartID()+"|");
        System.out.print("Kart Puan Goster:"+getSeviye_puani()+"|");
        System.out.println("Dayaniklik:"+getDayaniklilik());
    }

    @Override
    public void durumGuncelle(int hasar) {
        this.setDayaniklilik(Math.max(this.getDayaniklilik() - hasar, 0));
    }


}
