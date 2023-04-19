package paquete;

public class Equipo
{
    private final String nombre;
    private final String descripcion;

    public Equipo(String nombre, String descripcion)
    {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
    public String getNombre(){
        return nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    @Override
    public String toString() {
    return nombre;
    }
}
