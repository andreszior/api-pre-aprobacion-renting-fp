# **Aplicación Endpoints de autoaprobación de solicitudes**

<p> Se solicita una aplicación de autoaprobación de solicitudes de renting de vehículos. El stack tecnológico solicitado es un frontend en Angular con un backend en Java Spring. La comunicación entre back y front se realizará mediante un API ReST. Vuestro equipo se encargará del API de back end.</p>

<p> Se debe poder dar de alta/actualizar un cliente potencial mediante llamadas API. Se deben poder borrar clientes si no tienen solicitudes realizadas. Se deben poder borrar solicitudes no aprobadas.</p>

<p> Deben existir endpoints para que los analistas puedan dar resultados finales de las solicitudes (Aprobada, denegada, aprobada con garantías).</p>

<p> Todos los borrados deben ser lógicos por temas de trazabilidad.</p>

<p> Para realizar una solicitud se deben indicar el id de cliente e indicar los datos de la solicitud. Una solicitud puede tener uno o varios vehículos. Se dispondrá de un endpoint para consultar los vehículos disponibles. Este endpoint deberá mostrar al menos: marca, modelo, precio del vehículo (inversión), cilindrada, potencia, color, número de plazas, lista de descripción de extras, cuota mensual base. Para cada vehículo se podrán solicitar ciertos extras (como color especial, bola de remolque, etc). Un extra supondrá un aumento de cuota en importe fijo o porcentual. En caso de aumento de cuota porcentual se aumenta la inversión en el mismo porcentaje. En caso de valor fijo se aumenta la inversión el importe del extra por 12.</p>

<p> La aplicación debe calcular la cuota. La cuota base se calcula con un plazo de 12 meses. Si el plazo se reduce se aumenta un 10% por mes de reducción. Si el plazo se aumenta, la cuota se reduce en un 3% por mes de aumento, hasta un máximo de un descuento de un 20%.</p>

<p> Para preaprobarse automáticamente una solicitud deben cumplirse todas las condiciones de aprobación y no cumplirse ninguna de denegación.</p>

<p> Si se cumple una sola condición de denegación se auto denegaría.</p>

<p> En cualquier otro caso quedaría en pendiente de revisar por analista.</p>

## **Reglas de aprobación**

| Nº | APROBACIONES AUTOMATICAS cumplir TODAS |
| --- | --- |
| 1 | INVERSION <= INGRESOS NETO (ASALARIADO + AUTONOMO) |
| 2 | INVERSION < = INGRESOS BRUTOS X 3 (Solo aplica a autónomos) |
| 3 | Deuda < IMPORTE DE UNA CUOTA MENSUAL |
| 4 | ANTIGÜEDAD EMPLEO >= MINIMO 3 AÑOS (ASALARIADO/NÓMINA) |
| 5 | INVERSION < =80 K |
| 6 | Nacionalidad = Española |
| 7 | Si Cliente NO ha sido previamente rechazado (en un plazo de 2 años desde fecha actual) |
| 8 | Si Cliente previamente NO ha sido aprobado con garantías (en un plazo de 2 años desde fecha actual) |
| 9 | El Cliente tiene Rating < 5 en SCORING |
| 10 | * El CIF/NIF de la empresa donde trabaja el cliente se encuentra en INFORMA con un resultado antes de impuesto medio de los últimos 3 años superior a 150.000 € |
| 11 | Cliente NO es garante en operaciones aprobadas con garantías (Solo para nuevos clientes, es decir no tiene productos contratados activos con la empresa de ningún tipo) |

**Notas regla 10:**

- Solo aplica a asalariados
- Aplican los años de los que se disponga, si solo se dispone de un año se coge ese año. Si se dispone de 2 años se hace la media de esos dos años.
- Si el último balance es de hace más de dos años se considera que no está en INFORMA.

## **Reglas de denegación**

| Nº | DENEGACIONES AUTOMATICAS (cumplir UNA) |
| --- | --- |
| 1 | TITULAR INCLUIDO EN LA HERRAMIENTA DE IMPAGOS INTERNOS |
| 2 | TITULAR con scoring >= 6 |
| 3 | Deudas IMPORTE >= UNA CUOTA MENSUAL, independientemente del tipo de cedente o acreedor |
| 4 | Categoría de cedente o acreedor de la deuda = ENTIDAD FINANCIERA O DE RENTING (para cualquier importe de deuda) |
| 5 | Edad cliente < 18 años |
| 6 | Edad cliente + Plazo a contratar >= 80 |

<p> Datos necesarios. Persona contratante:</p>

- Fecha de nacimiento
- Ingresos netos anuales como asalariado
- Ingresos netos anuales como autónomo
- Ingresos brutos anuales como autónomo
- Fecha inicio empleo como asalariado
- Nacionalidad
- CIF empleador

<p> Solicitud renting:</p>

- Fecha solicitud
- Vehículos
- Inversión (coste total coches)
- Cuota
- Plazo (meses)
- Fecha inicio vigor renting
- Resultado
- Garantías
    - Tipo garantía (aval financiero/avalista)
    - NIF Avalista
    - Importe aval
    - Descripción aval
