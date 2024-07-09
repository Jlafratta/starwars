# Starwars API
StarWars API Project

### Requisitos:

1. **Integración con la API de Star Wars:**
<br><br>
    - [X] La aplicación debe conectarse a la API de Star Wars y obtener datos de People, Films, Starships y Vehicles.
    - Se realizo la integracion la API de StarWars de manera generica para un correcto funcionamiento.
    <hr>
2. **Listado de Entidades:**
<br><br>
    -  [X] Implementar funcionalidades para listar People, Films, Starships y Vehicles.
    -  [X] La lista debe ser paginada para manejar grandes cantidades de datos.
    - Se implemento la paginacion tanto para los endpoints que la aplicaban como para los que no (filtrados)
    <hr>
3. **Filtrado:**
<br><br>
    -  [X] Permitir el filtrado de resultados por ID y/o nombre.
    - Se implemento el filtrado por nombre y/o ID. En el caso de Films se utilizo el atributo "title"
    <hr>
4. **Autenticación Segura:**
<br><br>
    -  [X] Implementar un sistema de login seguro.
    -  [X] Manejo adecuado de autenticación y autorización para acceder a las listas.
    - Se implemento una autenticacion mediante apiKey para su correcto funcionamiento
    <hr>
5. **Documentación:**
   <br><br>
    -  [X] La aplicación debe estar bien documentada, incluyendo instrucciones para la configuración y uso del sistema.
    -  [X] La documentación del código debe ser clara y detallada.
    - Quedo pendiente por falta de tiempo agregar Swagger para una correcta documentacion de la API
    <hr>
6. **Pruebas Unitarias y de Integración:**
   <br><br>
    -  [X] La aplicación debe contener pruebas unitarias para garantizar el correcto funcionamiento de las unidades de código.
    -  [X] Incluir pruebas de integración para asegurar que los componentes trabajen correctamente juntos.
    - Se realizaron tanto pruebas unitarias como de integracion. 
    - Se utilizo JUnit + Mockito. 
    - Se utilizo el plugin Jacoco para medir el coverage
    <hr>
7. **Despliegue:**
   <br><br>
    -  [ ] Se recomienda desplegar la aplicación en algún servidor gratuito, como Heroku o Vercel, para facilitar la prueba de la app.
    - Quedo pendiente debido a falta de tiempo