# cs61b-2048
My version of 2048 based on cs61b(2019) project0
No use of the ucb library.
Javafx imported.

- Two ways to run
  1. Main
     1. javac --module-path /path/to/javafx-sdk-<version>/lib --add-modules=javafx.base,javafx.controls,javafx.graphics Main.java
     2. java --module-path /path/to/javafx-sdk-<version>/lib --add-modules=javafx.base,javafx.controls,javafx.graphics Main.java
  2. GUI
     - IntelliJ IDEA: `Run` -> `Edit Configurations` -> `Add New Configuration` -> `Modify options` -> `Add VM options`
       - VM options: --module-path /path/to/javafx-sdk-<version>/lib --add-modules=javafx.base,javafx.controls,javafx.graphics
       - Main class: GUI
