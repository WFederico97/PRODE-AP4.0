package paquete;

public class ConfigFileErrorException extends Exception
{
    private String message = "!CONFIG ERROR, VERIFICAR ARCHIVO";

    public ConfigFileErrorException() {}

    public ConfigFileErrorException(String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
