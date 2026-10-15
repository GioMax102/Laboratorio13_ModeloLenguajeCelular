package mx.tec.redacta.ui.state

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import mx.tec.redacta.domain.Tarea

/**
 * El ViewModel de la pantalla. Lo que no depende del modelo ya funciona: el
 * texto, los ejemplos, los chips. Lo demás lo llenas en los Bloques A y B.
 */
@HiltViewModel
class RedactaViewModel @Inject constructor() : ViewModel() {

    private val _ui = MutableStateFlow(RedactaUiState())
    val ui: StateFlow<RedactaUiState> = _ui.asStateFlow()

    /** Busca el archivo y lo carga. Es también el botón «Ya lo copié: buscar otra vez». */
    fun buscarModelo() {
        // A3
    }

    fun alCambiarTexto(texto: String) = _ui.update { it.copy(texto = texto) }

    fun elegirTarea(tarea: Tarea) = _ui.update { it.copy(tarea = tarea) }

    /** «Usar este texto»: la respuesta pasa a ser tu texto, para pedirle otra cosa. */
    fun usarRespuesta(texto: String) = _ui.update { it.copy(texto = texto, respuesta = null) }

    fun redactar() {
        // B2
    }

    fun detener() {
        // B2
    }
}
