#!/bin/bash

# JavaFX SDK'nın bulunduğu yolu belirtin
JAVA_FX_PATH="/Library/Java/javafx-sdk-17.0.13/lib"

# Çalıştırılacak JAR dosyasının yolu
JAR_FILE="/Users/enesdemir/Desktop/ProLab2.jar"

# Grafik işlemeyi yazılımsal olarak zorlamak için bir seçenek (isteğe bağlı)
PRISM_OPTION="-Dprism.order=sw"

# Java komutu ile uygulamayı çalıştırın
java $PRISM_OPTION --module-path $JAVA_FX_PATH --add-modules javafx.controls,javafx.fxml -jar $JAR_FILE
