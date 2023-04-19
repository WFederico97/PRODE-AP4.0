package paquete;
import java.util.ArrayList;

public class Persona
{
    private final String nombre;
    private ArrayList<Pronostico> pronosticos;

    public Persona(String nombre)
    {
        this.nombre = nombre;
        this.pronosticos = new ArrayList<Pronostico>();
    }

    public void addPronostico(Pronostico x)
    {
        pronosticos.add(x);
    }
    public String getNombre()
    {
        return nombre;
    }
    public ArrayList<Pronostico> getPronosticos()
    {
        return pronosticos;
    }

    public Pronostico getPronostico(int id)
    {
        for (Pronostico x : pronosticos)
        {
            if (id == x.getIdPartido()) return x;
        }
        return null;
    }

    public String toString()
    {
        StringBuilder stb = new StringBuilder("NOMBRE: " + nombre + "/n");
        for (Pronostico x : pronosticos)
        {
            stb.append("-*- Pronostico del partido" + x.getIdPartido() + ": /n");
            stb.append(x.toString());
        }
        return stb.toString();
    }
}
