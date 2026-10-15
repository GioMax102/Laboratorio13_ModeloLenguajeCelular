package mx.tec.redacta.domain

/**
 * Lo que Redacta sabe hacer con tu texto. La `instruccion` va como
 * instrucción de sistema: el modelo la lee antes que tu texto.
 *
 * Así se le pediría a una persona. En el Bloque C vas a ver si a un modelo
 * de 1 000 millones de parámetros le basta.
 */
enum class Tarea(val etiqueta: String, val instruccion: String) {
    Amable("Más amable", "Haz este texto más amable."),
    Breve("Más corto", "Haz este texto más corto."),
    Responder("3 respuestas", "Sugiere tres respuestas para este mensaje.");

    fun pedidoPara(texto: String): Pedido = Pedido(mensaje = texto, instruccion = instruccion)
}
