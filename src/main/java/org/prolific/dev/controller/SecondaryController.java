package org.prolific.dev.controller;

import java.io.IOException;
import javafx.fxml.FXML;
import org.prolific.dev.App;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}