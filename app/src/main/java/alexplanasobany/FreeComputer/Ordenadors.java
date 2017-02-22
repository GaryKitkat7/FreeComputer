package alexplanasobany7.freecomputer;

/**
 * Created by alexplanasobany on 22/2/17.
 */

public class Ordenadors {
    private String nombre;
    private int idDrawable;
    private boolean Reserva;


    public Ordenadors(String nombre, int idDrawable, boolean Reserva) {
        this.nombre = nombre;
        this.idDrawable = idDrawable;
        this.Reserva = Reserva;
    }

    public String getNombre() {
        return nombre; //Retorna el nom del cotxe
    }

    public int getIdDrawable() {
        return idDrawable; //Retorna el identificador de la imatge que hi ha en la carpeta drawable
    }
    public boolean getReserva(){
        return Reserva;
    }

    public int getId() {
        return nombre.hashCode();
    }

    // TODO: Recorda cambiar les imatges
    public void PosarFotoReserva(int posicio){
        Ordenadors.ITEMS[posicio] = new Ordenadors("Lliure", R.drawable.pc, true);
    }

    public void PosarFotoNoReserva(int posicio){
        Ordenadors.ITEMS[posicio] = new Ordenadors("Reservat", R.drawable.no_pc, false);
    }

    // TODO: Mirar perquè no puc posar un "for" a dins de la funció. Te problemes amb l'"static", s'ha de buscar
    // TODO: ja que es més fàcil d'implementar, i podria funcionar per a classes amb diferent mida

    // TODO: Perquè no puc posar el primer camp com un "STRING", només mu deixa fer a la Activitat i al layout
    public static Ordenadors[] ITEMS = {  // Vector amb totes les imatge que aniran sortin al GridView
            new Ordenadors("Lliure", R.drawable.pc,true),
            new Ordenadors("Lliure", R.drawable.pc,true),
            new Ordenadors("Lliure", R.drawable.pc,true),
            new Ordenadors("Lliure", R.drawable.pc,true),
            new Ordenadors("Lliure", R.drawable.pc,true),
            new Ordenadors("Lliure", R.drawable.pc,true),
            new Ordenadors("Lliure", R.drawable.pc,true),
            new Ordenadors("Lliure", R.drawable.pc,true),
            new Ordenadors("Lliure", R.drawable.pc,true),
            new Ordenadors("Lliure", R.drawable.pc,true),
            new Ordenadors("Lliure", R.drawable.pc,true),
            new Ordenadors("Lliure", R.drawable.pc,true),
            new Ordenadors("Lliure", R.drawable.pc,true),
            new Ordenadors("Lliure", R.drawable.pc,true),
            new Ordenadors("Lliure", R.drawable.pc,true),
            new Ordenadors("Lliure", R.drawable.pc,true),


    };

    /**
     * Obte un item basat en el seu ID
     *
     * @param id
     * @return Ordenadors
     */
    public static Ordenadors getItem(int id) {
        for (Ordenadors item : ITEMS) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }
}
