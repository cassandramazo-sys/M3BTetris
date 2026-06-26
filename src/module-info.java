module M3BTetris {
    requires javafx.controls;
    requires javafx.fxml;

    opens ph.edu.dlsu.lbycpob.tetris to javafx.fxml;
    exports ph.edu.dlsu.lbycpob.tetris;
}