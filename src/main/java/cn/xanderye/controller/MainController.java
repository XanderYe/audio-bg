package cn.xanderye.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import lombok.SneakyThrows;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author XanderYe
 * @date 2020/2/6
 */
public class MainController implements Initializable {
    @FXML
    private CheckBox loopCheckBox;
    @FXML
    private Slider progressSlider;
    @FXML
    private Label progressLabel;

    private static boolean first = true;
    private static boolean play = false;
    private static boolean loop = true;
    private static double total = 0;
    private static MediaPlayer mediaPlayer;

    @SneakyThrows
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        URL url = Thread.currentThread().getContextClassLoader().getResource("background.mp3");
        Media media = new Media(url.toString());
        mediaPlayer = new MediaPlayer(media);
        progressSlider.setMin(0);
        progressSlider.setMax(100);
        progressSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                double oldVal = (Double) oldValue;
                double newVal = (Double) newValue;
                if (Math.abs(newVal - oldVal) > 2) {
                    double sec = newVal / 100 * total;
                    mediaPlayer.seek(Duration.seconds(sec));
                }
            }
        });
        mediaPlayer.setOnEndOfMedia(() -> {
            mediaPlayer.stop();
            play = false;
        });
    }

    public void loop() {
        loop = !loop;
    }

    public void play() {
        if (play) {
            mediaPlayer.pause();
        } else {
            if (loop) {
                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            } else {
                mediaPlayer.setCycleCount(1);
            }
            mediaPlayer.play();
            total = mediaPlayer.getTotalDuration().toSeconds();
            if (first) {
                ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
                scheduledExecutorService.scheduleAtFixedRate(() -> {
                    double current = mediaPlayer.getCurrentTime().toSeconds();
                    progressSlider.setValue(current / total * 100);
                    String time = formatTime(current) + "/" + formatTime(total);
                    Platform.runLater(() -> progressLabel.setText(time));
                }, 0, 1, TimeUnit.SECONDS);
            }
        }
        play = !play;
        first = false;
    }

    public static String formatTime(double sec) {
        int seconds = (int) sec;
        int hours = 0;
        int minutes = 0;
        if (seconds >= 60) {
            minutes = seconds / 60;
            seconds = seconds % 60;
        }
        if (minutes >= 60) {
            hours = minutes / 60;
            minutes = minutes % 60;
        }
        String time = "";
        if (hours > 0) {
            time += String.format("%02d", hours) + ":";
        }
        time += String.format("%02d", minutes) + ":";
        time += String.format("%02d", seconds);
        return time;
    }
}
