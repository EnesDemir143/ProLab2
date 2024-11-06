public class KFS extends Kara_araclari{
    private static int kfsSayisi = 0;

    public KFS() {
        super(0, 10, 10,10,20,"kfs"+(++kfsSayisi));
    }

    @Override
    public void kartPuanGoster() {
        System.out.println("Kart Puan Goster:"+getSeviye_puani());
        System.out.println("Dayaniklik:"+getDayaniklilik());
    }

    @Override
    public void durumGuncelle(int hasar) {
        this.setDayaniklilik(Math.max(this.getDayaniklilik() - hasar, 0));
    }

}
