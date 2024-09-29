# language: es
Característica: gestión de productos

   Esquema del escenario: Nuevo producto sin tareas
      Dado que se ingresa el nuevo producto con "<nombre>"
      Cuando presiono el botón de guardar producto
      Entonces se espera el siguiente <status> con la "<respuesta>"

      Ejemplos:
      | nombre           | status | respuesta |
      | mesa 1.80x0.70 | 200    | Producto mesa 1.80x0.70 ingresado correctamente |
      | mesa 2x1 | 200    | Producto mesa 2x1 ingresado correctamente |
      | silla estándar | 200    | Producto silla estándar ingresado correctamente |
      | banqueta | 200    | Producto banqueta ingresado correctamente |
      | zócalos de 1.5m | 200    | Producto zócalos de 1.5m ingresado correctamente |
      | zócalos de 2m | 200    | Producto zócalos de 2m ingresado correctamente |
      | zócalos de 2.5m | 200    | Producto zócalos de 2.5m ingresado correctamente |
      | puerta estándar | 200    | Producto puerta estándar ingresado correctamente |
      | puerta doble | 200    | Producto puerta doble ingresado correctamente |
      | perchero de pie | 200    | Producto perchero de pie ingresado correctamente |

   Esquema del escenario: Agregar tareas a los productos existente
      Dado que existen los productos cuando se agrega la tarea con "<nombreTarea>", <orden>, <tiempo> y "<tipoEquipo>" para el producto "<nombreProducto>"
      Cuando presiono el botón de actualizar producto
      Entonces se espera el siguiente <status> con la "<respuesta>"

      Ejemplos:
      | nombreProducto | nombreTarea         | orden | tiempo | tipoEquipo         | status | respuesta |
      | mesa 1.80x0.70 | cortar patas        | 1     | 10     | cierra             | 200    | Producto mesa 1.80x0.70 actualizado correctamente |
      | mesa 1.80x0.70 | cortar tapa         | 2     | 15     | cierra             | 200    | Producto mesa 1.80x0.70 actualizado correctamente |
      | mesa 1.80x0.70 | pulir patas         | 3     | 7      | lijadora de banda  | 200    | Producto mesa 1.80x0.70 actualizado correctamente |
      | mesa 1.80x0.70 | pulir tapa          | 4     | 25     | lijadora de banda  | 200    | Producto mesa 1.80x0.70 actualizado correctamente |
      | mesa 1.80x0.70 | ensamblar y encolar | 5     | 45     | mesa ensamblado    | 200    | Producto mesa 1.80x0.70 actualizado correctamente |
      | mesa 1.80x0.70 | pintar y finalizar  | 6     | 35     | mesa ensamblado    | 200    | Producto mesa 1.80x0.70 actualizado correctamente |
      | mesa 2x1 | cortar patas        | 1     | 12     | cierra             | 200    | Producto mesa 2x1 actualizado correctamente |
      | mesa 2x1 | cortar tapa         | 2     | 19     | cierra             | 200    | Producto mesa 2x1 actualizado correctamente |
      | mesa 2x1 | pulir patas         | 3     | 10     | lijadora de banda  | 200    | Producto mesa 2x1 actualizado correctamente |
      | mesa 2x1 | pulir tapa          | 4     | 29     | lijadora de banda  | 200    | Producto mesa 2x1 actualizado correctamente |
      | mesa 2x1 | ensamblar y encolar | 5     | 52     | mesa ensamblado    | 200    | Producto mesa 2x1 actualizado correctamente |
      | mesa 2x1 | pintar y finalizar  | 6     | 41     | mesa ensamblado    | 200    | Producto mesa 2x1 actualizado correctamente |
      | silla estándar | cortar patas        | 1     | 8      | cierra             | 200    | Producto silla estándar actualizado correctamente |
      | silla estándar | cortar respaldo     | 2     | 7      | cierra             | 200    | Producto silla estándar actualizado correctamente |
      | silla estándar | cortar asiento      | 3     | 10     | cierra             | 200    | Producto silla estándar actualizado correctamente |
      | silla estándar | pulir patas         | 4     | 5      | lijadora de banda  | 200    | Producto silla estándar actualizado correctamente |
      | silla estándar | pulir respaldo      | 5     | 4      | lijadora de banda  | 200    | Producto silla estándar actualizado correctamente |
      | silla estándar | pulir asiento       | 6     | 20     | lijadora de banda  | 200    | Producto silla estándar actualizado correctamente |
      | silla estándar | ensamblar y encolar | 7     | 32     | mesa ensamblado    | 200    | Producto silla estándar actualizado correctamente |
      | silla estándar | pintar y finalizar  | 8     | 23     | mesa ensamblado    | 200    | Producto silla estándar actualizado correctamente |
      | banqueta       | cortar patas        | 1     | 8      | cierra             | 200    | Producto banqueta actualizado correctamente | 
      | banqueta       | cortar asiento      | 3     | 10     | cierra             | 200    | Producto banqueta actualizado correctamente |
      | banqueta       | pulir patas         | 4     | 5      | lijadora de banda  | 200    | Producto banqueta actualizado correctamente |
      | banqueta       | pulir asiento       | 6     | 20     | lijadora de banda  | 200    | Producto banqueta actualizado correctamente |
      | banqueta       | ensamblar y encolar | 7     | 32     | mesa ensamblado    | 200    | Producto banqueta actualizado correctamente |
      | banqueta       | pintar y finalizar  | 8     | 23     | mesa ensamblado    | 200    | Producto banqueta actualizado correctamente |
      | zócalos de 1.5m| cortar              | 1     | 5      | cierra             | 200    | Producto zócalos de 1.5m actualizado correctamente |
      | zócalos de 1.5m| fresar              | 2     | 6      | fresadora          | 200    | Producto zócalos de 1.5m actualizado correctamente |
      | zócalos de 1.5m| cepillar            | 3     | 8      | Cepillo Garlopa    | 200    | Producto zócalos de 1.5m actualizado correctamente |
      | zócalos de 2m  | cortar              | 1     | 5      | cierra             | 200    | Producto zócalos de 2m actualizado correctamente |
      | zócalos de 2m  | fresar              | 2     | 7      | fresadora          | 200    | Producto zócalos de 2m actualizado correctamente |
      | zócalos de 2m  | cepillar            | 3     | 9      | Cepillo Garlopa    | 200    | Producto zócalos de 2m actualizado correctamente |
      | zócalos de 2.5m| cortar              | 1     | 6      | cierra             | 200    | Producto zócalos de 2.5m actualizado correctamente |
      | zócalos de 2.5m| fresar              | 2     | 8      | fresadora          | 200    | Producto zócalos de 2.5m actualizado correctamente |
      | zócalos de 2.5m| cepillar            | 3     | 10     | Cepillo Garlopa    | 200    | Producto zócalos de 2.5m actualizado correctamente |
      | puerta estándar| cortar              | 1     | 25     | cierra             | 200    | Producto puerta estándar actualizado correctamente |
      | puerta estándar| pulir               | 2     | 28     | lijadora de banda  | 200    | Producto puerta estándar actualizado correctamente |
      | puerta estándar| cepillar            | 3     | 12     | Cepillo Garlopa    | 200    | Producto puerta estándar actualizado correctamente |
      | puerta estándar| ensamblar y encolar | 4     | 31     | mesa ensamblado    | 200    | Producto puerta estándar actualizado correctamente |
      | puerta estándar| fresar              | 5     | 12     | fresadora          | 200    | Producto puerta estándar actualizado correctamente |
      | puerta doble   | cortar              | 1     | 50     | cierra             | 200    | Producto puerta doble actualizado correctamente |
      | puerta doble   | pulir               | 2     | 56     | lijadora de banda  | 200    | Producto puerta doble actualizado correctamente |
      | puerta doble   | cepillar            | 3     | 24     | Cepillo Garlopa    | 200    | Producto puerta doble actualizado correctamente |
      | puerta doble   | ensamblar y encolar | 4     | 62     | mesa ensamblado    | 200    | Producto puerta doble actualizado correctamente |
      | puerta doble   | fresar              | 5     | 24     | fresadora          | 200    | Producto puerta doble actualizado correctamente |
      | perchero de pie| cortar              | 1     | 50     | cierra             | 200    | Producto perchero de pie actualizado correctamente |
      | perchero de pie| doblar              | 2     | 24     | dobladora          | 200    | Producto perchero de pie actualizado correctamente |
      | perchero de pie| ensamblar y encolar | 3     | 31     | mesa ensamblado    | 200    | Producto perchero de pie actualizado correctamente |
      | perchero de pie| Lustrar             | 4     | 24     | mesa ensamblado    | 200    | Producto perchero de pie actualizado correctamente |
