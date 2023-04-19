package paquete;

import java.util.ArrayList;
public class Fase
{
    private final int numero;
    private ArrayList<Ronda> rondas;

    public Fase(int numero)
    {
        this.numero = numero;
        this.rondas = new ArrayList<>();
    }
    public void addRonda(Ronda x){
        rondas.add(x);
    }
    public int getNumero(){
        return numero;
    }
    public ArrayList<Ronda> getRondas() {
        return rondas;
    }
    @Override
    public String toString()
    {
        StringBuilder stb = new StringBuilder("Fase" + numero + ":\n");
        for (Ronda x : rondas)
        {
            stb.append(x.toString());
        }
        return stb.toString();
    }
}
