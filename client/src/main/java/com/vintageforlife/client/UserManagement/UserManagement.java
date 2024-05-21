package com.vintageforlife.client.UserManagement;

import com.vintageforlife.client.dto.UserDTO;
import com.vintageforlife.client.homepage.Homepage;
import com.vintageforlife.client.http.HttpService;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class UserManagement {
    private HttpService userService;
    private Homepage homepage;

    public void setUserService(HttpService userService) {
        this.userService = userService;
    }

    public void setHomepage(Homepage homepage) {
        this.homepage = homepage;
    }

    public Scene display(Stage primaryStage) {
        List<UserDTO> users = userService.getAllUsers();

        BorderPane root = new BorderPane();
        Label logoLabel = new Label("Vintage For Life");
        logoLabel.getStyleClass().add("label-vintage");

        // Create a button that navigates to the Homepage
        Button homepageButton = new Button("Homepage");
        homepageButton.getStyleClass().add("homepage-button");
        homepageButton.setOnAction(e -> primaryStage.setScene(homepage.display(primaryStage)));

        // Create a button that navigates to the CreateAccountPage
        Button createUserButton = new Button("Create User");
        createUserButton.getStyleClass().add("create-user-button");
        createUserButton.setOnAction(e -> {
            CreateAccountPage createAccountPage = new CreateAccountPage(UserManagement.this);
            Scene createScene = createAccountPage.createScene(primaryStage);
            primaryStage.setScene(createScene);
        });

        // TableView for displaying the user information and buttons
        TableView<UserDTO> usersTable = new TableView<>();

        TableColumn<UserDTO, String> nameColumn = new TableColumn<>("Naam");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<UserDTO, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<UserDTO, String> roleColumn = new TableColumn<>("Rol");
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        TableColumn<UserDTO, Void> editColumn = new TableColumn<>("Wijzigen");
        editColumn.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = createTextButton("\u2699", "Edit"); // Unicode voor een tandwiel (⚙)

            {
                editButton.setOnAction(event -> {
                    UserDTO user = getTableView().getItems().get(getIndex());
                    UserEditPage userEditPage = new UserEditPage(userService);
                    Scene editScene = userEditPage.display(primaryStage, user);
                    primaryStage.setScene(editScene);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(editButton);
                }
            }
        });

        TableColumn<UserDTO, Void> deleteColumn = new TableColumn<>("Verwijderen");
        deleteColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = createTextButton("\uD83D\uDDD1", "Delete"); // Unicode voor een prullenbak (🗑️)

            {
                deleteButton.setOnAction(event -> {
                    UserDTO user = getTableView().getItems().get(getIndex());
                    boolean success = userService.deleteUser(user.getId());
                    if (success) {
                        refreshUserTable(usersTable);
                    } else {
                        // Toon een foutmelding bij mislukte verwijdering
                        System.out.println("Failed to delete user.");
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });

        usersTable.getColumns().addAll(nameColumn, emailColumn, roleColumn, editColumn, deleteColumn);
        usersTable.getItems().addAll(users);

        root.setTop(logoLabel);
        root.setCenter(usersTable);

        HBox topBox = new HBox(10, homepageButton, createUserButton);
        VBox topContainer = new VBox(10, logoLabel, topBox);
        root.setTop(topContainer);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Vintage For Life");
        primaryStage.setScene(scene);
        primaryStage.show();

        scene.getStylesheets().add("/UserManagement.css");

        return scene;
    }

    private Button createTextButton(String text, String tooltipText) {
        Button button = new Button(text);
        button.setTooltip(new Tooltip(tooltipText));
        return button;
    }

    private void refreshUserTable(TableView<UserDTO> usersTable) {
        usersTable.getItems().clear();
        List<UserDTO> users = userService.getAllUsers();
        usersTable.getItems().addAll(users);
    }
}
