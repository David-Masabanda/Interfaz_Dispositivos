# Dispositivos Móviles (Aplicación):tw-1f4f1: 
Esta es una aplicación enfocada en el universo de comics de Marvel, como una biblioteca portátil, con todos los superhéroes y villanos que uno pueda recordar. El desarrollo de la aplicación ha sido gracias a los conocimientos obtenidos a lo largo del semestre en Dispositivos Móviles. Además de otras fuentes de información que permitieron agregar funcionalidades más novedosas y seguras.

### Características

- Conexión con los servicios de Firebase para la autenticación.
- Firestore para almacenar datos de un usuario.
- Storage para guardar elementos multimedia.
- Uso del sensor biométrico, para acceder a los datos del usuario.
- Uso del micrófono, para realizar el reconocimiento de voz.
- Acceso a la cámara y galería del dispositivo para archivos multimedia.
- Uso de elementos multimedia para interactuar con el usuario: gifs, imágenes, sonidos, entre otros.
- Búsqueda en la base de datos y almacenamiento instantáneo.


### Librerías Usadas
- Picasso
- Retrofit
- GSON
- Corrutinas
- Biometrico
- Firebase
- Glide

### Pantallas
Empezamos con el login o la pantalla de ingreso a nuestra aplicación, tenemos varias opciones, pero la recomendada es registrarse. De esa forma podremos dar los datos necesarios para que la aplicación presente la información en el perfil principal.
[![Whats-App-Image-2023-08-25-at-9-05-35-PM.jpg](https://i.postimg.cc/W11W3XQP/Whats-App-Image-2023-08-25-at-9-05-35-PM.jpg)](https://postimg.cc/svb9H9jn)

La pantalla de registro recopila datos del usuario como:
- Nombre
- Correo
- Dirección
- Teléfono

Y la contraseña que va a usar el usuario para acceder a su cuenta y que es manejada de manera segura. Una de las funciones disponibles de Firebase es Authentication, que asegura nuestros datos y encripta información. 
[![Whats-App-Image-2023-08-25-at-9-05-35-PM-1.jpg](https://i.postimg.cc/Nf4CBWC4/Whats-App-Image-2023-08-25-at-9-05-35-PM-1.jpg)](https://postimg.cc/Whdw7fvF)

Al entrar en la aplicación se encuentra un texto de bienvenida que a breves rasgos da una explicación de como funciona la aplicación y las opciones que tiene disponible.
- Perfil
- API
- Search
- Exit
[![Whats-App-Image-2023-08-25-at-9-05-35-PM-2.jpg](https://i.postimg.cc/Cxrvfbgn/Whats-App-Image-2023-08-25-at-9-05-35-PM-2.jpg)](https://postimg.cc/TKmjzLhT)

El perfil del usuario estará por defecto vacío y con los datos que cargo en su ingreso. Puede cambiar su imagen de usuario tomando una foto con la cámara o seleccionando una imagen de la galería. Se subirán de inmediato a Storage y se cargarán en el perfil (esto puede tomar un rato). Si aún no tiene personajes favoritos, la lista estará vacía y se mostrara una imagen (gif de espera). Para cambiar algún dato puede dar click en el boton editar.
[![Whats-App-Image-2023-08-25-at-9-05-35-PM-3.jpg](https://i.postimg.cc/Pf1FFLm0/Whats-App-Image-2023-08-25-at-9-05-35-PM-3.jpg)](https://postimg.cc/4nN5Yx21)

La pantalla de Edición tiene una restricción al momento de ingresar. El usuario debe verificar su identidad con la huella dactilar (se recomienda usar la aplicación en un teléfono y no en el emulador para estos casos). Una vez que el usuario haya cambiado los datos puede guardarlos y volver a su perfil.
[![Whats-App-Image-2023-08-25-at-9-05-34-PM-1.jpg](https://i.postimg.cc/Njdn7W1M/Whats-App-Image-2023-08-25-at-9-05-34-PM-1.jpg)](https://postimg.cc/RWnGvpp5)

La segunda opción (API) accede a la base de datos de los personajes. El usuario puede visualizar todos los personajes de la API y una breve reseña del comic al que pertenecen. Hay un pequeño botón con la figura de un corazón que se refiere a un personaje favorito. Si el usuario quiere guardar un personaje solo debe dar click al botón, un breve sonido confirmara la ejecución.
[![Whats-App-Image-2023-08-25-at-9-05-32-PM-2.jpg](https://i.postimg.cc/Rh1s38Sv/Whats-App-Image-2023-08-25-at-9-05-32-PM-2.jpg)](https://postimg.cc/m1DNJm1n)

En el caso de que desee optimizar la búsqueda, accede a la tercera opción (Search). Se puede buscar a cualquier personaje poniendo su nombre en la barra de búsqueda o por comandos de voz. Solo debe aplastar el botón y esperar al asistente. La presentación de personajes será similar a la opción anterior.
[![Whats-App-Image-2023-08-25-at-9-05-32-PM-3.jpg](https://i.postimg.cc/rFL78GV8/Whats-App-Image-2023-08-25-at-9-05-32-PM-3.jpg)](https://postimg.cc/Cdmc7qnt)

Si desea saber un poco más de los personajes puede dar click en cualquier zona de la tarjeta. Esto lleva a una nueva pantalla con todos los datos relevantes del personaje.
[![Whats-App-Image-2023-08-25-at-9-05-29-PM.jpg](https://i.postimg.cc/6qxbpwNH/Whats-App-Image-2023-08-25-at-9-05-29-PM.jpg)](https://postimg.cc/LYv3xc5f)

Cuando el usuario vuelva a su perfil, todos los personajes que selecciono como favoritos apareceran en la lista de espera que estaba antes. Tiene acceso a más información si da click en alguno de ellos y puede seguir almacenando más personajes.
[![Whats-App-Image-2023-08-25-at-9-05-19-PM.jpg](https://i.postimg.cc/26ZXYygb/Whats-App-Image-2023-08-25-at-9-05-19-PM.jpg)](https://postimg.cc/vgGvLYYb)

### Nuevas Funcionalidades
**- Firebase**
Al momento de crear el proyecto se debe realizar toda la configuracion necesaria para trabajar con Firebase. Este servicio es de mucha ayuda y la herramienta que se utilizó en este proyecto como base de datos para los usuarios y su información.
Firestore: Guarda los datos personales del usuario y la lista de personajes favoritos como una arreglo.
Storage: Guarda la URI de las imágenes que va a usar como foto de perfil.

**- Glide**
Los imageview son capaces de presentar elementos multimedia como los gif, pero no los procesan como deberían. Para estos casos glide es la herramienta indicada. Solo hay que implementarla en gradle y dentro del activity o fragment que tenga un elemento de este tipo lo cargamos desde la referencia a drawable.

[![implementacion.png](https://i.postimg.cc/ZnbHnzHZ/implementacion.png)](https://postimg.cc/XGPd1htD)





