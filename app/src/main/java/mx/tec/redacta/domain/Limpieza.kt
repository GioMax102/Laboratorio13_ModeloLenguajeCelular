package mx.tec.redacta.domain

/**
 * La respuesta, ya terminada, lista para mostrarse o copiarse. Gemma suele
 * dejar dos espacios después del punto y espacios de más al final.
 */
fun limpiar(texto: String): String = texto.trim().replace(ESPACIOS, " ")

private val ESPACIOS = Regex(" {2,}")
