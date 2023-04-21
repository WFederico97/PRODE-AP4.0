package paquete;


public class Pronostico
{
    private final Partido partido;
    private final Equipo equipo;
    private final ResultadoEnum resultado;

    public Pronostico(Partido partido, Equipo equipo, ResultadoEnum resultado)
    {
        this.partido = partido;
        this.equipo = equipo;
        this.resultado = resultado;
    }


    public boolean acierto()
    {
        ResultadoEnum x = partido.confirmarResultado(equipo);
        if (x.equals(resultado))
            return true;

        return false;
    }

    public Partido getPartido()
    {
        return partido;
    }

    public Equipo getEquipo()
    {
        return equipo;
    }

    public ResultadoEnum getResultado()
    {
        return resultado;
    }


    public int getIdPartido()
    {
        return partido.getId();
    }

    @Override
    public String toString()
    {
        if (resultado == ResultadoEnum.EMPATE)
            return partido.toString() + "\n\tPREDICCION: " + resultado + "\n";

        return partido.toString() + "\n\tPREDICCION: " + equipo.toString() + " - " + resultado + "\n";
    }

}