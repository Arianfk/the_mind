package GUI;

import Connection.ConnectionHandler;
import Connection.Message;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

import java.io.IOException;
import java.net.Socket;

public class ControllerTest {
    private static final String[] emojis = {"\\uD83D\\uDE00", "\\uD83D\\uDE42", "\\uD83D\\uDE15"};
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

    private Socket socket;
    private ConnectionHandler connectionHandler;
    @FXML
    private MenuButton emojiMenu;
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

        for (String emoji : emojis) {
            MenuItem menuItem = new MenuItem(emoji);
            menuItem.setOnAction(emojiAction);
            emojiMenu.getItems().add(menuItem);
        }
    }


}
