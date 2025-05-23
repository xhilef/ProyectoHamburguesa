package burgervend.controllers;

import burgervend.models.Hamburguesa;
import burgervend.services.GestorPedidos;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import burgervend.utils.ScreenManager;
import burgervend.controllers.BurgerCellFactory;
import burgervend.utils.AppState;

public class SelectionController {
    @FXML private ListView<Hamburguesa> listHamburguesas;
    private final GestorPedidos gestorPedidos = new GestorPedidos();
    
    @FXML
    private void initialize() {
        // Cargar las hamburguesas base en la lista
        listHamburguesas.getItems().addAll(gestorPedidos.getHamburguesasBase());

        // Asignar la fábrica de celdas personalizada
        listHamburguesas.setCellFactory(BurgerCellFactory.create());
    }
    
    @FXML
    private void onPersonalizarClick(javafx.event.ActionEvent event) {
        // Obtener la hamburguesa seleccionada
        Hamburguesa seleccionada = listHamburguesas.getSelectionModel().getSelectedItem();
        
        // Verifica si hay una hamburguesa seleccionada
        if (seleccionada != null) {
            // cargar la pantalla de personalización y pasar la hamburguesa seleccionada
            ScreenManager.loadCustomizationScreen(seleccionada);
        }
    }
}
