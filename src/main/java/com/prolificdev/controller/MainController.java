package com.prolificdev.controller;

import com.prolificdev.App;
import javafx.fxml.FXML;

import java.io.IOException;

public class MainController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("chart");
    }
}
