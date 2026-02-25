# Trabajo Práctico Final Libre – Programación II TUDAI

## Ejecución del programa:
Desde la raíz del proyecto ejecutar en consola:
```
java -cp bin App
```

El programa iniciará el CLI, permitiendo:
- Cargar un estado inicial desde archivo
- Inicializar el tablero de forma aleatoria
- Ejecutar una cantidad determinada de generaciones
- Ejecutar indefinidamente hasta que no haya cambios
  
1) Carga desde archivo:
   - Se solicita la ruta del archivo
   - Si el archivo no existe o el formato es inválido, se informa el error y se permite reintentar
   - El archivo debe respetar el formato especificado (primera línea con las filas/columnas y luego representación de celdas)

2) Inicialización aleatoria:
   - Se solicita la cantidad de filas y columnas
   - Se solicita la probabilidad de que una celda esté viva (valor entre 0 y 1)
   - Se valida que los valores ingresados sean correctos

Una vez inicializado el tablero, el usuario puede:

- Indicar la cantidad de generaciones a ejecutar
- Ejecutar el juego en modo indeterminado hasta que no se producen cambios entre generaciones

## Requisitos de Diseño
### • Separación entre lógica del modelo y presentación
El modelo contiene toda la lógica relacionada con la evolución del juego:
- Clase Tablero: responsable de la generacion y del cálculo de vecinos
- Clase Celda: delega el comportamiento a su estado
- Interfaz EstadoCelda y sus implementaciones
- EstadoFactory: responsable de la creación de estados
- LectorArchivo: encargado de construir el tablero a partir de un archivo

 CLI (presentación) no contiene reglas del juego ni lógica:
- Interactuar con el usuario
- Solicitar datos
- Mostrar el estado del tablero
- Controlar la ejecución

La separación permite:
- Reemplazar la interfaz por una GUI sin modificar el modelo
- Facilitar el mantenimiento
- Reducir el acoplamiento entre componentes
- Respetar el principio de responsabilidad única

### • Aplicar principios SOLID
#### Single Responsibility Principle 
Cada clase del sistema tiene una única responsabilidad bien definida:
-Tablero: administrar la matriz de celdas y la generación
- Celda: delegar el comportamiento al estado actual y permitir su actualización
- EstadoCelda y sus implementaciones: definir la lógica de transformación según el estado
- EstadoFactory: centralizar la creación de estados
- LectorArchivo: construir un tablero a partir de un archivo
- App: interactuar con el usuario y controlar el flujo de ejecución

---

#### Open/Closed Principle
El sistema está abierto a extensión pero cerrado a modificación.
Ejemplo 
- Agregar un nuevo estado:
  1) Crear una clase que implemente EstadoCelda
  2) Definir su comportamiento en siguienteEstado
  3) Agregar su representación en EstadoFactory
No se modifica Tablero ni Celda, los estados Enferma y Latente se fueron agregados sin modificar las clases

---

#### Liskov Substitution Principle
Las clases que implementan EstadoCelda pueden sustituirse entre si sin alterar el funcionamiento del sistema
Tablero y Celda trabajan exclusivamente con el tipo abstracto EstadoCelda sin depender de implementaciones concretas
Cualquier nuevo estado puede integrarse sin romper el comportamiento del sistema

---

#### Interface Segregation Principle
La interfaz EstadoCelda define únicamente los métodos necesarios para el comportamiento de una celda:
- siguienteEstado
- estaViva
- getRepresentacion
No existen métodos que obliguen a las implementaciones a definir comportamientos que no utilizan

---

#### Dependency Inversion Principle 
Las clases Tablero, Celda dependen de la abstracción EstadoCelda y no de clases concretas como EstadoViva o EstadoMuerta
Esto reduce el acoplamiento y permite que nuevas implementaciones de estado se integren sin modificar
La creación de estados se delega a EstadoFactory evitando que el resto del sistema conozca detalles de instanciación

### • Manejo de Errores y Validaciones
#### Carga desde archivo
- Archivo inexistente
- Ruta inválida
- Formato incorrecto en las filas/columnas
- Cantidad de filas o columnas inconsistente
- Caracteres no validos
---

#### Carga Random
- Cantidad de filas y columnas mayores a cero
- Probabilidad de celda viva (entre 0 y 1)
---
#### En caso de error:
- Se informa el problema al usuario
- Se permite reintentar
---

#### Durante la ejecución del juego
- Se detecta si no existen cambios entre generaciones
- En caso de que no haya cambios se detiene
- Se informa al usuario que el sistema cuando finaliza

---

#### Separación de errores
- La CLI valida entradas del usuario.
- LectorArchivo valida formato estructural.
- EstadoFactory valida caracteres de estado.
- El modelo no realiza operaciones de entrada/salida.
---
### Permitir extender el juego
#### Incorporación de nuevos estados
1) Crear una clase que implemente la interfaz EstadoCelda
2) Definir la lógica correspondiente en el método siguienteEstado
3) Registrar el carácter representativo en EstadoFactory
No es necesario modificar:
- Tablero
- Celda
- CLI
Se respeta el principio Open/Closed permitiendo extender funcionalidades sin alterar el modelo

#### Extensión de reglas completas del juego
Las reglas están encapsuladas dentro de cada implementación de EstadoCelda

 Para modificar las reglas globales o de supervivencia se podría incorporar una nueva abstracción que represente el conjunto de reglas
- Definir una interfaz ReglaJuego
- Delegar el cálculo del siguiente estado
- Permitir que Tablero reciba una implementación concreta de reglas
Asi cambiar completamente el comportamiento sin modificar las clases existentes
```java
public interface ReglaJuego {
EstadoCelda calcularSiguienteEstado(Celda celda, int vecinosVivos);
}
```

## Patrones de diseño
#### Patron State

Se aplica n entre Celda y EstadoCelda
Cada celda mantiene una referencia a un objeto que representa su estado actual
El comportamiento (cálculo del siguiente estado) se delega a ese objeto
Permite:
- Eliminar estructuras condicionales
- Cambiar comportamiento dinámicamente
- Agregar nuevos estados sin modificar el núcleo del sistema
---

#### Patron Factory

La clase EstadoFactory evita que el resto del sistema dependa de clases concretas
- Centralizar la lógica de instanciación
- Facilitar la incorporación de nuevos estados
- Reducir el acoplamiento

---

#### Delegación y Polimorfismo
El comportamiento variable se implementa mediante polimorfismo
Las clases del modelo trabajan con la abstracción EstadoCelda permitiendo que cualquier implementación sea utilizada sin alterar el funcionamiento


## Clases Principales y Responsabilidades

### Tablero
Responsabilidades:
- Mantener la matriz de celdas
- Calcular vecinos vivos
- Avance del tablero de una generación a la siguiente
- Detectar cuando ya no haya cambios
- Inicializar el tablero
Calcular una nueva generación se realiza en dos fases:
1) Se determinan los nuevos estados sin modificar el tablero actual 
2) Se actualizan todas las celdas simultáneamente

---
### Celda
Responsabilidades:
- Mantener su estado actual
- Delegar el cálculo del siguiente estado
- Actualizar su estado
Delega el comportamiento dinámico al objeto EstadoCelda
---
### EstadoCelda (Interfaz)
Define funciones comunes:
- siguienteEstado(int vecinosVivos)
- estaViva()
- getRepresentacion()
Las clases del modelo trabajan contra esta abstracción permitiendo el uso de polimorfismo
---
### Estados
Cada estado encapsula su propia logica, evita estructuras condicionales basadas en el tipo
EstadoViva:
- Implementa reglas de supervivencia
- Puede convertirse en Enferma con una probabilidad del 25%
   
EstadoMuerta:
- Revive con exactamente 3 vecinos vivos

EstadoEnferma:
- Muere obligatoriamente en la siguiente generación

EstadoLatente:
- Revive con exactamente 1 vecino vivo
---
### EstadoFactory
Responsabilidades:
- Centralizar la creación de estados
- Manejo de caracteres del archivo en objetos concretos
- Evitar dependencia directa con clases concretas
---
### LectorArchivo
Responsabilidades:
- Validar formato
- Crear el tablero
- Delegar creación de estados a EstadoFactory
---
### CLI
Responsabilidades:
- Interactuar con el usuario
- Solicitar entradas
- Controlar flujo de ejecución
- Mostrar el tablero

## Cálculo del próximo estado de una celda:
### 1) Cálculo de vecinos
Para cada celda del tablero se calcula la cantidad de vecinos vivos  
Se consideran las ocho posiciones (horizontal/vertical/diagonal) verificando que cada posición se encuentre dentro de los límites del tablero
### 2) Cálculo del siguiente estado (sin modificar el tablero)
Con la cantidad de vecinos vivos cada celda delega en su objeto EstadoCelda
```java
siguienteEstado(int vecinosVivos)
```
- No se modifica el estado actual de las celdas
- Los nuevos estados se almacenan temporalmente en una estructura auxiliar
- Todas las transiciones se calculan en base al estado completo de la generación actual
---
### 3) Actualización simultánea

Una vez que todos los nuevos estados fueron calculado el tablero actualiza cada celda con su nuevo estado

---
### 4) Detectar Fin
Durante el proceso de actualización se verifica si alguna celda cambió de estado
Si ninguna celda modifica su estado respecto de la generación anterior, la ejecución se detiene

---
