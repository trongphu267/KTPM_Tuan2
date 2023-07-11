module de.tranotech.demo.kafkachatdemo {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;
    requires kafka.clients;

    opens de.tramotech.demo.kafkachatdemo to javafx.fxml;
    exports de.tramotech.demo.kafkachatdemo;
}