package mx.tec.redacta.domain

/**
 * Lo que se le manda al modelo.
 *
 * `instruccion` es la instrucción de sistema: el modelo la lee antes que nada.
 * `ejemplo` es una pregunta con su respuesta ya resuelta, como si el modelo la
 * hubiera contestado antes: a un modelo chico le enseña el formato mejor que
 * cualquier explicación.
 * `temperatura` es cuánto se arriesga al elegir cada palabra. null = la del modelo.
 */
data class Pedido(
    val mensaje: String,
    val instruccion: String? = null,
    val ejemplo: Ejemplo? = null,
    val temperatura: Double? = null
)

data class Ejemplo(val pregunta: String, val respuesta: String)
