package de.tramotech.demo.kafkachatdemo;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ChatUIController2 {
    @FXML
    private TextArea mainArea;
    @FXML
    private TextField input;
    private KafkaClient2 kafka2 = KafkaClient2.getInstance();

    @FXML

    protected void onHelloButtonClick() {
        kafka2.sendMessage(input.getText());

    }
}