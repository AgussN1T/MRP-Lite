# language: es
Característica: gestión de talleres

   Esquema del escenario: Nuevo tipo de equipo para asignar a talleres
      Dado que se ingresa el tipo de equipo con "<nombre>"
      Cuando presiono el botón de guardar
      Entonces se espera el siguiente <status> con la "<respuesta>"

      Ejemplos:
      | nombre            | status | respuesta                      |
      | cierra            | 200    | Tipo de Equipo cierra registrado correctamente |
      | lijadora de banda | 200    | Tipo de Equipo lijadora de banda registrado correctamente | 
      | mesa ensamblado   | 200    | Tipo de Equipo mesa ensamblado registrado correctamente |
      | fresadora         | 200    | Tipo de Equipo fresadora registrado correctamente |
      | Cepillo Garlopa   | 200    | Tipo de Equipo Cepillo Garlopa registrado correctamente |
      | dobladora         | 200    | Tipo de Equipo dobladora registrado correctamente |
