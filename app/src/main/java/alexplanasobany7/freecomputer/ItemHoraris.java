package alexplanasobany7.freecomputer;

/**
 * Created by alexplanasobany on 3/6/17.
 */

public class ItemHoraris {

    private String Assignatura, Data_Inici, Data_final;


    public ItemHoraris(String assignatura, String data_Inici, String data_final) {
        Assignatura = assignatura;
        Data_final = data_final;
        Data_Inici = data_Inici;


    }

    public String getAssignatura() {
        return Assignatura;
    }

    public String getData_final() {
        return Data_final;
    }

    public String getData_Inici() {
        return Data_Inici;
    }

}
