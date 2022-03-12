package cn.xanderye;

import cn.xanderye.ext.JavaFxWindow;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;

/**
 * @author XanderYe
 * @date 2020/2/6
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Main.fxml"));
        primaryStage.setTitle("空白背景音");
        primaryStage.setScene(new Scene(root, 400, 150));
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.iconifiedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                Platform.runLater(primaryStage::hide);
            }
        });
        primaryStage.setOnCloseRequest(event -> System.exit(0));

        Platform.setImplicitExit(false);
        URL url = Thread.currentThread().getContextClassLoader().getResource("favicon.png");
        MenuItem open = new MenuItem("打开");
        open.addActionListener(e -> JavaFxWindow.showStage(primaryStage));
        MenuItem close = new MenuItem("关闭");
        close.addActionListener(e -> System.exit(0));
        JavaFxWindow.initTray(url, "打开窗口", new MenuItem[]{open, close}, new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JavaFxWindow.showStage(primaryStage);
                }
            }
        });
    }
}
