import paquete.Logica;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class PRODE {
    public static void main(String[]  args) {
        System.out.println("CARGANDO PROGRAMA, SEA PACIENTE..........");
        Timer timer = new Timer();
        timer.schedule(new MiTarea(args,timer), 5000);
    }
    static class MiTarea extends TimerTask {
        String[] args;
        Timer timer;

        public MiTarea(String[] args, Timer timer) {
            this.args = args;
            this.timer = timer;
        }

        public void run() {
            System.out.println("/--------------------------------");
            System.out.println("** Trabajo Integrador Argentina Programa 4.0 **");
            System.out.println("/--------------------------------\n\t");
            System.out.println("/// ** Federico Wuthrich ** ///");
            System.out.println("/--------------------------------\n\t");

            if (args.length < 2) {
                System.out.println("Error en rutas de archivos");
                return;
            }

            String f1 = args[0];
            String f2 = args[1];
            Logica x = new Logica(f1, f2);

            System.out.println("\n--- RANKING ---");
            System.out.println(x.listadoPuntos());

            Scanner sc = new Scanner(System.in);
            System.out.println("¿Desea ver los pronosticos? (y/n) >>>");
            String opc = sc.nextLine();
            if ( opc.toLowerCase().equals("y") ) {
                System.out.println("\n---PRONOSTICOS DE CADA JUGADOR ---");
                System.out.println(x.listadoPronosticos());
            }
            System.out.println("/--------------------------------");
            System.out.println("SUCCESS!");
            System.out.println("/--------------------------------\n\t");
            timer.cancel();
        }
    }
}