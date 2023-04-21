package paquete;

import java.util.ArrayList;


public class Ronda
{
    private final int numero;
    private ArrayList<Partido> partidos;


    public Ronda()
    {
        numero = -1;
    }

    public Ronda(int numero)
    {
        this.numero = numero;
        this.partidos = new ArrayList<Partido>();
    }


    public void addPartido(Partido x)
    {
        partidos.add(x);
    }


    public int getNumero()
    {
        return numero;
    }


    public ArrayList<Partido> getPartidos()
    {
        return partidos;
    }


    public Partido getPartido(int id)
    {
        Partido x = null;
        for (Partido y : partidos)
        {
            if (y.getId() == id)
            {
                x = y;
                break;
            }
        }
        return x;
    }

    public int getSize()
    {
        return partidos.size();
    }

    @Override
    public String toString()
    {
        StringBuilder stb = new StringBuilder("- RONDA " + numero + ": " + "\n");
        for (Partido x : partidos)
        {
            stb.append(" ---> Partido\n" + x.getId() + ": \n");
            stb.append(x.toString());
            stb.append("\n");
        }
        return stb.toString();
    }
}
