module com.example.bomberman_uet_21020778 {
  requires javafx.controls;
  requires javafx.fxml;
  requires java.desktop;


  opens com.bomberman_uet_21020778 to javafx.fxml;
  exports com.bomberman_uet_21020778;
}