package Presentation.Busqueda.PantallaResultado;

import Model.Imagen.ImagenData;
import Model.Services.Busqueda.Resultado;
import Model.Estructuras.Iterator;
import Model.Imagen.Imagen;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

public class View extends JFrame implements PropertyChangeListener {

    private JPanel contentPanel;
    private JButton nuevaBusquedaButton;
    private JButton salirButton;
    private JLabel queryImagePreview;
    private JLabel informationLabel;
    private JScrollPane scroollPanel;
    private JPanel results;

    private Controller controller;
    private Model model;

    public View() {
        setTitle("Resultados de la búsqueda");
        setContentPane(contentPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        queryImagePreview.setHorizontalAlignment(SwingConstants.CENTER);
        queryImagePreview.setVerticalAlignment(SwingConstants.CENTER);

        results.removeAll();

        results.setLayout(new GridLayout(0, 3, 15, 15));
        results.setBorder(new EmptyBorder(15, 15, 15, 15));
        scroollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroollPanel.getVerticalScrollBar().setUnitIncrement(16);
        nuevaBusquedaButton.addActionListener(e -> {controller.nuevaBusqueda();});
        salirButton.addActionListener(e -> {System.exit(0);
        });
    }

    public JPanel getContentPanel() {
        return contentPanel;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setModel(Model model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        switch (evt.getPropertyName()) {
            case Model.CURRENT:
                Imagen imagenConsultada = model.getCurrent();
                if (imagenConsultada == null) {
                    queryImagePreview.setIcon(null);
                    queryImagePreview.setText("Sin imagen consultada");
                } else {
                    mostrarImagen(queryImagePreview, imagenConsultada.getRuta(), 250, 160);
                }
                break;
            case Model.METHOD:
                informationLabel.setText("Método: " + model.getMethod());
                break;
            case Model.RESULTS:

                results.removeAll();
                informationLabel.setText("<html>" + "Método: " + model.getMethod() + "<br>" + "Resultados encontrados: " + model.getResults().tamano() + "</html>");
                Iterator<Resultado> iterador = model.getResults().getIterador();
                while (iterador.hasNext()) {
                    Resultado resultado = iterador.next();
                    //JPanel tarjeta = crearTarjeta(resultado);
                    //resultado.add(tarjeta);
                }
                results.revalidate();
                results.repaint();
                break;
        }

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void mostrarImagen(JLabel label, String ruta, int anchoMaximo, int altoMaximo) {
        if (ruta == null || ruta.isBlank()) {
            label.setIcon(null);
            label.setText("Imagen no disponible");
            return;
        }

        ImageIcon iconoOriginal = new ImageIcon(ruta);

        int anchoOriginal = iconoOriginal.getIconWidth();
        int altoOriginal = iconoOriginal.getIconHeight();

        if (anchoOriginal <= 0 || altoOriginal <= 0) {
            label.setIcon(null);
            label.setText("Imagen no disponible");
            return;
        }

        double escala = Math.min((double) anchoMaximo / anchoOriginal, (double) altoMaximo / altoOriginal);
        int nuevoAncho = Math.max(1, (int) (anchoOriginal * escala));
        int nuevoAlto = Math.max(1, (int) (altoOriginal * escala));
        Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(nuevoAncho, nuevoAlto, Image.SCALE_SMOOTH);
        label.setText("");
        label.setIcon(new ImageIcon(imagenEscalada));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
    }

    private JPanel crearTarjeta(Resultado resultado) {

        ImagenData imagenData = resultado.getImagenData();

        JPanel tarjeta = new JPanel(new BorderLayout(5, 5));
        tarjeta.setPreferredSize(new Dimension(240, 220));
        tarjeta.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.GRAY, 1), new EmptyBorder(10, 10, 10, 10)));
        JLabel imagenLabel = new JLabel("Imagen no disponible", SwingConstants.CENTER);
        imagenLabel.setPreferredSize(new Dimension(220, 160));
        mostrarImagen(imagenLabel, imagenData.getRuta(), 220, 160);
        String nombreArchivo = new File(imagenData.getRuta()).getName();
        JLabel nombreLabel = new JLabel(nombreArchivo, SwingConstants.CENTER);
        nombreLabel.setToolTipText(imagenData.getRuta());
        JLabel similitudLabel = new JLabel(String.format("Similitud: %.2f%%", resultado.getValor()), SwingConstants.CENTER);
        similitudLabel.setFont(similitudLabel.getFont().deriveFont(Font.BOLD));
        JPanel informacionTarjeta = new JPanel(new GridLayout(2, 1, 0, 3));
        informacionTarjeta.add(nombreLabel);
        informacionTarjeta.add(similitudLabel);
        tarjeta.add(imagenLabel, BorderLayout.CENTER);
        tarjeta.add(informacionTarjeta, BorderLayout.SOUTH);
        return tarjeta;
    }
}