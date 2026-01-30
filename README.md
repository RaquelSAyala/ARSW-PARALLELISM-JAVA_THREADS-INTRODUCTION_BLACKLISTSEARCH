# ARSW – Introducción al Paralelismo y Hilos en Java  
## Caso BlackListSearch

**Nombre:** Raquel Selma

**Curso:** Arquitecturas de Software – ARSW  
**Universidad:** Escuela Colombiana de Ingeniería Julio Garavito  

---

## Descripción

Este repositorio contiene la solución al ejercicio de introducción al paralelismo en Java, cuyo objetivo es comprender el uso de hilos (Threads), el ciclo de vida de los mismos y el impacto del paralelismo en el desempeño de aplicaciones reales.

El ejercicio se divide en tres partes principales: una introducción básica a hilos, la paralelización del caso BlackListSearch y el análisis de desempeño aplicando la Ley de Amdahl.

---

## Parte I – Introducción a Hilos en Java

Se implementaron tres hilos que imprimen números en diferentes rangos:

- Hilo 1: 0 – 99  
- Hilo 2: 99 – 199  
- Hilo 3: 200 – 299  

### Diferencia entre `start()` y `run()`

- `start()` crea un nuevo hilo de ejecución, permitiendo la ejecución concurrente de los hilos. La salida se presenta intercalada.
  <img width="1403" height="954" alt="image" src="https://github.com/user-attachments/assets/41386af6-ee89-4854-85ff-452419993594" />

  <img width="1393" height="746" alt="image" src="https://github.com/user-attachments/assets/c6feeb1e-c9d8-4028-b53c-2507cd7ab09d" />

  
- `run()` no crea un nuevo hilo; el método se ejecuta de forma secuencial en el hilo principal, produciendo una salida ordenada.
  <img width="1402" height="949" alt="image" src="https://github.com/user-attachments/assets/9fdca38b-98d0-43f1-ac11-c523f95d68c2" />
  
  <img width="1386" height="880" alt="image" src="https://github.com/user-attachments/assets/cb51d91c-5e8d-4a2f-86fe-30cf88d0e96f" />


El uso de `start()` permite aprovechar la concurrencia real del sistema.

---

## Parte II – Black List Search (Paralelización)

El proceso de validación de direcciones IP contra múltiples listas negras fue paralelizado para reducir el tiempo de ejecución, especialmente en casos donde las coincidencias están dispersas o no existen.

La solución implementa:
- División del espacio de búsqueda entre N hilos.
- Ejecución concurrente de búsquedas independientes.
- Sincronización mediante `join()` para esperar la finalización de todos los hilos.
- Consolidación de resultados para determinar si una IP es confiable o no.

Una dirección IP se considera **no confiable** si aparece en al menos cinco listas negras.

<img width="1289" height="258" alt="image" src="https://github.com/user-attachments/assets/4e3f6b0e-efde-450f-a45f-b89eda0a9463" />


---

---

## Parte III – Evaluación de Desempeño

Se realizaron pruebas de validación para una IP reportada de forma dispersa utilizando diferentes configuraciones:

- 1 hilo.
- Número de hilos igual a los núcleos del procesador.
- El doble del número de núcleos.
- 50 hilos.
- 100 hilos.

<img width="1081" height="344" alt="image" src="https://github.com/user-attachments/assets/7c707b0e-ab77-43f9-be62-5945939a64ea" />


---

## Parte IV – Análisis con la Ley de Amdahl

### ¿Por qué el mejor desempeño no se logra con 500 hilos?

El aumento excesivo de hilos genera sobrecostos asociados a cambios de contexto, planificación y competencia por recursos, lo que reduce el beneficio del paralelismo.

### Comparación entre núcleos y el doble de núcleos

Utilizar un número de hilos igual al número de núcleos suele ofrecer el mejor balance. Usar el doble de hilos no garantiza mejoras y puede degradar el desempeño en problemas intensivos en CPU.

### Paralelismo distribuido

El uso de un hilo en múltiples máquinas puede escalar mejor que muchos hilos en una sola CPU, siempre que los costos de comunicación y coordinación sean mínimos. Aun así, la fracción secuencial del algoritmo sigue limitando el desempeño según la Ley de Amdahl.

---

## Conclusiones

- El paralelismo mejora el desempeño en búsquedas masivas.
- Más hilos no implican necesariamente mejores tiempos.
- El número óptimo de hilos depende del hardware y del overhead de sincronización.
- La Ley de Amdahl permite entender los límites teóricos y prácticos del paralelismo.

---
