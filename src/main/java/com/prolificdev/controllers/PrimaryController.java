package com.prolificdev.controllers;

import java.io.IOException;

import com.prolificdev.App;
import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
