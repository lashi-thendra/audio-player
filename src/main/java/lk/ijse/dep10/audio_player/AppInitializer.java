package lk.ijse.dep10.audio_player;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.io.File;
import java.text.FieldPosition;

public class AppInitializer extends Application {


    MediaPlayer mediaPlayer;
    Media media;
    File audioFile;
    double durationMillis;
    double currentDuration;
    boolean isPlaying =false;
    double volumeValue = 0;
    int durationSliderValue = 0;
    String strPathDisplay;
    int stringLength;
    int cycleCount=0;

    int loopCount = 1;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        mainInterface(primaryStage);
        primaryStage.show();
    }

    private void mainInterface(Stage stage){

        Font font = new Font(20);
        Button btnBrowse = new Button("Browse");
        btnBrowse.setFont(new Font(15));
        btnBrowse.setMaxWidth(Double.MAX_VALUE);

        ImageView fileOpener = new ImageView("file_opener.png");
        fileOpener.setFitHeight(40);
        fileOpener.setFitWidth(40);

        Label textField = new Label(" Select an audio file..");
        textField.setFont(font);
        textField.setTextFill(Color.BLUE);
        textField.setPadding(new Insets(0,0,0,0));
        textField.setBackground(Background.fill(Color.BLACK));
        textField.setPrefHeight(20);
        textField.setPrefWidth(400);
        textField.setAlignment(Pos.BASELINE_LEFT);


        HBox hBox1 = new HBox(20, fileOpener, textField);
//        hBox1.setBackground(Background.fill(Color.YELLOW));
        hBox1.setMaxWidth(Double.MAX_VALUE);
        hBox1.setPadding(new Insets(20,0,30,0));
        hBox1.setAlignment(Pos.CENTER);


        Label currentTime = new Label("00.00");
        currentTime.setFont(font);
        currentTime.setTextFill(Color.LIGHTBLUE);
        Label totalTime = new Label("00.00");
        totalTime.setFont(font);
        totalTime.setTextFill(Color.LIGHTBLUE);
        Slider slider = new Slider();
        slider.setPrefWidth(375);

        HBox hBox2 = new HBox(10,currentTime, slider, totalTime);
//        hBox2.setBackground(Background.fill(Color.GREEN));
        hBox2.setPrefHeight(50);
        hBox2.setMaxWidth(Double.MAX_VALUE);
        hBox2.setPadding(new Insets(10,0,0,0));
        hBox2.setAlignment(Pos.CENTER);

        Image playOnHover = new Image("play_on.png");
        Image playClick = new Image("play_click.png");
        Image pauseOnHover = new Image("pause_hower.png");
        Image pauseClick = new Image("pause_click.png");
        Image stopOnHover = new Image("stop_hower.png");
        Image stopClick = new Image("stop_click.png");
        Image playNormal = new Image("play_normal.png");
        Image pauseNormal = new Image("pause_noraml.png");
        Image stopNormal = new Image("stop_noraml.png");


        ImageView backGround = new ImageView("background.jpg");
        backGround.setFitHeight(400);
        backGround.setFitWidth(600);
        ImageView play = new ImageView(playNormal);
        play.setFitWidth(50);
        play.setFitHeight(50);
        ImageView pause = new ImageView(pauseNormal);
        pause.setFitWidth(50);
        pause.setFitHeight(50);
        ImageView stop = new ImageView(stopNormal);
        stop.setFitWidth(50);
        stop.setFitHeight(50);
        Label labelCount = new Label("x"+loopCount);
        labelCount.setFont(new Font(20));
        labelCount.setPadding(new Insets(10,0,0,0));
        labelCount.setTextFill(Color.LIGHTBLUE);
        labelCount.setAlignment(Pos.BASELINE_CENTER);
        labelCount.setPrefWidth(30);
        HBox hBoxControllers = new HBox(15,play, pause, stop, labelCount);

        Image speakerOffHover = new Image("speaker_off_hower.png");
        Image speakerOffClicked = new Image("speaker_off_click.png");
        Image speakerOff = new Image("speaker_off_normal.png");
        Image speakerOnHover = new Image("speaker_on_hower.png");
        Image speakerOnClicked = new Image("speaker_on_click.png");
        Image speakerOn = new Image("speaker_on_normal.png");

        ImageView speaker = new ImageView(speakerOn);
        speaker.setFitWidth(40);
        speaker.setFitHeight(40);
        Slider volume = new Slider();
        volume.setValue(50);
        volume.setPadding(new Insets(10,0,0,0));
        HBox hBoxSounds = new HBox(10, speaker, volume);

        HBox hBox3 = new HBox(50,hBoxControllers, hBoxSounds);
//        hBox3.setBackground(Background.fill(Color.RED));
        hBox3.setPrefHeight(100);
        hBox3.setMaxWidth(Double.MAX_VALUE);
        hBox3.setAlignment(Pos.CENTER);
        hBox3.setPadding(new Insets(20,0,0,0));

        VBox vBox = new VBox(hBox1, hBox2, hBox3);
        vBox.setSpacing(40);
        vBox.setPadding(new Insets(40));

        StackPane root = new StackPane(backGround, vBox);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setHeight(400);
        stage.setWidth(600);

        btnBrowse.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select an audio file.");
            audioFile = fileChooser.showOpenDialog(null);
            if( audioFile != null){
                mediaPlayer = new MediaPlayer(media = new Media(audioFile.toURI().toString()));
                mediaPlayer.setOnReady(()->{
                    mediaPlayer.setVolume(0.5);
                    textField.setText(strPathDisplay = ("File loaded from: " + audioFile.toURI()+"   "));
                    stringLength = strPathDisplay.length();
                    durationMillis = media.getDuration().toMillis();
                    long minutes = (long) (durationMillis / 1000) / 60;
                    long seconds =(long) (durationMillis / 1000) % 60;
                    String duration = minutes +"." + seconds;
                    totalTime.setText(duration);
                });
                mediaPlayer.currentTimeProperty().addListener((value, previousValue, currentValue)->{
                    currentDuration = currentValue.toMillis();
                    durationSliderValue = (int)((currentDuration)/durationMillis*100);
                    slider.setValue(durationSliderValue);
                    if(durationSliderValue >= 99) {
                        play.setImage(playNormal);
                        mediaPlayer.stop();
                    }
                    long minutes = (long) (currentDuration / 1000) / 60;
                    long seconds =(long) (currentDuration / 1000) % 60;
                    String duration = minutes +"." + seconds;
                    currentTime.setText(duration);

                    if(cycleCount++ == 1){
                        cycleCount = 0;
                        strPathDisplay = strPathDisplay.substring(1,stringLength-1) + strPathDisplay.charAt(0);
                        System.out.println(strPathDisplay);
                        textField.setText(strPathDisplay);
                    }
                });
            }
        });

        play.setCursor(Cursor.HAND);
        play.setOnMouseEntered(event ->{
            play.setImage(playOnHover);
        });
        play.setOnMouseExited(event -> {
            if(isPlaying) play.setImage(playOnHover);
            else play.setImage(playNormal);
        });
        play.setOnMousePressed(event -> {
            play.setImage(playClick);
            play.setOpacity(0.5);
        });
        play.setOnMouseReleased(event -> {
            isPlaying = true;
            mediaPlayer.play();
            play.setImage(playOnHover);
            play.setOpacity(1);
        });

        pause.setCursor(Cursor.HAND);
        pause.setOnMouseEntered(event ->{
            pause.setImage(pauseOnHover);
        });
        pause.setOnMouseExited(event -> {
            pause.setImage(pauseNormal);
        });
        pause.setOnMousePressed(event -> {
            pause.setImage(pauseClick);
            pause.setOpacity(0.5);
        });
        pause.setOnMouseReleased(event -> {
            isPlaying = false;
            play.setImage(playNormal);
            mediaPlayer.pause();
            pause.setImage(pauseOnHover);
            pause.setOpacity(1);
        });

        stop.setCursor(Cursor.HAND);
        stop.setOnMouseEntered(event ->{
            stop.setImage(stopOnHover);
        });
        stop.setOnMouseExited(event -> {
            stop.setImage(stopNormal);
        });
        stop.setOnMousePressed(event -> {
            stop.setImage(stopClick);
            stop.setOpacity(0.5);
        });
        stop.setOnMouseReleased(event -> {
            isPlaying = false;
            play.setImage(playNormal);
            mediaPlayer.stop();
            stop.setImage(stopOnHover);
            stop.setOpacity(1);
        });

        speaker.setCursor(Cursor.HAND);
        speaker.setOnMouseEntered(event->{
            if(mediaPlayer.isMute()) {
                speaker.setImage(speakerOffHover);
            }else{
                speaker.setImage(speakerOnHover);
            }
        });
        speaker.setOnMouseExited(event->{
            if(mediaPlayer.isMute()) {
                speaker.setImage(speakerOff);
            }else{
                speaker.setImage(speakerOn);
            }
        });
        speaker.setOnMousePressed(event->{
            if(mediaPlayer.isMute()) {
                speaker.setImage(speakerOffClicked);
                speaker.setOpacity(0.5);
            }else{
                speaker.setImage(speakerOnClicked);
                speaker.setOpacity(0.5);
            }
        });
        speaker.setOnMouseReleased(event->{
            if(mediaPlayer.isMute()) {
                mediaPlayer.setMute(false);
                speaker.setImage(speakerOnHover);
                speaker.setOpacity(1);
                volume.setValue(volumeValue*100);
            }else{
                speaker.setImage(speakerOffHover);
                volumeValue = mediaPlayer.getVolume();
                mediaPlayer.setMute(true);
                speaker.setOpacity(1);
                volume.setValue(0);

            }
        });

        volume.valueProperty().addListener((value, previousValue, currentValue)->{
            mediaPlayer.setVolume(currentValue.doubleValue()/100);
            if(currentValue.doubleValue() == 0){
                mediaPlayer.setMute(true);
                speaker.setImage(speakerOff);
            }else{
                mediaPlayer.setMute(false);
                speaker.setImage(speakerOn);
            }
        });

        fileOpener.setOnMouseEntered(event->{
            fileOpener.setOpacity(0.5);
            fileOpener.setCursor(Cursor.HAND);
        });
        fileOpener.setOnMouseExited(event->{
            fileOpener.setOpacity(1);
        });
        fileOpener.setOnMousePressed(mouseEvent -> {
            btnBrowse.fire();
        });


        slider.valueProperty().addListener((value, previousValues, currentValue)->{
//            mediaPlayer.seek(Duration.millis((currentValue.doubleValue()/100)* durationMillis));
        });

        labelCount.setCursor(Cursor.HAND);
        labelCount.setOnMouseClicked(event->{
            if(loopCount++ == 4) loopCount = 1;
            if(loopCount == 4){
                labelCount.setText("\u27B0");
            }else{
                labelCount.setText("x"+loopCount);
            }
        });


    }
}
