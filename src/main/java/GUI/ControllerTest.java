package GUI;

import Connection.ConnectionHandler;
import Connection.JsonRoom;
import Connection.Message;
import Connection.MessageReceiveListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ControllerTest {
    private static final byte[][] emojis = {new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x81}, new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0xAD}, new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x90}};
    public Button startButton;
    public Button playButton;
    public Button ninjaRequestButton;
    public int ninjaState = 0;
    public Dialog<ButtonType> ninjaDialog;
    private Socket socket;
    private ConnectionHandler connectionHandler;
    @FXML
    private MenuButton emojiMenu;
    private final EventHandler<ActionEvent> emojiAction = new EventHandler<>() {
        @Override
        public void handle(ActionEvent event) {
            //String emoji = null;
            ObservableList<MenuItem> items = emojiMenu.getItems();
            for (int i = 0; i < items.size(); i++) {
                MenuItem item = items.get(i);
                if (item == event.getSource()) {
                    // emoji = ((Label) item.getGraphic()).getText();
                    connectionHandler.sendWithAT(new Message(String.valueOf(i), (byte) 0x08));
                    break;
                }
            }
        }
    };
    @FXML
    private Label heartNumberLabel;
    @FXML
    private Label ninjaNumberLabel;
    @FXML
    private Label lastPlayedCardLabel;
    @FXML
    private ListView<String> gameCardsInformation;
    @FXML
    private ListView<String> yourCards;

    @FXML
    public void initialize() {
        //Connection
        try {
            socket = new Socket("localhost", 80);
            connectionHandler = new ConnectionHandler(socket);
            connectionHandler.setAuthToken(connectionHandler.waitForMessage().getAuthToken());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Name
        Optional<String> result;
        do {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setHeaderText("Input Your Name: ");
            result = dialog.showAndWait();
        } while (result.isEmpty() || result.get().equals(""));

        connectionHandler.sendWithAT(new Message(result.get()));

        // Gson
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        // Room
        connectionHandler.sendWithAT(new Message((byte) 0x01));
        String json = new String(connectionHandler.waitForMessage().getBody());
        System.out.println(json);
        JsonRoom[] rooms = gson.fromJson(json, JsonRoom[].class);
        RoomDialog dialog = new RoomDialog(rooms);
        dialog.showAndWait();

        JsonRoom jsonRoom = dialog.getResult();
        boolean isHost;
        if (jsonRoom == null) {
            ChoiceDialog<Integer> choiceDialog = new ChoiceDialog<>(2, 2, 3, 4);
            choiceDialog.setHeaderText("Number Of Players: ");
            choiceDialog.showAndWait();

            connectionHandler.sendWithAT(new Message(String.valueOf(choiceDialog.getResult()), (byte) 0x02));
            isHost = true;
        } else {
            connectionHandler.sendWithAT(new Message(jsonRoom.getId(), (byte) 0x03));
            isHost = false;
        }

        if (!isHost) startButton.setVisible(false);

        setEmojis();
        connectionHandler.setMessageReceiveListener(new MessageReceiveListener() {
            @Override
            public void onMessageReceived(Message message) {
                switch (message.getHeader()) {
                    case 0x09 -> {
                        Platform.runLater(() -> {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setContentText("WIN!?");
                            alert.showAndWait();
                            try {
                                socket.close();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
                    }
                    case 0x01 -> {
                        Platform.runLater(() -> lastPlayedCardLabel.setText(new String(message.getBody())));
                    }
                    case 0x02 -> {
                        String json = new String(message.getBody());
                        Type setType = new TypeToken<HashSet<String>>() {
                        }.getType();
                        Set<String> cards = gson.fromJson(json, setType);

                        Platform.runLater(() -> yourCards.setItems(FXCollections.observableArrayList(cards)));
                    }
                    case 0x03 -> {
                        int count = Integer.parseInt(new String(message.getBody()));
                        Platform.runLater(() -> heartNumberLabel.setText("Hearts: " + new String(message.getBody())));
                        if (count == 0) {
                            Platform.runLater(() -> {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setContentText("LOSS!?");
                                alert.showAndWait();
                            });
                            close();
                        }
                    }
                    case 0x04 -> {
                        Platform.runLater(() -> ninjaNumberLabel.setText("Ninja: " + new String(message.getBody())));
                    }
                    case 0x05 -> {
                        Platform.runLater(() -> {
                            String json1 = new String(message.getBody());
                            Type listType = new TypeToken<ArrayList<String>>() {
                            }.getType();

                            ArrayList<String> list = gson.fromJson(json1, listType);
                            gameCardsInformation.setItems(FXCollections.observableArrayList(list));
                        });
                    }
                    case 0x06 -> {
                        Platform.runLater(() -> startButton.setVisible(true));
                    }
                    case 0x07 -> {
                        if (message.getBody().length == 0) {
                            Platform.runLater(() -> {
                                if (ninjaDialog != null) {
                                    ninjaDialog.setOnCloseRequest(null);
                                    ninjaDialog.getDialogPane().getScene().getWindow().setOnCloseRequest(null);
                                    ninjaDialog.close();
                                }
                            });
                            ninjaState = 0;
                        } else {
                            int state = Integer.parseInt(new String(message.getBody()));
                            if (state == 0) {
                                if (ninjaState != 1) {
                                    ninjaState = 1;
                                    Platform.runLater(() -> {
                                        ninjaDialog = new Dialog<>();
                                        ninjaDialog.setDialogPane(new DialogPane() {
                                            @Override
                                            protected Node createButton(ButtonType buttonType) {
                                                Button button = (Button) super.createButton(buttonType);
                                                button.setOnAction(event -> connectionHandler.sendWithAT(new Message((buttonType == ButtonType.YES ? "1" : "2"), (byte) 0x07)));
                                                return button;
                                            }
                                        });
                                        ninjaDialog.getDialogPane().getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
                                        ninjaDialog.setHeaderText("Do you want to use star card?");
                                        ninjaDialog.setOnCloseRequest(Event::consume);
                                        ninjaDialog.getDialogPane().getScene().getWindow().setOnCloseRequest(Event::consume);
                                        ninjaDialog.showAndWait();
                                    });
                                }
                            } else {
                                if (ninjaState != 2) {
                                    if (ninjaState == 1) {
                                        Platform.runLater(() -> {
                                            ninjaDialog.setOnCloseRequest(null);
                                            ninjaDialog.getDialogPane().getScene().getWindow().setOnCloseRequest(null);
                                            ninjaDialog.close();
                                        });
                                    }
                                    ninjaState = 2;
                                    Platform.runLater(() -> {
                                        ninjaDialog = new Dialog<>();
                                        ninjaDialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                                        ninjaDialog.setHeaderText("Waiting for other players...");
                                        ninjaDialog.getDialogPane().lookupButton(ButtonType.OK).setVisible(false);
                                        ninjaDialog.showAndWait();
                                    });
                                }
                            }
                        }
                    }
                    case 0x08 -> {
                        String tmp = new String(message.getBody());
                        String[] t = tmp.split("\n");
                        String name = t[0];
                        int emojiIndex = Integer.parseInt(t[1]);
                        Platform.runLater(() -> {
                            Dialog<ButtonType> emojiDialog = new Dialog<>();
                            Path url = Paths.get("src/main/resources/style.css");
                            try {
                                emojiDialog.getDialogPane().getScene().getStylesheets().add(url.toUri().toURL().toExternalForm());
                            } catch (MalformedURLException e) {
                                throw new RuntimeException(e);
                            }
                            emojiDialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
                            emojiDialog.setHeaderText(name);
                            Label label = new Label();
                            label.setLayoutX(45);
                            label.setLayoutY(10);
                            label.setText(new String(emojis[emojiIndex], StandardCharsets.UTF_8));
                            label.setId("emoji");
                            Pane pane = new Pane();
                            pane.getChildren().add(label);
                            emojiDialog.getDialogPane().setContent(pane);
                            emojiDialog.showAndWait();
                        });
                    }
                }
            }
        });

        connectionHandler.start();
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setEmojis() {
        for (byte[] emoji : emojis) {
            MenuItem menuItem = new MenuItem();
            Label label = new Label();
            label.setText(new String(emoji, StandardCharsets.UTF_8));
            label.setId("emoji");
            menuItem.setGraphic(label);
            menuItem.setOnAction(emojiAction);
            emojiMenu.getItems().add(menuItem);
        }
    }

    public void startButtonClicked() {
        connectionHandler.sendWithAT(new Message((byte) 0x04));
        startButton.setVisible(false);
    }

    public void onPlayButtonClicked() {
        connectionHandler.sendWithAT(new Message((byte) 0x05));
    }

    public void onNinjaRequest() {
        connectionHandler.sendWithAT(new Message((byte) 0x06));
    }
}
