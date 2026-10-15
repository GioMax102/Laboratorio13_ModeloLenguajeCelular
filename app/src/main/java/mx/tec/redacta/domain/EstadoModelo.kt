package mx.tec.redacta.domain

/** En qué va el modelo. La app dibuja una pantalla distinta para cada caso. */
sealed interface EstadoModelo {
    /** El archivo no está donde la app lo busca. `ruta` es dónde debería estar. */
    data class Falta(val ruta: String) : EstadoModelo

    /** El archivo está y se está subiendo a memoria: de 5 a 20 segundos. */
    data object Cargando : EstadoModelo

    data object Listo : EstadoModelo

    /** El archivo está, pero no se pudo cargar: incompleto, de otro formato o sin memoria. */
    data class Error(val mensaje: String) : EstadoModelo
}
