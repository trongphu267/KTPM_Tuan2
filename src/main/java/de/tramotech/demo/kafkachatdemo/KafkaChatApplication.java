package de.tramotech.demo.kafkachatdemo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import java.io.IOException;

public class KafkaChatApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(KafkaChatApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 620, 440);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        KafkaClient kafka = KafkaClient.getInstance();
        TextArea mainArea = (TextArea) scene.lookup("#mainArea");

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    ConsumerRecords<String, String> records =  kafka.pollRecords();
                    for(ConsumerRecord<String, String> record : records) {
                        mainArea.appendText(record.key() + "> " + record.value() + "\n");
                    }
                    kafka.commitAsynch();

                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        t.start();

    }

    public static void main(String[] args) {
        launch();
    }
}