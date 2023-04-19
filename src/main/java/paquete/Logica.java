package paquete;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Logica
{
    private int puntosAcertados;
    private int puntosRonda;
    private int puntosFase;
    private  File config;
    private  Datos datos;

    public Logica (String resultados , String config)
    {
        this.config = new File(config);
        System.out.println("ARCHIVO DE CONFIG --->" + this.config.getAbsolutePath());
        String[] db = readConfigFile();
        datos = new Datos(resultados, db);
    }

    private int calcularPuntos(Persona participante)
    {
        int puntos = 0;
        for (Fase f : datos.getListaFases())
        {
            boolean faseCompleta = true;
            for (Ronda r : f.getRondas())
            {
                boolean rondaCompleta = true;
                for (Partido p : r.getPartidos())
                {
                    Pronostico x = participante.getPronostico(p.getId());
                    if (x != null && x.acierto()) puntos += puntosAcertados;
                    else
                    {
                        faseCompleta = false;
                        rondaCompleta  = false;
                    }
                }
                if (rondaCompleta) puntos += puntosRonda;
            }
            if (faseCompleta) puntos += puntosFase;
        }
        return puntos;
    }

    private String[] readConfigFile()
    {
        Pattern p = Pattern.compile("jdbc:mysql://[^,;\\s]+:\\d+/([^,;\\s]+,){4}(\\d+,){2}\\d+");
        try (Scanner sc = new Scanner(config))
        {
            String linea = sc.nextLine();
            Matcher m = p.matcher(linea);
            if ( ! m.matches() ) throw new ConfigFileErrorException();

            String[] cfg = linea.split(";");
            puntosAcertados = Integer.parseInt(cfg[4]);
            puntosRonda = Integer.parseInt(cfg[5]);
            puntosFase = Integer.parseInt(cfg[6]);
            String [] y = new String[4];
            System.arraycopy(cfg, 0, y, 0, 4);
            return y;
        }
        catch (IOException ex)
        {
            System.out.println(ex.getMessage() + "*--- !Cerrando Programa ! ---*");
            System.exit(1);
        }
        catch (ConfigFileErrorException ex)
        {
            System.out.println(ex.getMessage());
            System.exit(1);
        }
        return null;
    }

    public String listadoPuntos()
    {
        StringBuilder stb = new StringBuilder();
        for (Persona x : datos.getListaPersonas())
        {
            stb.append(x.getNombre() + "--- Puntaje: " + calcularPuntos(x) + "\n");
        }
        return stb.toString();
    }
}