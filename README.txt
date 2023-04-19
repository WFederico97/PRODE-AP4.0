///******/////
PROYECTO FINAL ARGENTINA PROGRAMA 4.0
///******/////
AUTOR: FEDERICO WUTHRICH
///******/////

///---------------README----------------///

El programa se ejecuta a traves de consola con el siguiente comando:
.\prode-v2> java -jar PRODE-V2-jar-with-dependencies.jar resultados.csv config.csv **el archivo jar y los csv tienen que estar en el mismo directorio para ser ejecutados**

**En la clase Logica.java , el programa recibe dos parametros que son el de resultados.csv y config.csv, donde en este ultimo archivo se
encuentra la url de la db con los puntajes de acierto tanto para un partido, una ronda y una fase.**

**En la clase Datos.java , el programa recibe la info tanto de resultados.csv y config.csv, siendo que toma la info de config.csv para obtener la informacion
de la db como un array para los pronosticos y al archivo resultados.csv para obtener los resultados de los partidos y así poder calcular
si las predicciones fueron correctas y generar los puntajes**