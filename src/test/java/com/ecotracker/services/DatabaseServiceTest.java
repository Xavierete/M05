package com.ecotracker.services;

import com.ecotracker.models.Activitat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DatabaseServiceTest {
    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @Mock
    private Statement mockStatement;
    @Mock
    private ResultSet mockResultSet;

    private DatabaseService databaseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        databaseService = new DatabaseService();
    }

    @Test
    void saveActivitat_ShouldSaveSuccessfully() throws SQLException {
        // Arrange
        Activitat activitat = new Activitat(
            "Test Activitat",
            LocalDate.now(),
            "Test Categoria",
            "Test Descripció",
            1.5
        );

        when(mockConnection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS)))
            .thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(1)).thenReturn(1);

        // Act
        databaseService.saveActivitat(activitat);

        // Assert
        verify(mockPreparedStatement).setString(1, activitat.getNom());
        verify(mockPreparedStatement).setDate(2, Date.valueOf(activitat.getData()));
        verify(mockPreparedStatement).setString(3, activitat.getCategoria());
        verify(mockPreparedStatement).setString(4, activitat.getDescripcio());
        verify(mockPreparedStatement).setDouble(5, activitat.getCo2Estalviat());
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    void getAllActivitats_ShouldReturnList() throws SQLException {
        // Arrange
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("nom")).thenReturn("Test Activitat");
        when(mockResultSet.getDate("data")).thenReturn(Date.valueOf(LocalDate.now()));
        when(mockResultSet.getString("categoria")).thenReturn("Test Categoria");
        when(mockResultSet.getString("descripcio")).thenReturn("Test Descripció");
        when(mockResultSet.getDouble("co2_estalviat")).thenReturn(1.5);

        // Act
        List<Activitat> activitats = databaseService.getAllActivitats();

        // Assert
        assertNotNull(activitats);
        assertEquals(1, activitats.size());
        assertEquals("Test Activitat", activitats.get(0).getNom());
        assertEquals("Test Categoria", activitats.get(0).getCategoria());
        assertEquals(1.5, activitats.get(0).getCo2Estalviat());
    }

    @Test
    void deleteActivitat_ShouldDeleteSuccessfully() throws SQLException {
        // Arrange
        int id = 1;
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        // Act
        databaseService.deleteActivitat(id);

        // Assert
        verify(mockPreparedStatement).setInt(1, id);
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    void getTotalCo2Estalviat_ShouldReturnTotal() throws SQLException {
        // Arrange
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getDouble("total")).thenReturn(10.5);

        // Act
        double total = databaseService.getTotalCo2Estalviat();

        // Assert
        assertEquals(10.5, total);
    }
} 