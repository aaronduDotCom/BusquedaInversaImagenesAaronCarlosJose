package Presentation.Busqueda;

import Model.Estructuras.ResultadoBusqueda;
import Model.Services.Busqueda.BuscadorInverso;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View extends JFrame implements PropertyChangeListener {

    private JPanel contentPanel;
    private JButton buscarButton;
    private JComboBox<String> searchMethodcomboBox;
    private JLabel imagePreview;
    private JLabel imageRoute;
    private JButton seleccionarImagenButton;
    private JComboBox<String> comboBoxOrdenamiento;
    private JComboBox<String> comboBoxBins;

    public View() {
        setTitle("Búsqueda Inversa de Imágenes");
        setContentPane(contentPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);

        imagePreview.setHorizontalAlignment(SwingConstants.CENTER);
        imagePreview.setVerticalAlignment(SwingConstants.CENTER);

        if (imageRoute != null) {
            imageRoute.setText("Sin ruta por el momento");
        }

        seleccionarImagenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos de imagen", "png", "jpg", "jpeg", "bmp"));
                fileChooser.setAcceptAllFileFilterUsed(false);
                int result = fileChooser.showOpenDialog(contentPanel);

                if (result == JFileChooser.APPROVE_OPTION) {
                    String path = fileChooser.getSelectedFile().getAbsolutePath();
                    try {
                        controller.selectImage(path);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(contentPanel, ex.getMessage(), "Error al seleccionar la imagen", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int bins = Integer.parseInt(comboBoxBins.getSelectedItem().toString());
                if (bins != 64) {
                    JOptionPane.showMessageDialog(contentPanel, "Actualmente solo se soporta la búsqueda con 64 bins.", "Información", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                try {
                    controller.search();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(contentPanel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

                }
            }
        });
    }

    Controller controller;
    Model model;

    public void setController(Controller controller) { this.controller = controller; }
    public void setModel(Model model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }
    public JPanel getContentPanel() {
        return contentPanel;
    }
    public JButton getBuscarButton() {
        return buscarButton;
    }
    public JComboBox<String> getSearchMethodcomboBox() {
        return searchMethodcomboBox;
    }
    public JComboBox<String> getComboBoxOrdenamiento() {return comboBoxOrdenamiento;}
    public JComboBox<String> getComboBoxBins() {return comboBoxBins;}
    public JLabel getImagePreview() {
        return imagePreview;
    }
    public JLabel getImageRoute() { return imageRoute; }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        switch (evt.getPropertyName()) {

            case Model.CURRENT:

                if (model.getCurrent() != null) {
                    String rutaImagen = model.getCurrent().getRuta();
                    imageRoute.setText(rutaImagen);
                    mostrarImagen(imagePreview, rutaImagen);
                } else {
                    imageRoute.setText("Sin ruta por el momento");
                    imagePreview.setIcon(null);
                    imagePreview.setText("Sin imagen seleccionada");
                }
                break;

            case Model.METHOD:
                searchMethodcomboBox.setSelectedItem(model.getMethod());
                break;
        }

        contentPanel.revalidate();
        contentPanel.repaint();
    }


    private void mostrarImagen(JLabel label, String ruta) {
        ImageIcon iconoOriginal = new ImageIcon(ruta);

        int anchoLabel = label.getWidth();
        int altoLabel = label.getHeight();

        if (anchoLabel <= 0 || altoLabel <= 0) {
            anchoLabel = 300;
            altoLabel = 250;
        }

        int anchoImagen = iconoOriginal.getIconWidth();
        int altoImagen = iconoOriginal.getIconHeight();

        if (anchoImagen <= 0 || altoImagen <= 0) {
            label.setIcon(null);
            label.setText("No se pudo cargar la imagen");
            return;
        }

        double escala = Math.min((double) anchoLabel / anchoImagen, (double) altoLabel / altoImagen);
        int nuevoAncho = (int) (anchoImagen * escala);
        int nuevoAlto = (int) (altoImagen * escala);
        Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(nuevoAncho, nuevoAlto, Image.SCALE_SMOOTH);
        label.setText("");
        label.setIcon(new ImageIcon(imagenEscalada));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
    }

}