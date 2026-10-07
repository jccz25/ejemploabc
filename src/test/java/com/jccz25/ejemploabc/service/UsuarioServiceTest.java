package com.jccz25.ejemploabc.service;

import com.jccz25.ejemploabc.model.Usuario;
import com.jccz25.ejemploabc.repository.UsuarioRepository;
import com.jccz25.ejemploabc.service.UsuarioService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuarioEjemplo;

    @BeforeEach
    void setUp() {
        usuarioEjemplo = Usuario.builder()
                .id(1L)
                .nombre("Juan Carlos")
                .apellidoPaterno("Cardona")
                .apellidoMaterno("Zárate")
                .email("juan@ejemplo.com")
                .build();
    }

    @Test
    @DisplayName("Debe listar todos los usuarios registrados")
    void obtenerTodos_debeRetornarListaUsuarios() {
        // Arrange
        when(usuarioRepository.findAll()).thenReturn(List.of(usuarioEjemplo));

        // Act
        List<Usuario> resultado = usuarioService.obtenerTodos();

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Juan Carlos", resultado.get(0).getNombre());
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe guardar un usuario (Alta)")
    void guardarUsuario_debeGuardarYRetornarUsuario() {
        // Arrange
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioEjemplo);

        // Act
        Usuario creado = usuarioService.guardarUsuario(usuarioEjemplo);

        // Assert
        assertNotNull(creado);
        assertEquals("Juan Carlos", creado.getNombre());
        verify(usuarioRepository, times(1)).save(usuarioEjemplo);
    }

    @Test
    @DisplayName("Debe eliminar un usuario por ID (Baja)")
    void eliminarUsuario_debeLlamarRepositoryDelete() {
        // Arrange
        doNothing().when(usuarioRepository).deleteById(1L);

        // Act
        usuarioService.eliminarUsuario(1L);

        // Assert
        verify(usuarioRepository, times(1)).deleteById(1L);
    }
}