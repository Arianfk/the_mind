package GUI;

import Connection.ConnectionHandler;
import Connection.JsonRoom;
import Connection.Message;
import Logic.Controller.GameCardsInformation;
import Logic.Model.Game.game;
import Logic.Model.Players.Player;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;

public class ControllerTest {
    private static final byte[][] emojis = {new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x81}, new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0xAD}, new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x90}};
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
    private Label LastPlayedCardLabel;
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
        JsonRoom[] rooms = gson.fromJson(new String(connectionHandler.waitForMessage().getBody()), JsonRoom[].class);
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
            LastPlayedCardLabel.setText("no one has played yet");
            return;
        }
        LastPlayedCardLabel.setText(lastCard);


    }


}
