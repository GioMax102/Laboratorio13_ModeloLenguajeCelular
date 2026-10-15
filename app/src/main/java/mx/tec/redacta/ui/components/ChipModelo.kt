package mx.tec.redacta.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import mx.tec.redacta.R
import mx.tec.redacta.domain.EstadoModelo
import mx.tec.redacta.ui.theme.RedactaTema

/**
 * En qué va el modelo, siempre a la vista. Va sobre el encabezado verde: por
 * eso usa los colores «on primary» y los contenedores claros del tema.
 *
 * El punto late mientras carga: algo está pasando, espera.
 */
@Composable
fun ChipModelo(estado: EstadoModelo, modifier: Modifier = Modifier) {
    val c = MaterialTheme.colorScheme
    val (texto, punto) = when (estado) {
        EstadoModelo.Listo -> "Gemma 3 1B · listo" to c.inversePrimary
        EstadoModelo.Cargando -> "Cargando el modelo…" to c.tertiaryContainer
        is EstadoModelo.Falta -> "Falta el modelo" to c.errorContainer
        is EstadoModelo.Error -> "El modelo no cargó" to c.errorContainer
    }
    val late = estado == EstadoModelo.Cargando
    val pulso by rememberInfiniteTransition(label = "pulso").animateFloat(
        initialValue = 1f,
        targetValue = if (late) 0.25f else 1f,
        animationSpec = infiniteRepeatable(tween(700, easing = LinearEasing), RepeatMode.Reverse),
        label = "pulso"
    )
    PildoraClara(modifier) {
        Box(Modifier.size(8.dp).alpha(pulso).background(punto, CircleShape))
        Text(texto, style = MaterialTheme.typography.labelMedium, color = c.onPrimary)
    }
}

/** Lo que vende la app: nada de lo que escribes sale del teléfono. */
@Composable
fun ChipSinInternet(modifier: Modifier = Modifier) {
    val c = MaterialTheme.colorScheme
    PildoraClara(modifier) {
        Icon(painterResource(R.drawable.ic_avion), contentDescription = null, tint = c.onPrimary, modifier = Modifier.size(14.dp))
        Text("Sin internet", style = MaterialTheme.typography.labelMedium, color = c.onPrimary)
    }
}

@Composable
private fun PildoraClara(modifier: Modifier, contenido: @Composable () -> Unit) {
    val e = RedactaTema.espaciado
    Row(
        modifier = modifier
            .background(MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.16f), CircleShape)
            .padding(horizontal = e.md, vertical = e.xs + 2.dp),
        horizontalArrangement = Arrangement.spacedBy(e.sm),
        verticalAlignment = Alignment.CenterVertically
    ) { contenido() }
}
