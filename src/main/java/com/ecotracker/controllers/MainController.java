package com.ecotracker.controllers;

import com.ecotracker.models.Activitat;
import com.ecotracker.services.DatabaseService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MainController {
    @FXML private TextField nomField;
    @FXML private DatePicker dataPicker;
    @FXML private ComboBox<String> categoriaCombo;
    @FXML private TextArea descripcioArea;
    @FXML private TextField co2Field;
    @FXML private TableView<Activitat> activitatsTable;
    @FXML private TableColumn<Activitat, String> nomColumn;
    @FXML private TableColumn<Activitat, LocalDate> dataColumn;
    @FXML private TableColumn<Activitat, String> categoriaColumn;
    @FXML private TableColumn<Activitat, String> descripcioColumn;
    @FXML private TableColumn<Activitat, Double> co2Column;
    @FXML private TableColumn<Activitat, Void> accionsColumn;
    @FXML private Label totalCo2Label;

    private final DatabaseService databaseService;
    private final ObservableList<Activitat> activitats;

    public MainController() {
        this.databaseService = new DatabaseService();
        this.activitats = FXCollections.observableArrayList();
    }

    @FXML
    public void initialize() {
        // Inicialitzar categories
        categoriaCombo.setItems(FXCollections.observableArrayList(
            "Transport",
            "Energia",
            "Residus",
            "Aigua",
            "Altres"
        ));

        // Configurar columnes de la taula
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        dataColumn.setCellValueFactory(new PropertyValueFactory<>("data"));
        categoriaColumn.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        descripcioColumn.setCellValueFactory(new PropertyValueFactory<>("descripcio"));
        co2Column.setCellValueFactory(new PropertyValueFactory<>("co2Estalviat"));

        // Configurar columna d'accions
        accionsColumn.setCellFactory(col -> new TableCell<>() {
            private final Button deleteButton = new Button("Eliminar");
            {
                deleteButton.setOnAction(event -> {
                    Activitat activitat = getTableView().getItems().get(getIndex());
                    handleDelete(activitat);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : deleteButton);
            }
        });

        // Configurar format de data
        dataColumn.setCellFactory(col -> new TableCell<>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            @Override
            protected void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setText(empty ? null : formatter.format(date));
            }
        });

        // Configurar format de CO₂
        co2Column.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Double value, boolean empty) {
                super.updateItem(value, empty);
                setText(empty ? null : String.format("%.2f kg", value));
            }
        });

        // Carregar dades
        activitatsTable.setItems(activitats);
        refreshData();
    }

    @FXML
    private void handleSave() {
        try {
            String nom = nomField.getText();
            LocalDate data = dataPicker.getValue();
            String categoria = categoriaCombo.getValue();
            String descripcio = descripcioArea.getText();
            double co2Estalviat = Double.parseDouble(co2Field.getText());

            if (nom.isEmpty() || data == null || categoria == null) {
                showAlert("Error", "Si us plau, omple tots els camps obligatoris.");
                return;
            }

            Activitat activitat = new Activitat(nom, data, categoria, descripcio, co2Estalviat);
            databaseService.saveActivitat(activitat);
            refreshData();
            clearFields();
        } catch (NumberFormatException e) {
            showAlert("Error", "El valor de CO₂ ha de ser un número vàlid.");
        }
    }

    private void handleDelete(Activitat activitat) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar eliminació");
        alert.setHeaderText("Eliminar activitat");
        alert.setContentText("Estàs segur que vols eliminar aquesta activitat?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            databaseService.deleteActivitat(activitat.getId());
            refreshData();
        }
    }

    @FXML
    private void handleExportCSV() {
        exportToFile("CSV", "*.csv", this::formatCSV);
    }

    @FXML
    private void handleExportTXT() {
        exportToFile("TXT", "*.txt", this::formatTXT);
    }

    private void exportToFile(String format, String extension, ExportFormatter formatter) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exportar a " + format);
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter(format + " Files", extension)
        );

        File file = fileChooser.showSaveDialog(activitatsTable.getScene().getWindow());
        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(formatter.format(activitats));
            } catch (IOException e) {
                showAlert("Error", "No s'ha pogut exportar el fitxer: " + e.getMessage());
            }
        }
    }

    private String formatCSV(List<Activitat> activitats) {
        StringBuilder sb = new StringBuilder();
        sb.append("Nom,Data,Categoria,Descripció,CO₂ Estalviat\n");
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (Activitat a : activitats) {
            sb.append(String.format("%s,%s,%s,%s,%.2f\n",
                a.getNom(),
                formatter.format(a.getData()),
                a.getCategoria(),
                a.getDescripcio(),
                a.getCo2Estalviat()
            ));
        }
        return sb.toString();
    }

    private String formatTXT(List<Activitat> activitats) {
        StringBuilder sb = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        for (Activitat a : activitats) {
            sb.append(String.format("Activitat: %s\n", a.getNom()));
            sb.append(String.format("Data: %s\n", formatter.format(a.getData())));
            sb.append(String.format("Categoria: %s\n", a.getCategoria()));
            sb.append(String.format("Descripció: %s\n", a.getDescripcio()));
            sb.append(String.format("CO₂ Estalviat: %.2f kg\n", a.getCo2Estalviat()));
            sb.append("-------------------\n");
        }
        return sb.toString();
    }

    private void refreshData() {
        activitats.clear();
        activitats.addAll(databaseService.getAllActivitats());
        updateTotalCo2();
    }

    private void updateTotalCo2() {
        double total = databaseService.getTotalCo2Estalviat();
        totalCo2Label.setText(String.format("%.2f kg", total));
    }

    private void clearFields() {
        nomField.clear();
        dataPicker.setValue(null);
        categoriaCombo.setValue(null);
        descripcioArea.clear();
        co2Field.clear();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FunctionalInterface
    private interface ExportFormatter {
        String format(List<Activitat> activitats);
    }
} 