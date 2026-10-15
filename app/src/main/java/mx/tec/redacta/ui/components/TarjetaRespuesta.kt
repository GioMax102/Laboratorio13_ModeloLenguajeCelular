package mx.tec.redacta.ui.components

import android.content.ClipData
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.toClipEntry
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import mx.tec.redacta.R
import mx.tec.redacta.ui.state.Respuesta
import mx.tec.redacta.ui.theme.RedactaTema

/**
 * Lo que contesta el modelo. Crece mientras llega, con un cursor al final; al
 * terminar aparecen los botones. En «3 respuestas», una tarjeta por opción.
 */
@Composable
fun TarjetaRespuesta(
    respuesta: Respuesta,
    onUsar: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val c = MaterialTheme.colorScheme
    val e = RedactaTema.espaciado
    val portapapeles = LocalClipboard.current
    val alcance = rememberCoroutineScope()
    val copiar: (String) -> Unit = { texto ->
        alcance.launch { portapapeles.setClipEntry(ClipData.newPlainText("Redacta", texto).toClipEntry()) }
    }
    ElevatedCard(
        modifier = modifier.fillMaxWidth().animateContentSize(),
        colors = CardDefaults.elevatedCardColors(containerColor = c.surfaceContainerLowest)
    ) {
        Column(Modifier.padding(e.lg), verticalArrangement = Arrangement.spacedBy(e.md)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(e.sm)) {
                Box(Modifier.size(32.dp).background(c.primaryContainer, CircleShape), contentAlignment = Alignment.Center) {
                    Icon(painterResource(R.drawable.ic_chispa), contentDescription = null, tint = c.onPrimaryContainer, modifier = Modifier.size(18.dp))
                }
                Text(respuesta.tarea.etiqueta, style = MaterialTheme.typography.titleSmall, modifier = Modifier.weight(1f))
                respuesta.tokensPorSegundo?.let { Velocidad(it, respuesta.tokens) }
            }
            when {
                respuesta.leyendo -> Leyendo()
                respuesta.opciones.isNotEmpty() -> respuesta.opciones.forEach { Opcion(it, onCopiar = { copiar(it) }) }
                else -> TextoConCursor(respuesta.texto, conCursor = respuesta.generando)
            }
            respuesta.error?.let {
                Text(it, style = MaterialTheme.typography.bodyMedium, color = c.error)
            }
            if (!respuesta.generando && respuesta.texto.isNotBlank()) {
                Text(
                    "Revísalo antes de usarlo: un modelo así de chico a veces cambia el sentido.",
                    style = MaterialTheme.typography.bodySmall,
                    color = c.onSurfaceVariant
                )
            }
            if (!respuesta.generando && respuesta.texto.isNotBlank() && respuesta.opciones.isEmpty()) {
                Row(horizontalArrangement = Arrangement.spacedBy(e.sm)) {
                    TextButton(onClick = { copiar(respuesta.texto) }) {
                        Icon(painterResource(R.drawable.ic_copiar), contentDescription = null, modifier = Modifier.size(ButtonDefaults.IconSize))
                        Spacer(Modifier.width(ButtonDefaults.IconSpacing))
                        Text("Copiar")
                    }
                    FilledTonalButton(onClick = { onUsar(respuesta.texto.trim()) }) {
                        Icon(painterResource(R.drawable.ic_usar), contentDescription = null, modifier = Modifier.size(ButtonDefaults.IconSize))
                        Spacer(Modifier.width(ButtonDefaults.IconSpacing))
                        Text("Usar este texto")
                    }
                }
            }
        }
    }
}

@Composable
private fun TextoConCursor(texto: String, conCursor: Boolean) {
    val c = MaterialTheme.colorScheme
    val parpadeo by rememberInfiniteTransition(label = "cursor").animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(tween(500, easing = LinearEasing), RepeatMode.Reverse),
        label = "cursor"
    )
    val contenido = buildAnnotatedString {
        append(texto.trim())
        if (conCursor) withStyle(SpanStyle(color = c.primary.copy(alpha = parpadeo))) { append(" ▍") }
    }
    Text(contenido, style = MaterialTheme.typography.bodyLarge)
}

/** Antes del primer token: el modelo está leyendo tu texto. Tres puntos que respiran. */
@Composable
private fun Leyendo() {
    val e = RedactaTema.espaciado
    val c = MaterialTheme.colorScheme
    val fase by rememberInfiniteTransition(label = "puntos").animateFloat(
        initialValue = 0f,
        targetValue = 3f,
        animationSpec = infiniteRepeatable(tween(1200, easing = LinearEasing)),
        label = "puntos"
    )
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(e.xs)) {
        repeat(3) { i ->
            Box(Modifier.size(8.dp).alpha(if (fase.toInt() == i) 1f else 0.3f).background(c.primary, CircleShape))
        }
        Spacer(Modifier.width(e.sm))
        Text("Leyendo tu texto…", style = MaterialTheme.typography.bodyMedium, color = c.onSurfaceVariant)
    }
}

/** Una de las tres respuestas. Tocarla la copia. */
@Composable
private fun Opcion(texto: String, onCopiar: () -> Unit) {
    val e = RedactaTema.espaciado
    Surface(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onCopiar),
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colorScheme.secondaryContainer
    ) {
        Row(Modifier.padding(horizontal = e.lg, vertical = e.md), verticalAlignment = Alignment.CenterVertically) {
            Text(texto, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.weight(1f))
            Spacer(Modifier.width(e.sm))
            Icon(painterResource(R.drawable.ic_copiar), contentDescription = "Copiar", modifier = Modifier.size(18.dp))
        }
    }
}

/** Qué tan rápido escribe el modelo en ESTE teléfono. */
@Composable
private fun Velocidad(tokensPorSegundo: Double, tokens: Int) {
    val c = MaterialTheme.colorScheme
    val e = RedactaTema.espaciado
    Text(
        text = "${formatoVelocidad(tokensPorSegundo)} tok/s · $tokens",
        style = MaterialTheme.typography.labelMedium,
        color = c.onTertiaryContainer,
        modifier = Modifier.background(c.tertiaryContainer, CircleShape).padding(horizontal = e.sm, vertical = e.xs)
    )
}
