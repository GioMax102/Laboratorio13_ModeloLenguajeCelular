Ejercicio 0

Para cada app, decidan dónde correría el modelo y por qué: (a) sugerir respuestas en un chat privado; (b) resumir un PDF de 80 páginas; (c) traducir carteles en un viaje sin datos; (d) un asistente que te dice si va a llover hoy.

- (a) En el dispositivo (local).
  Por privacidad, para que los mensajes personales no salgan del teléfono ni pasen por servidores externos. Además ofrece baja latencia para respuestas inmediatas.

- (b) En la nube (servidor).
  Porque procesar 80 páginas requiere una ventana de contexto enorme y alta capacidad de cómputo/memoria que sobrepasa los límites de un celular.

- (c) En el dispositivo (local).
  Porque al ser un viaje sin conexión a datos o internet, la app debe funcionar de forma offline directamente en el hardware del teléfono.

- (d) En la nube (servidor).
  Porque requiere consultar datos meteorológicos y APIs del clima en tiempo real, lo cual depende obligatoriamente de una conexión a internet.
C1

¿Por qué no obedeció? Prueben las tres tareas y anoten qué sobra. ¿Qué tendría que decir el pedido para que no sobre?



- Sobran introducciones como "Aquí hay...", opciones numeradas, comillas y negritas. El modelo no falla, sino que actúa como en un chat de ayuda ofreciendo opciones (la conversación más probable). Al ser un modelo pequeño de 1B, no adivina intenciones; para que no sobre nada, hay que darle instrucciones exactas y mostrarle ejemplos de cómo debe responder.





C3

El límite de un modelo de 1B. Lee la respuesta con cuidado: ¿dice lo mismo que el original? Busca una frase que no venga del texto. ¿De dónde salió?



- A veces el modelo inventa información o copia frases de los ejemplos ocultos que se le dieron en las instrucciones (como el tema de las prórrogas). Por esto, un modelo de 1B es útil para generar borradores, pero siempre debe ser revisado por un humano antes de enviarse, especialmente en tareas donde los errores cuestan caro.