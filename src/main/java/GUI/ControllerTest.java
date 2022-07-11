package GUI;

import Connection.ConnectionHandler;
import Connection.JsonRoom;
import Connection.Message;
import Connection.MessageReceiveListener;
import Logic.Controller.GameCardsInformation;
import Logic.Model.Game.game;
import Logic.Model.Players.Player;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ControllerTest {
    private static final byte[][] emojis = {new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x81}, new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0xAD}, new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x90}};
    public Button startButton;
    public Button playButton;
    public Button ninjaRequestButton;
    private boolean isHost;
    private Socket socket;
    private game gamePlayerIdPlaying;
    private ConnectionHandler connectionHandler;
    @FXML
    private MenuButton emojiMenu;
    private final EventHandler<ActionEvent> emojiAction = new EventHandler<>() {
        @Override
        public void handle(ActionEvent event) {
            String emoji = null;
            for (MenuItem item : emojiMenu.getItems()) {
                if (item == event.getSource()) {
                    emoji = ((Label) item.getGraphic()).getText();
                    break;
                }
            }

            connectionHandler.sendWithAT(new Message(emoji, (byte) 1));
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
    private final EventHandler<ActionEvent> chooseCardAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            String chosenNumber = null;
            for (String x : yourCards.getItems()) {
                if (x.equals(event.getSource())) {
                    chosenNumber = x;
                    break;
                }
            }
            connectionHandler.sendWithAT(new Message(chosenNumber, (byte) 2));
        }
    };

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

        connectionHandler.sendMessage(new Message(result.get()));

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

        connectionHandler.setMessageReceiveListener(new MessageReceiveListener() {
            @Override
            public void onMessageReceived(Message message) {
                switch (message.getHeader()) {
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
                        Platform.runLater(() -> heartNumberLabel.setText("Hearts: " + new String(message.getBody())));
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

                    }
                }
            }
        });

        connectionHandler.start();
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


    public void setEveryThing(game game, Player player) {
        setEmojis();
        setGameCardsInformation(game);
        setHeartNumberLabel(game);
        setLastPlayedCardLabel(game);
        setNinjaNumberLabel(game);
        setYourCards(player);
    }

    public void setHeartNumberLabel(game game) {
        heartNumberLabel.setText("number of hearts : " + game.getNumberOfHeartCards());
    }

    public void setNinjaNumberLabel(game game) {
        ninjaNumberLabel.setText("number of ninja cads : " + game.getNumberOfNinjaCards());
    }

    public void setGameCardsInformation(game game) {
        GameCardsInformation info = new GameCardsInformation(game);
        ArrayList<String> information = info.getGameCardsInformation();
        this.gameCardsInformation.setItems(FXCollections.observableArrayList(information));
    }

    public void setYourCards(Player player) {
        LinkedList<String> cardsNumber = player.getNumericalCardsNumber();
        this.yourCards.setItems(FXCollections.observableArrayList(cardsNumber));
        this.yourCards.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }


    public void setLastPlayedCardLabel(game game) {
        String lastCard = game.getLastPlayedCardNumber();
        if (lastCard == null) {
            lastPlayedCardLabel.setText("no one has played yet");
            return;
        }
        lastPlayedCardLabel.setText(lastCard);


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
