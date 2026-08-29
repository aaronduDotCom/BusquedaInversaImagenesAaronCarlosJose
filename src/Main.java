

import Model.Services.Estructuras.Iterator;
import Model.Services.Estructuras.Vector;
import javax.swing.*;


public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            } catch (Exception ignored) {
            }

            JFrame window = new JFrame("Reservas");
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setSize(800, 600);
            window.setLocationRelativeTo(null);
            window.setContentPane(new JPanel()); // pantalla vacía
            window.setVisible(true);
        });


        Vector<String> vector = new Vector<>(3);

        System.out.println("=== TEST VECTOR ===");

        vector.insertar("Hola");
        vector.insertar("Mundo");
        vector.insertar("Java");

        System.out.println("Tamaño: " + vector.tamanno());

        System.out.println("Posición 0: " + vector.getPos(0));
        System.out.println("Posición 1: " + vector.getPos(1));
        System.out.println("Posición 2: " + vector.getPos(2));

        System.out.println("\n=== TEST VECTOR ITERATOR ===");

        Iterator<String> iterador = vector.getIterador();

        while (iterador.hasNext()) {
            System.out.println(iterador.next());
        }

        System.out.println("\n=== TEST CAPACIDAD ===");

        vector.insertar("Extra");

        System.out.println("Tamaño después de insertar de más: "
                + vector.tamanno());

    }
}