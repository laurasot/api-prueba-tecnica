## Explicacion para probar API

1 -> ejecutar la aplicacion

2 -> probar el siguiente endpoint encargado de persistir un usuario en la bd
http://localhost:8080/users/create

3-> en caso de probarlo en postman debe ser una solicitud tipo post

4 -> se debe enviar en el body de la solicitud http el objeto que corresponde al usuario (en este caso se encuentra en script.txt)

5 -> la expresion regular de la contraseña se puede cambiar en el archivo application.properties

6 -> dentro del archivo script.txt,se encuentran 1 ejemplo de request en formato json, se puede probar con ese.
