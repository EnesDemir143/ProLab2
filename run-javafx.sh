#!/bin/bash

# JavaFX SDK'nın bulunduğu yolu belirtin
JAVA_FX_PATH="/Library/Java/javafx-sdk-17.0.13/lib"

# Çalıştırılacak JAR dosyasının yolu
JAR_FILE="/Users/enesdemir/Desktop/ProLab2.jar"

# Java komutu ile uygulamayı çalıştırın
java --module-path $JAVA_FX_PATH --add-modules javafx.controls,javafx.fxml -jar $JAR_FILE
