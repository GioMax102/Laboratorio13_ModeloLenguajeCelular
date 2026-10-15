package mx.tec.redacta

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/** Donde Hilt arma el contenedor de la app (Práctica 9). Declarada en el manifiesto con `android:name`. */
@HiltAndroidApp
class RedactaApplication : Application()
