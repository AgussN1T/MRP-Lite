# language: es

Característica: administrar pedidos de fabricación
   permite generar, recuperar y gestionar los pedidos de fabricación

   Esquema del escenario: Generar nuevos pedidos de fabricación
   Dada que existe el "<producto>" 
   Y el cliente con <cuit>
   Cuando se solicita generar un pedido al cliente <cuit> con fecha de pedido <fechaPedido> para entregar en la fecha <fechaEntrega> la cantidad de <cantidad> del producto "<Producto>"
   Entonces se espera el siguiente <status> con la "<respuesta>"

   Ejemplos:
   | cuit      | fechaPedido  | fechaEntrega | cantidad | producto        | status | respuesta                                    |
   | 1000000001| "2023-05-04" | "2023-05-29" | 10       | mesa 1.80x0.70  |  200   | Pedido de fabricación generado correctamente |
   | 1000000001| "2023-05-04" | "2023-05-29" | 20       | mesa 2x1        |  200   | Pedido de fabricación generado correctamente |
   | 1000000001| "2023-05-04" | "2023-05-29" | 25       | silla estándar  |  200   | Pedido de fabricación generado correctamente |
   | 1000000001| "2023-05-04" | "2023-05-29" | 30       | banqueta        |  200   | Pedido de fabricación generado correctamente |
   | 1000000001| "2023-05-04" | "2023-05-29" | 23       | zócalos de 1.5m |  200   | Pedido de fabricación generado correctamente |
   | 1000000001| "2023-05-04" | "2023-05-29" | 40       | zócalos de 2m   |  200   | Pedido de fabricación generado correctamente |
   | 2000000002| "2023-05-04" | "2023-05-30" | 50       | mesa 1.80x0.70  |  200   | Pedido de fabricación generado correctamente |
   | 2000000002| "2023-05-04" | "2023-05-30" | 70       | mesa 2x1        |  200   | Pedido de fabricación generado correctamente |
   | 2000000002| "2023-05-04" | "2023-05-30" | 95       | silla estándar  |  200   | Pedido de fabricación generado correctamente |
   | 2000000002| "2023-05-04" | "2023-05-30" | 130      | banqueta        |  200   | Pedido de fabricación generado correctamente |
   | 2000000002| "2023-05-04" | "2023-05-30" | 123      | zócalos de 1.5m |  200   | Pedido de fabricación generado correctamente |
   | 2000000002| "2023-05-04" | "2023-05-30" | 140      | zócalos de 2m   |  200   | Pedido de fabricación generado correctamente |
   | 2000000002| "2023-05-04" | "2023-05-30" | 100      | puerta doble    |  200   | Pedido de fabricación generado correctamente |
   | 2000000002| "2023-05-04" | "2023-05-30" | 150      | perchero de pie |  200   | Pedido de fabricación generado correctamente |
   | 3000000003| "2023-05-04" | "2023-05-28" | 250      | mesa 1.80x0.70  |  200   | Pedido de fabricación generado correctamente |
   | 3000000003| "2023-05-04" | "2023-05-28" | 270      | mesa 2x1        |  200   | Pedido de fabricación generado correctamente |
   | 3000000003| "2023-05-04" | "2023-05-28" | 295      | silla estándar  |  200   | Pedido de fabricación generado correctamente |
   | 3000000003| "2023-05-04" | "2023-05-28" | 330      | banqueta        |  200   | Pedido de fabricación generado correctamente |
   | 3000000003| "2023-05-04" | "2023-05-28" | 323      | zócalos de 1.5m |  200   | Pedido de fabricación generado correctamente |
   | 3000000003| "2023-05-04" | "2023-05-28" | 340      | zócalos de 2m   |  200   | Pedido de fabricación generado correctamente |
   | 3000000003| "2023-05-04" | "2023-05-28" | 300      | zócalos de 2.5m |  200   | Pedido de fabricación generado correctamente |
   | 3000000003| "2023-05-04" | "2023-05-28" | 350      | puerta estándar |  200   | Pedido de fabricación generado correctamente |
   | 4000000004| "2023-05-04" | "2023-05-31" | 150      | mesa 1.80x0.70  |  200   | Pedido de fabricación generado correctamente |
   | 4000000004| "2023-05-04" | "2023-05-31" | 170      | mesa 2x1        |  200   | Pedido de fabricación generado correctamente |
   | 4000000004| "2023-05-04" | "2023-05-31" | 195      | silla estándar  |  200   | Pedido de fabricación generado correctamente |
   | 4000000004| "2023-05-04" | "2023-05-31" | 230      | banqueta        |  200   | Pedido de fabricación generado correctamente |
   | 4000000004| "2023-05-04" | "2023-05-31" | 223      | zócalos de 1.5m |  200   | Pedido de fabricación generado correctamente |
   | 4000000004| "2023-05-04" | "2023-05-31" | 240      | zócalos de 2m   |  200   | Pedido de fabricación generado correctamente |
   | 4000000004| "2023-05-04" | "2023-05-31" | 200      | zócalos de 2.5m |  200   | Pedido de fabricación generado correctamente |
   | 4000000004| "2023-05-04" | "2023-05-31" | 250      | puerta estándar |  200   | Pedido de fabricación generado correctamente |
   | 4000000004| "2023-05-04" | "2023-05-31" | 200      | puerta doble    |  200   | Pedido de fabricación generado correctamente |
   | 4000000004| "2023-05-04" | "2023-05-31" | 250      | perchero de pie |  200   | Pedido de fabricación generado correctamente |
   | 5000000005| "2023-05-04" | "2023-05-30" | 400      | zócalos de 2.5m |  200   | Pedido de fabricación generado correctamente |
   | 5000000005| "2023-05-04" | "2023-05-30" | 450      | puerta estándar |  200   | Pedido de fabricación generado correctamente |
   | 5000000005| "2023-05-04" | "2023-05-30" | 400      | puerta doble    |  200   | Pedido de fabricación generado correctamente |
   | 5000000005| "2023-05-04" | "2023-05-30" | 450      | perchero de pie |  200   | Pedido de fabricación generado correctamente |
   | 1000000001| "2023-05-20" | "2023-06-06" | 10       | mesa 1.80x0.70  |  200   | Pedido de fabricación generado correctamente |
   | 1000000001| "2023-05-20" | "2023-06-06" | 20       | mesa 2x1        |  200   | Pedido de fabricación generado correctamente |
   | 1000000001| "2023-05-20" | "2023-06-06" | 25       | silla estándar  |  200   | Pedido de fabricación generado correctamente |
   | 1000000001| "2023-05-20" | "2023-06-06" | 30       | banqueta        |  200   | Pedido de fabricación generado correctamente |
   | 1000000001| "2023-05-20" | "2023-06-06" | 23       | zócalos de 1.5m |  200   | Pedido de fabricación generado correctamente |
   | 1000000001| "2023-05-20" | "2023-06-06" | 40       | zócalos de 2m   |  200   | Pedido de fabricación generado correctamente |
   | 2000000002| "2023-05-20" | "2023-06-06" | 50       | mesa 1.80x0.70  |  200   | Pedido de fabricación generado correctamente |
   | 2000000002| "2023-05-20" | "2023-06-06" | 70       | mesa 2x1        |  200   | Pedido de fabricación generado correctamente |
   | 2000000002| "2023-05-20" | "2023-06-06" | 95       | silla estándar  |  200   | Pedido de fabricación generado correctamente |
   | 2000000002| "2023-05-20" | "2023-06-06" | 130      | banqueta        |  200   | Pedido de fabricación generado correctamente |
   | 2000000002| "2023-05-20" | "2023-06-06" | 123      | zócalos de 1.5m |  200   | Pedido de fabricación generado correctamente |
   | 2000000002| "2023-05-20" | "2023-06-06" | 140      | zócalos de 2m   |  200   | Pedido de fabricación generado correctamente |
   | 2000000002| "2023-05-20" | "2023-06-06" | 100      | puerta doble    |  200   | Pedido de fabricación generado correctamente |
   | 2000000002| "2023-05-20" | "2023-06-06" | 150      | perchero de pie |  200   | Pedido de fabricación generado correctamente |
   | 3000000003| "2023-05-20" | "2023-06-06" | 250      | mesa 1.80x0.70  |  200   | Pedido de fabricación generado correctamente |
   | 3000000003| "2023-05-20" | "2023-06-06" | 270      | mesa 2x1        |  200   | Pedido de fabricación generado correctamente |
   | 3000000003| "2023-05-20" | "2023-06-06" | 295      | silla estándar  |  200   | Pedido de fabricación generado correctamente |
   | 3000000003| "2023-05-20" | "2023-06-06" | 330      | banqueta        |  200   | Pedido de fabricación generado correctamente |
   | 3000000003| "2023-05-20" | "2023-06-06" | 323      | zócalos de 1.5m |  200   | Pedido de fabricación generado correctamente |
   | 3000000003| "2023-05-20" | "2023-06-06" | 340      | zócalos de 2m   |  200   | Pedido de fabricación generado correctamente |
   | 3000000003| "2023-05-20" | "2023-06-06" | 300      | zócalos de 2.5m |  200   | Pedido de fabricación generado correctamente |
   | 3000000003| "2023-05-20" | "2023-06-06" | 350      | puerta estándar |  200   | Pedido de fabricación generado correctamente |
   | 4000000004| "2023-05-20" | "2023-06-06" | 150      | mesa 1.80x0.70  |  200   | Pedido de fabricación generado correctamente |
   | 4000000004| "2023-05-20" | "2023-06-06" | 170      | mesa 2x1        |  200   | Pedido de fabricación generado correctamente |
   | 4000000004| "2023-05-20" | "2023-06-06" | 195      | silla estándar  |  200   | Pedido de fabricación generado correctamente |
   | 4000000004| "2023-05-20" | "2023-06-06" | 230      | banqueta        |  200   | Pedido de fabricación generado correctamente |
   | 4000000004| "2023-05-20" | "2023-06-06" | 223      | zócalos de 1.5m |  200   | Pedido de fabricación generado correctamente |
   | 4000000004| "2023-05-20" | "2023-06-06" | 240      | zócalos de 2m   |  200   | Pedido de fabricación generado correctamente |
   | 4000000004| "2023-05-20" | "2023-06-06" | 200      | zócalos de 2.5m |  200   | Pedido de fabricación generado correctamente |
   | 4000000004| "2023-05-20" | "2023-06-06" | 250      | puerta estándar |  200   | Pedido de fabricación generado correctamente |
   | 4000000004| "2023-05-20" | "2023-06-06" | 200      | puerta doble    |  200   | Pedido de fabricación generado correctamente |
   | 4000000004| "2023-05-20" | "2023-06-06" | 250      | perchero de pie |  200   | Pedido de fabricación generado correctamente |
   | 5000000005| "2023-05-20" | "2023-06-06" | 400      | zócalos de 2.5m |  200   | Pedido de fabricación generado correctamente |
   | 5000000005| "2023-05-20" | "2023-06-06" | 450      | puerta estándar |  200   | Pedido de fabricación generado correctamente |
   | 5000000005| "2023-05-20" | "2023-06-06" | 400      | puerta doble    |  200   | Pedido de fabricación generado correctamente |
   | 5000000005| "2023-05-20" | "2023-06-06" | 450      | perchero de pie |  200   | Pedido de fabricación generado correctamente |
