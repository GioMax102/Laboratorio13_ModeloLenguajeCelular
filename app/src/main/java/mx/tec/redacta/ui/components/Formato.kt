package mx.tec.redacta.ui.components

import java.util.Locale

/** 13.5 en vez de 13.461538: un decimal basta para comparar teléfonos. */
fun formatoVelocidad(tokensPorSegundo: Double): String =
    String.format(Locale.ROOT, "%.1f", tokensPorSegundo)
