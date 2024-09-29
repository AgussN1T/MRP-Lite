# language: es

Característica: administra planificación de pedidos de fabricación
Planifica, replanifica pedidos planificados y nuevos generados

   Esquema del escenario: Seleccionar taller pertinente a producto
   Dado el "<producto>"
   Y la lista de talleres ordenada alfabéticamente por código
   Cuando se solicita recuperar el primer taller que cumple con la condición de fabricación
   Entonces se obtiene el "<taller>" con <status> y "<respuesta>"

   Ejemplos:

   | producto        | taller | status | respuesta                      |
   | mesa 1.80x0.70  | este   | 200    | Taller recuperado exitosamente |
   | mesa 2x1        | este   | 200    | Taller recuperado exitosamente |
   | silla estándar  | este   | 200    | Taller recuperado exitosamente |
   | banqueta        | este   | 200    | Taller recuperado exitosamente |
   | zócalos de 1.5m | centro | 200    | Taller recuperado exitosamente |
   | zócalos de 2m   | centro | 200    | Taller recuperado exitosamente |
   | zócalos de 2.5m | centro | 200    | Taller recuperado exitosamente |
   | puerta estándar | este   | 200    | Taller recuperado exitosamente |
   | puerta doble    | este   | 200    | Taller recuperado exitosamente |
   | perchero de pie | oeste  | 200    | Taller recuperado exitosamente |
   | no existe       |        | 501    | El producto no existe          |


   Esquema del escenario: Recuperar todos los taller pertinente a producto
   Dado el "<producto>"
   Y la lista de talleres ordenada alfabéticamente por código
   Cuando se solicita recuperar la lista de talleres que cumplen con la condición de fabricación
   Entonces se obtiene la lista de "<talleres>" con <status> y "<respuesta>"

   Ejemplos:

   | producto        | talleres                      | status | respuesta                         |
   | mesa 1.80x0.70  | [este,norte,oeste,sur]        | 200    | Talleres recuperados exitosamente |
   | mesa 2x1        | [este,norte,oeste,sur]        | 200    | Talleres recuperados exitosamente |
   | silla estándar  | [este,norte,oeste,sur]        | 200    | Talleres recuperados exitosamente |
   | banqueta        | [este,norte,oeste,sur]        | 200    | Talleres recuperados exitosamente |
   | zócalos de 1.5m | [centro,este,norte,oeste,sur] | 200    | Talleres recuperados exitosamente |
   | zócalos de 2m   | [centro,este,norte,oeste,sur] | 200    | Talleres recuperados exitosamente |
   | zócalos de 2.5m | [centro,este,norte,oeste,sur] | 200    | Talleres recuperados exitosamente |
   | puerta estándar | [este,norte,oeste,sur]        | 200    | Talleres recuperados exitosamente |
   | puerta doble    | [este,norte,oeste,sur]        | 200    | Talleres recuperados exitosamente |
   | perchero de pie | [oeste,sur]                   | 200    | Talleres recuperados exitosamente |
   | no existe       |                               | 501    | El producto no existe             |