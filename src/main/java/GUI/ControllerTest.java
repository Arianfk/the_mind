package GUI;

import Connection.ConnectionHandler;
import Connection.Message;
import Logic.Controller.GameCardsInformation;
import Logic.Model.Game.game;
import Logic.Model.Players.Player;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

public class ControllerTest {

    private static final String[] emojis = {"\\uD83D\\uDE00", "\\uD83D\\uDE42", "\\uD83D\\uDE15"};
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
                    emoji = item.getText();
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


        setEmojis();


    }


    public void setEmojis() {
        for (String emoji : emojis) {
            MenuItem menuItem = new MenuItem(emoji);
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
