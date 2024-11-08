import Savas_Araclari.Savas_Araclari;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public interface Dosya_Islemleri {
    default void ilkKartlar(Oyuncu insan, Oyuncu pc, ArrayList<Savas_Araclari> insankart,ArrayList<Savas_Araclari> pckart) {
        try (FileWriter fw = new FileWriter("KartIslemleri.txt", true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write("Senin başlangıc puanı: "+insan.getInsanSkor());
            bw.newLine();
            bw.write("Senin kartları bunlardır:\n");
            for(var kartlar: insankart) {
                bw.write(kartlar.getKartID());
                bw.newLine();
            }
            bw.newLine();
            bw.write("Bilgisayarın baslangıc puanı: "+pc.getPcSkor());
            bw.newLine();
            bw.write("Bilgisayarın kartları bunlardır:\n");
            for(var kartlar: pckart) {
                bw.write(kartlar.getKartID());
                bw.newLine();
            }
            bw.newLine();
            bw.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    default void savas(Oyuncu insan,Oyuncu pc,ArrayList<Savas_Araclari> insanseckart,ArrayList<Savas_Araclari> pckartsec,int adim) {
        try (FileWriter fw = new FileWriter("KartIslemleri.txt", true);
             BufferedWriter bw = new BufferedWriter(fw)){
            bw.write("-".repeat(20)+adim+".adim"+"-".repeat(20));
            bw.newLine();
            bw.write("Senin sectiğin kartlar:\n");
            for(var kartlar: insanseckart) {
                bw.write(kartlar.getKartID());
                bw.newLine();
            }
            bw.newLine();
            bw.write("PC sectiği kartlar:\n");
            for(var kartlar: pckartsec){
                bw.write(kartlar.getKartID());
                bw.newLine();
            }
            bw.newLine();
            bw.write("-".repeat(20)+"Savaş"+"-".repeat(20));
            bw.newLine();
            bw.write(" ".repeat(12)+"Sen"+" ".repeat(9)+"Bilgisayar");
            bw.newLine();
            for (int i = 0; i < 3; i++) {
                bw.write((i+1)+".Eşleşme:"+insanseckart.get(i).getKartID()+" <----> "+pckartsec.get(i).getKartID());
                bw.newLine();
            }
            bw.newLine();
            bw.write("Skorlar=>\n");
            bw.write("Sen:"+insan.getInsanSkor()+"\n");
            bw.write("Bilgisayar:"+pc.getPcSkor()+"\n");

            bw.newLine();
            bw.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    default void destendekiKartlar(Oyuncu insan,Oyuncu pc,ArrayList<Savas_Araclari> insanDeste,ArrayList<Savas_Araclari> pcDeste){
        try (FileWriter fw = new FileWriter("KartIslemleri.txt", true);
             BufferedWriter bw = new BufferedWriter(fw)){
            bw.write("Senin desten=>\n");
            for(var kartlar: insanDeste){
                bw.write(kartlar.getKartID());
                bw.newLine();
            }
            bw.newLine();
            bw.write("Bilgisayarın destesi=>\n");
            for(var kartlar: pcDeste){
                bw.write(kartlar.getKartID());
                bw.newLine();
            }
            bw.newLine();
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    default void dosyayiSifirla() {
        try (FileWriter fw = new FileWriter("KartIslemleri.txt", false)) {
            // Dosyayı boş olarak yeniden oluşturur
        } catch (IOException e) {
            System.out.println("Dosya sıfırlama hatası: " + e.getMessage());
        }
    }
}
