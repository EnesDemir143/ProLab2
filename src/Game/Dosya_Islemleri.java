package Game;

import Veri_Modelleri.Savas_Araclari_Modeli.Savas_Araclari;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public interface Dosya_Islemleri {

    default void ilkKartlar(Oyuncu insan, Oyuncu pc, ArrayList<Savas_Araclari> insankart, ArrayList<Savas_Araclari> pckart) {
        try (FileWriter fw = new FileWriter("KartIslemleri.txt", true);
             BufferedWriter bw = new BufferedWriter(fw)) {

            // İnsan oyuncu bilgileri
            bw.write("-".repeat(50));
            bw.newLine();
            bw.write("BAŞLANGIÇ KARTLARI VE PUANLAR");
            bw.newLine();
            bw.write("-".repeat(50));
            bw.newLine();

            bw.write("Oyuncu: " + insan.getOyuncu_adi());
            bw.newLine();
            bw.write("Başlangıç Puanı: " + insan.getInsanSkor());
            bw.newLine();
            bw.write("Kartlar:");
            bw.newLine();
            for(var kart: insankart) {
                bw.write(String.format("- %s (Dayanıklılık: %d)",
                        kart.getKartID(), kart.getDayaniklilik()));
                bw.newLine();
            }

            bw.write("-".repeat(30));
            bw.newLine();

            // Bilgisayar bilgileri
            bw.write("Oyuncu: Bilgisayar");
            bw.newLine();
            bw.write("Başlangıç Puanı: " + pc.getPcSkor());
            bw.newLine();
            bw.write("Kartlar:");
            bw.newLine();
            for(var kart: pckart) {
                bw.write(String.format("- %s (Dayanıklılık: %d)",
                        kart.getKartID(), kart.getDayaniklilik()));
                bw.newLine();
            }

            bw.write("-".repeat(50));
            bw.newLine();
            bw.newLine();

        } catch (IOException e) {
            throw new RuntimeException("Dosya yazma hatası: " + e.getMessage());
        }
    }

    default void savas(Oyuncu insan, Oyuncu pc, ArrayList<Savas_Araclari> insanseckart, ArrayList<Savas_Araclari> pckartsec, int adim) {
        try (FileWriter fw = new FileWriter("KartIslemleri.txt", true);
             BufferedWriter bw = new BufferedWriter(fw)) {

            bw.write("-".repeat(20) + " " + adim + ". SAVAŞ " + "-".repeat(20));
            bw.newLine();
            bw.newLine();

            // Seçilen kartların gösterimi
            bw.write("Oyuncu Seçilen Kartlar:");
            bw.newLine();
            for(var kart: insanseckart) {
                bw.write(String.format("- %s (Dayanıklılık: %d)",
                        kart.getKartID(), kart.getDayaniklilik()));
                bw.newLine();
            }

            bw.write("-".repeat(30));
            bw.newLine();

            bw.write("Bilgisayar Seçilen Kartlar:");
            bw.newLine();
            for(var kart: pckartsec) {
                bw.write(String.format("- %s (Dayanıklılık: %d)",
                        kart.getKartID(), kart.getDayaniklilik()));
                bw.newLine();
            }

            bw.newLine();
            bw.write("-".repeat(20) + " EŞLEŞMELER " + "-".repeat(20));
            bw.newLine();
            bw.write(String.format("%-35s | %-35s", "OYUNCU", "BİLGİSAYAR"));
            bw.newLine();
            bw.write("-".repeat(75));
            bw.newLine();

            for(int i = 0; i < 3; i++) {
                bw.write(String.format("%-35s | %-35s",
                        String.format("%s (Dayanıklılık: %d)",
                                insanseckart.get(i).getKartID(),
                                insanseckart.get(i).getDayaniklilik()),
                        String.format("%s (Dayanıklılık: %d)",
                                pckartsec.get(i).getKartID(),
                                pckartsec.get(i).getDayaniklilik())));
                bw.newLine();
            }

            bw.write("-".repeat(75));
            bw.newLine();
            bw.newLine();

        } catch (IOException e) {
            throw new RuntimeException("Dosya yazma hatası: " + e.getMessage());
        }
    }

    default void destendekiKartlar(Oyuncu insan, Oyuncu pc, ArrayList<Savas_Araclari> insanDeste, ArrayList<Savas_Araclari> pcDeste) {
        try (FileWriter fw = new FileWriter("KartIslemleri.txt", true);
             BufferedWriter bw = new BufferedWriter(fw)) {

            bw.write("-".repeat(20) + " GÜNCEL DURUM " + "-".repeat(20));
            bw.newLine();
            bw.newLine();

            // Skor durumu
            bw.write("SKORLAR");
            bw.newLine();
            bw.write("-".repeat(20));
            bw.newLine();
            bw.write(String.format("Oyuncu: %d", insan.getInsanSkor()));
            bw.newLine();
            bw.write(String.format("Bilgisayar: %d", pc.getPcSkor()));
            bw.newLine();

            bw.newLine();
            bw.write("GÜNCEL DESTELER");
            bw.newLine();
            bw.write("-".repeat(20));
            bw.newLine();

            // Game.Oyuncu destesi
            bw.write("Oyuncu Destesi:");
            bw.newLine();
            for(var kart: insanDeste) {
                bw.write(String.format("- %s (Dayanıklılık: %d)",
                        kart.getKartID(), kart.getDayaniklilik()));
                bw.newLine();
            }

            bw.write("-".repeat(20));
            bw.newLine();

            // Bilgisayar destesi
            bw.write("Bilgisayar Destesi:");
            bw.newLine();
            for(var kart: pcDeste) {
                bw.write(String.format("- %s (Dayanıklılık: %d)",
                        kart.getKartID(), kart.getDayaniklilik()));
                bw.newLine();
            }

            bw.write("-".repeat(50));
            bw.newLine();
            bw.newLine();

        } catch (IOException e) {
            throw new RuntimeException("Dosya yazma hatası: " + e.getMessage());
        }
    }

    default void dosyayiSifirla() {
        try (FileWriter fw = new FileWriter("KartIslemleri.txt", false)) {
            // Dosya içeriği sıfırlanıyor
        } catch (IOException e) {
            throw new RuntimeException("Dosya sıfırlama hatası: " + e.getMessage());
        }
    }

    default void savasSonucu(Oyuncu insan, Oyuncu pc) {
        try (FileWriter fw = new FileWriter("KartIslemleri.txt", true);
             BufferedWriter bw = new BufferedWriter(fw)) {

            bw.write("-".repeat(20) + " SAVAŞ SONUCU " + "-".repeat(20));
            bw.newLine();
            bw.newLine();

            if(insan.getInsanSkor() > pc.getPcSkor()) {
                bw.write("KAZANAN: " + insan.getOyuncu_adi());
                bw.newLine();
                bw.write("Final Skoru: " + insan.getInsanSkor());
            }
            else if(pc.getPcSkor() > insan.getInsanSkor()) {
                bw.write("KAZANAN: Bilgisayar");
                bw.newLine();
                bw.write("Final Skoru: " + pc.getPcSkor());
            }
            else {
                bw.write("SONUÇ: Berabere");
                bw.newLine();
                bw.write("Final Skoru: " + insan.getInsanSkor() + " - " + pc.getPcSkor());
            }

            bw.newLine();
            bw.write("-".repeat(50));
            bw.newLine();

        } catch (IOException e) {
            throw new RuntimeException("Dosya yazma hatası: " + e.getMessage());
        }
    }
}