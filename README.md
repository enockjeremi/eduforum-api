
# Eduforum

Rest API creada para el Challenge de Alura.

API para la plataforma eduforum, desarrollada en Java usando Spring Boot.


## Instalacion

Es necesario tener la version 17 de el JDK.
En la raiz esta un archivo .env.example, en este deben agregar las variables de entorno para la conexion a la DB.

Para la ejecucion de la base de datos, use un contenedor de docker, tambien encontraran un archivo .yml que tiene una
previa configuracion del contenedor.

## Recursos utilizados
para este proyecto se usaron diversas dependencias, especificas para cada tarea.

- Spring Data JPA, para el manejo de consultas a la base de datos.
- Spring Security, para el control de autenticacion y autorizacion.
- Auth0, para generar el token de acceso (JWT)
- Bean Validation, para validar los DTOs
- Flyway, para gestionar las migraciones a la base de datos.
- Lombok, para evitar el codigo repetitivo.

## Caracteristicas

- CRUD para Cursos
- CRUD para Tópicos
- CRUD para Respuestas
- Autenticación de Usuarios 
- Autorización por Roles 
- Búsqueda de Tópicos por Título
- Listar Tópicos por Curso 
- Listar Tópicos por Categoría de Curso 
- Cada Topico y Respuesta debe tener su respectivo autor
- Restricciones de Edición y Eliminación 
- Selección de Respuesta Solucionadora
- Cuando un usuario selecciona una respuesta como la que resuelve su tópico, el estado del tópico pasa a inactivo, indicando que ya no se requieren más respuestas.
- Tanto el autor del tópico como un usuario administrador tienen permisos para seleccionar cuál es la respuesta que da solución al tópico.
- Un usuario admin tiene los permisos necesarios para editar y eliminar tanto topicos, como respuestas 

## Comentario

Si bien para el challenge no era necesario agregar tantas caracteristicas, me resulto muy buena practica hacerlo.
Me gusto mucho la forma en la que se trabaja con Spring Boot, es un framework muy poderoso.

La parte que mas me costo incluir, sin dudas fue la autorizacion, ya que no fue un tema tocado en los diferentes cursos, pero con la ayuda de google y la documentacion, todo es posible.


## Autor

- [@enockjeremi](https://www.github.com/enockjeremi)