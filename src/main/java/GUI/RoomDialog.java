package GUI;

import Connection.JsonRoom;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RoomDialog extends Dialog<JsonRoom> {
    JsonRoom[] rooms;

    public RoomDialog(JsonRoom[] rooms) {
        this.rooms = rooms;

        ButtonType newRoomButton = new ButtonType("New Room");
        getDialogPane().getButtonTypes().addAll(newRoomButton);

        Pane pane = new Pane();
        ListView<JsonRoom> listView = new ListView<>();
        listView.setPrefHeight(300);
        listView.setPrefWidth(200);
        listView.setPadding(new Insets(0));
        listView.setItems(FXCollections.observableArrayList(rooms));
        listView.setCellFactory(param -> new MyCell());
        pane.getChildren().add(listView);
        getDialogPane().setContent(pane);
        getDialogPane().setPadding(new Insets(15));

        setResultConverter(param -> null);
    }

    public class MyCell extends ListCell<JsonRoom> {
        private final Label nameLabel;
        private final Label idLabel;
        private final Button button;
        private final Pane pane;

        public MyCell() {
            Path url = Paths.get("src/main/resources/room_item.fxml");
            FXMLLoader loader;
            try {
                loader = new FXMLLoader(url.toUri().toURL());
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            try {
                pane = loader.load();
                nameLabel = (Label) pane.getChildren().get(0);
                idLabel = (Label) pane.getChildren().get(1);
                button = (Button) pane.getChildren().get(2);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        protected void updateItem(JsonRoom item, boolean empty) {
            super.updateItem(item, empty);
            if (!empty && item != null) {
                setGraphic(pane);
                nameLabel.setText(item.getHostName());
                idLabel.setText(item.getId());
                button.setOnAction(event -> {
                    RoomDialog.this.setResult(item);
                    RoomDialog.this.close();
                });
            } else {
                setGraphic(null);
            }
        }
    }
}
