import paquete.Logica;

public class PRODE {
    public static void main(String[]  args)
    {

        System.out.println("/--------------------------------");
        System.out.println("** Trabajo Integrador Argentina Programa 4.0 **");
        System.out.println("/--------------------------------");
        System.out.println("** Federico Wuthrich **");

        if (args.length < 2)
        {
            System.out.println("Error en rutas de archivos");
            return;
        }

        String f1 = args[0];
        String f2 = args[1];
        Logica x = new Logica(f1, f2);

        System.out.println("/n--- Tabla de Posiciones ---");
        System.out.println(x.listadoPuntos());
        System.out.println("/--------------------------------");
        System.out.println("Success!");
        System.out.println("/--------------------------------");
    }
}