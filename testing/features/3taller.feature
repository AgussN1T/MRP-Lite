# language: es
Característica: gestión de talleres

   Esquema del escenario: Nuevo taller sin equipos
      Dado que se ingresa el nuevo taller con "<codigo>" y "<nombre>"
      Cuando presiono el botón de guardar taller
      Entonces se espera el siguiente <status> con la "<respuesta>"

      Ejemplos:
      | codigo    | nombre                               | status | respuesta                             | 
      | norte     | Taller norte que queda en el norte   | 200    | Taller norte ingresado correctamente  |
      | este      | Taller este que queda en el este     | 200    | Taller este ingresado correctamente   |
      | oeste     | Taller oeste que queda en el oeste   | 200    | Taller oeste ingresado correctamente  |
      | sur       | Taller sur que queda en el sur       | 200    | Taller sur ingresado correctamente    |
      | centro    | Taller centro que queda en el centro | 200    | Taller centro ingresado correctamente |

   Esquema del escenario: Agregar equipos a los talleres existente
      Dado que existen los talleres cuando se agrega el equipo con "<codigoEquipo>" del "<tipoEquipo>" y <capacidad> al taller "<codigoTaller>"
      Cuando presiono el botón de actualizar taller
      Entonces se espera el siguiente <status> con la "<respuesta>"


      Ejemplos:
      | codigoTaller | codigoEquipo                   | tipoEquipo             | capacidad |  status | respuesta | 
      | norte        | cierra norte                   | cierra                 | 2         | 200    | Taller norte actualizado correctamente  |
      | norte        | lijadora de banda norte        | lijadora de banda      | 3         | 200    | Taller norte actualizado correctamente  |
      | norte        | mesa ensamblado norte 1        | mesa ensamblado        | 1         | 200    | Taller norte actualizado correctamente  |
      | norte        | mesa ensamblado norte 2        | mesa ensamblado        | 2         | 200    | Taller norte actualizado correctamente  |
      | norte        | fresadora norte 1              | fresadora              | 2         | 200    | Taller norte actualizado correctamente  |
      | norte        | fresadora norte 2              | fresadora              | 1         | 200    | Taller norte actualizado correctamente  |
      | norte        | Cepillo Garlopa norte          | Cepillo Garlopa        | 3         | 200    | Taller norte actualizado correctamente  |
      | este         | cierra este 1                  | cierra                 | 1         | 200    | Taller este actualizado correctamente  |
      | este         | cierra este 2                  | cierra                 | 3         | 200    | Taller este actualizado correctamente  |
      | este         | lijadora de banda este 1       | lijadora de banda      | 1         | 200    | Taller este actualizado correctamente  |
      | este         | lijadora de banda este 2       | lijadora de banda      | 1         | 200    | Taller este actualizado correctamente  |
      | este         | mesa ensamblado este           | mesa ensamblado        | 3         | 200    | Taller este actualizado correctamente  |
      | este         | fresadora este 1               | fresadora              | 1         | 200    | Taller este actualizado correctamente  |
      | este         | fresadora este 2               | fresadora              | 3         | 200    | Taller este actualizado correctamente  |
      | este         | Cepillo Garlopa este 1         | Cepillo Garlopa        | 1         | 200    | Taller este actualizado correctamente  |
      | este         | Cepillo Garlopa este 2         | Cepillo Garlopa        | 1         | 200    | Taller este actualizado correctamente  |
      | sur          | cierra sur                     | cierra                 | 1         | 200    | Taller sur actualizado correctamente  |
      | sur          | lijadora de banda sur          | lijadora de banda      | 1         | 200    | Taller sur actualizado correctamente  |
      | sur          | mesa ensamblado sur            | mesa ensamblado        | 1         | 200    | Taller sur actualizado correctamente  |
      | sur          | fresadora sur                  | fresadora              | 1         | 200    | Taller sur actualizado correctamente  |
      | sur          | Cepillo Garlopa sur            | Cepillo Garlopa        | 1         | 200    | Taller sur actualizado correctamente  |
      | sur          | dobladora sur                  | dobladora              | 1         | 200    | Taller sur actualizado correctamente  |
      | oeste        | cierra oeste 1                 | cierra                 | 1         | 200    | Taller oeste actualizado correctamente  |
      | oeste        | cierra oeste 2                 | cierra                 | 1         | 200    | Taller oeste actualizado correctamente  |
      | oeste        | lijadora de banda oeste 1      | lijadora de banda      | 1         | 200    | Taller oeste actualizado correctamente  |
      | oeste        | lijadora de banda oeste 2      | lijadora de banda      | 1         | 200    | Taller oeste actualizado correctamente  |
      | oeste        | mesa ensamblado oeste 1        | mesa ensamblado        | 1         | 200    | Taller oeste actualizado correctamente  |
      | oeste        | mesa ensamblado oeste 2        | mesa ensamblado        | 1         | 200    | Taller oeste actualizado correctamente  |
      | oeste        | fresadora oeste 1              | fresadora              | 1         | 200    | Taller oeste actualizado correctamente  |
      | oeste        | fresadora oeste 2              | fresadora              | 1         | 200    | Taller oeste actualizado correctamente  |
      | oeste        | Cepillo Garlopa oeste 1        | Cepillo Garlopa        | 1         | 200    | Taller oeste actualizado correctamente  |
      | oeste        | Cepillo Garlopa oeste 2        | Cepillo Garlopa        | 1         | 200    | Taller oeste actualizado correctamente  |
      | oeste        | dobladora oeste 1              | dobladora              | 1         | 200    | Taller oeste actualizado correctamente  |
      | oeste        | dobladora oeste 2              | dobladora              | 1         | 200    | Taller oeste actualizado correctamente  |
      | centro       | cierra centro                  | cierra                 | 2         | 200    | Taller centro actualizado correctamente  |
      | centro       | fresadora centro               | fresadora              | 2         | 200    | Taller centro actualizado correctamente  |
      | centro       | Cepillo Garlopa centro         | Cepillo Garlopa        | 2         | 200    | Taller centro actualizado correctamente  |
