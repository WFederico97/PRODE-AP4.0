package paquete;

import javax.naming.ConfigurationException;
import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Logica
{
    private int puntosAcertados;
    private int puntosRonda;
    private int puntosFase;
    private File config;  Datos datos;


    public Logica(String resultados, String config)
    {
        this.config = new File(config);
        System.out.println("ARCHIVO DE CONFIG --->" + this.config.getAbsolutePath());
        String[] db = readConfigFile();
        datos = new Datos(resultados, db);
    }


    private int calcularPuntos(Persona participante)
    {
        int puntos = 0;
        for (Fase f : datos.getlistaFases())
        {
            boolean fase_completa = true;
            for (Ronda r : f.getRondas())
            {
                boolean ronda_completa = true;
                for (Partido p : r.getPartidos())
                {
                    Pronostico x = participante.getPronostico(p.getId());
                    if ( x != null && x.acierto() ) puntos += puntosAcertados;
                    else
                    {
                        fase_completa = false;
                        ronda_completa = false;
                    }
                }
                if (ronda_completa) puntos += puntosRonda;
            }
            if (fase_completa) puntos += puntosFase;
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

            String[] cfg = linea.split(",");
            puntosAcertados = Integer.parseInt(cfg[4]);
            puntosRonda = Integer.parseInt(cfg[5]);
            puntosFase = Integer.parseInt(cfg[6]);
            String [] y = new String[4];
            System.arraycopy(cfg, 0, y, 0, 4);
            return y;
        }
        catch(IOException ex)
        {
            System.out.println(ex.getMessage() + "*--- !Cerrando Programa ! ---*");
            System.exit(1);
        }
        catch(ConfigFileErrorException ex)
        {
            System.out.println(ex.getMessage());
            System.exit(1);
        }
        return null;
    }


    public String listadoPuntos()
    {
        StringBuilder stb = new StringBuilder();
        List<Persona> listaPersonas = datos.getlistaPersonas();

        Collections.sort(listaPersonas, new Comparator<Persona>() {
            @Override
            public int compare(Persona p1, Persona p2) {
                return Integer.compare(calcularPuntos(p2), calcularPuntos(p1));
            }
        });

        for (Persona x : listaPersonas)
        {
            stb.append(x.getNombre() + "--- Puntaje: " + calcularPuntos(x) + "\n");
        }
        return stb.toString();
    }

    public String listadoPronosticos()
    {
        StringBuilder stb = new StringBuilder();
        for (Persona x : datos.getlistaPersonas())
        {
            stb.append(x.toString() + "\n");
        }
        return stb.toString();
    }
}