# challenge
Challenge Meli Repo

# Comentarios
La solución fue hecha en Spring Boot para simplificar el desarrollo de la misma.

En cuanto a las mejoras, la primera es el manejo de errores, al no tener la información necesaria sobre los posibles errores de la API lo que hice fue devolver justamente el mismo error que obtengo de la API proporcionada. Tambien se podria hacer un manejo de errores propio de esta nueva api y no devolver el error que viene de la api, de igual manera configure la aplicación para que no exponga en la respuesta el stack trace del error. Otra mejora podria ser en la clase MercadoLibreService en el metodo doGet que es quien se comunica con la API proporcionada, se podria implementar una tolerancia a fallos, por ejemplo con Resilience4j y hacer X cantidad de intentos de Y segundos.

En cuanto a la performance, se podría guardar las respuestas en cache, y cada vez que se consulte por un mismo item, devolver el valor del cache. Esto seria posible siempre y cuando se extienda la API y cuando se invoque a los metodos para actualizar el item, ahi se invalidaria la cache, para que luego cuando se consulte nuevamente por el item actualizado, se vuelva a hacer la solicitud a la API y no devolver el valor de cache.

En cuanto al testing, se podrian agregar pruebas de integración con datos de prueba proporcionados a través de la API de mercado libre, para poder validar de manera real todos los posibles escenarios de exito y fallas.
