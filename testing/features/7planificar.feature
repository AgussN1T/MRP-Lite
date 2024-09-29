# language: es

   Característica: administra planificación de pedidos de fabricación
   Planifica, replanifica pedidos planificados y nuevos generados

   Esquema del escenario: Planificación sobre un taller seleccionado de un producto
   Dado el producto "<producto>"
   Y el taller "<taller>"
   Y tomando como base de planificación la fecha "01/01/2023"
   Cuando se solicita planificar con esquema de pronta entrega
   Entonces se obtiene la <planificación> con <status> y "<respuesta>"

   Ejemplos:

   | producto        | taller | planificación | status | respuesta                                                   |
   | mesa 1.80x0.70  | este   | 1             | 200    | Producto planificado pronta entrega en taller correctamente |
   | mesa 2x1        | este   | 2             | 200    | Producto planificado pronta entrega en taller correctamente |
   | silla estándar  | este   | 3             | 200    | Producto planificado pronta entrega en taller correctamente |
   | banqueta        | este   | 4             | 200    | Producto planificado pronta entrega en taller correctamente |
   | zócalos de 1.5m | centro | 5             | 200    | Producto planificado pronta entrega en taller correctamente |
   | zócalos de 2m   | centro | 6             | 200    | Producto planificado pronta entrega en taller correctamente |
   | zócalos de 2.5m | centro | 7             | 200    | Producto planificado pronta entrega en taller correctamente |
   | puerta estándar | este   | 8             | 200    | Producto planificado pronta entrega en taller correctamente |
   | puerta doble    | este   | 9             | 200    | Producto planificado pronta entrega en taller correctamente |
   | perchero de pie | oeste  | 10            | 200    | Producto planificado pronta entrega en taller correctamente |

   Esquema del escenario: Planificación sobre un taller seleccionado de un producto
   Dado el producto "<producto>"
   Y el taller "<taller>"
   Y tomando como base de planificación la fecha de entrega "31/01/2023"
   Cuando se solicita planificar con entrega tardía EDF
   Entonces se obtiene la <planificación> con <status> y "<respuesta>"

   Ejemplos:

   | producto        | taller | planificación | status | respuesta                                                   |
   | mesa 1.80x0.70  | este   | 11            | 200    | Producto planificado pronta entrega en taller correctamente |
   | mesa 2x1        | este   | 12            | 200    | Producto planificado pronta entrega en taller correctamente |
   | silla estándar  | este   | 13            | 200    | Producto planificado pronta entrega en taller correctamente |
   | banqueta        | este   | 14            | 200    | Producto planificado pronta entrega en taller correctamente |
   | zócalos de 1.5m | centro | 15            | 200    | Producto planificado pronta entrega en taller correctamente |
   | zócalos de 2m   | centro | 16            | 200    | Producto planificado pronta entrega en taller correctamente |
   | zócalos de 2.5m | centro | 17            | 200    | Producto planificado pronta entrega en taller correctamente |
   | puerta estándar | este   | 18            | 200    | Producto planificado pronta entrega en taller correctamente |
   | puerta doble    | este   | 19            | 200    | Producto planificado pronta entrega en taller correctamente |
   | perchero de pie | oeste  | 20            | 200    | Producto planificado pronta entrega en taller correctamente |