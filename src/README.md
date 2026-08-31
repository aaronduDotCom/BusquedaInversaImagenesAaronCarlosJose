# Varas que se necesitan para el proyecto

Para la creacion de los bins vamos a uar el ejemplo del profe de 4 bins por color RGB

Podriamos hacer una clase vector para reutilizar codigo por que se usan vectores en varias partes del proyecto

Delegación de varas para el proyecto: Para el viernes 28 de agosto

Delegación: 

- Jose: crear colección generica, crear colección imagen e imagen data
- Carlos: refactorizar o terminar de hacer serializador
- Aaron: interfaz y abstractor de imagen

Hilo de acciones: 

Primer paso: serializar las imagenes
Segundo paso: cargar las imagenes en memoria
Tercer paso: crear coleccion imagenesdata
Cuarto paso: pasamos la imagen a buscar y la coleccion imagenesdata a buscador inverso
Quinto paso: la sacamos la informacion a la imagen nueva 
Sexto paso: calculamos resultados
Septimo paso: pasar los resultados a la clase resultado u objeto 
Octavo paso: se lo pasamos a la controladora 
Noveno paso: la controladora se lo pasa al view para que lo muestre
Decimo paso: guardarlo en binario



AbstractorImagen: define el vector, crea los bins, y mapea lo pixeles a los indices de los bins, y guarda el vector en imagendata, normaliza el histograma
y lo guarda en imagendata //

Buscador Inverso: calcula simulitud coseno, euclideana, interseccion de histogramas, ordena los resultados y los manda a resultados (clase)