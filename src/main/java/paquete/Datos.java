package paquete;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Datos {
    private  ArrayList<Persona> listaPersonas = new ArrayList<>();
    private  ArrayList<Equipo> listaEquipos = new ArrayList<>();
    private  ArrayList<Fase> listaFases = new ArrayList<>();
    private  File resultados;


    public Datos(String resultados, String[] db_data){
        this.resultados = new File(resultados);
        System.out.println("Archivo de partidos ----> " + this.resultados.getAbsolutePath());
        generatePartidos();
        generateParticipantes(db_data);

    }
    private void generatePartidos(){
        Pattern p = Pattern.compile("\\d+,\\d+(,[^,;_:&#]+,[^,;_:&#]+,\\d+){2}");

        int id_Partidos = 1;
        try(Scanner sc = new Scanner(resultados))
        {
            while (sc.hasNextLine())
            {
                String linea = sc.nextLine();
                Matcher m = p.matcher(linea);
                if (! m.matches())
                {
                    System.out.println("/*Error*/ La linea " + " " + linea + " No fue procesada correctamente"
                    + "/n/ten" + resultados.getAbsolutePath() + " - linea" + id_Partidos);
                    id_Partidos++;
                    continue;
                }

                String[] datos = linea.split(",");

                Fase fase = buscarFase(Integer.parseInt(datos[0]));
                if (fase == null)
                {
                    fase = new Fase(Integer.parseInt(datos[0]));
                    listaFases.add(fase);
                }
                Ronda ronda = buscarRonda(Integer.parseInt(datos[1]));
                if (ronda == null)
                {
                    ronda = new Ronda(Integer.parseInt(datos[1]));
                    fase.addRonda(ronda);
                }
                Equipo equipo1 = buscarEquipo(datos[2]);
                if (equipo1 == null)
                {
                    equipo1 = new Equipo(datos[2], datos[3]);
                    listaEquipos.add(equipo1);
                }
                Equipo equipo2 = buscarEquipo(datos[5]);
                if (equipo2 == null)
                {
                    equipo2 = new Equipo(datos[5], datos[6]);
                    listaEquipos.add(equipo2);
                }
                int goles1 = Integer.parseInt(datos[4]);
                int goles2 = Integer.parseInt(datos[7]);
                Partido partido = new Partido(id_Partidos,equipo1,equipo2,goles1,goles2);
                id_Partidos++;
                ronda.addPartido(partido);
            }
        }
        catch (IOException ex){
            System.out.println(ex.getMessage());
            System.exit(1);
        }
    }
    private void generateParticipantes(String[] db_data)
    {
        Scanner sc = new Scanner(System.in);
        try
        {

            Connection conn = DriverManager.getConnection(db_data[0], db_data[2], db_data[3]);
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM " + db_data[1];
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next())
            {
                Persona persona = buscarPersona(rs.getString("Nombre"));
                if (persona == null)
                {
                    persona = new Persona (rs.getString("Nombre"));
                    listaPersonas.add(persona);
                }
                ResultadoEnum res = null;
                switch (rs.getString("resultado").toLowerCase())
                {
                    case "ganador":
                        res = ResultadoEnum.GANADOR;
                        break;
                    case "perdedor":
                        res = ResultadoEnum.PERDEDOR;
                        break;
                    case "empate":
                        res = ResultadoEnum.EMPATE;
                        break;
                }

                if (res == null) continue;

                Partido partido = buscarPartido( rs.getInt("idPartido"));

                if (partido == null)
                    continue;

                if (pronosticoRepetido(rs.getInt("idPartido"), persona))
                    continue;

                Equipo equipo = buscarEquipo(rs.getString("equipo"));
                if (equipo == null || !(equipo.equals(partido.getEquipo1()) || equipo.equals(partido.getEquipo2())))
                    continue;

                Pronostico pron = new Pronostico (partido, equipo,res);
                persona.addPronostico(pron);
            }
            conn.close();
            stmt.close();
            rs.close();
        }
        catch (SQLException ex){
            System.out.println("/*Error en base de datos*/ : " + ex.getMessage());
            String host = System.getenv("MYSQL_HOST");
            String user = System.getenv("MYSQL_USER");
            String password = System.getenv("MYSQL_PASS");
            System.out.println("Host: " + host + ", User: " + user + ", Password: " + password );
        }
    }
    private Fase buscarFase(int num)
    {
        Fase x = null;
        for (Fase y : listaFases)
        {
            if (y.getNumero() == num)
            {
                x = y;
                break;
            }
        }
        return x;
    }

    private Partido buscarPartido(int num)
    {
        Partido p = null;
        for (Fase x : listaFases)
        {
            ArrayList<Ronda> rondas = x.getRondas();
            for (Ronda y : rondas)
            {
                p = y.getPartido(num) ;
                if (p != null) return p;
            }
        }
        return p;
    }

    private Persona buscarPersona(String nombre)
    {
        Persona x = null;
        for (Persona y : listaPersonas)
        {
            if (nombre.equals(y.getNombre()))
            {
                x = y;
                break;
            }
        }
        return x;
    }

    private  Equipo buscarEquipo(String nombre)
    {
        Equipo x = null;
        for (Equipo y : listaEquipos)
        {
            if (nombre.equals(y.getNombre()))
            {
                x = y;
                break;
            }
        }
        return x;
    }
    private Ronda buscarRonda(int num)
    {
        Ronda r = null;
        for (Fase x : listaFases)
        {
            ArrayList<Ronda> rondas = x.getRondas();
            for (Ronda y : rondas)
            {
                if (y.getNumero() == num)
                {
                    r = y;
                    return r;
                }
            }
        }
        return  r;
    }

    public ArrayList<Persona> getListaPersonas() {
        return listaPersonas;
    }

    public ArrayList<Fase> getListaFases() {
        return listaFases;
    }

    private boolean pronosticoRepetido(int id, Persona x)
    {
        for (Pronostico y : x.getPronosticos())
        {
            if (y.getIdPartido() == id)
                return true;
        }
        return  false;
    }
}

