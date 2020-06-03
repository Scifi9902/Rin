package me.scifi.rin.gui;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import me.scifi.rin.Rin;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.net.URLConnection;

@Getter @Setter
public final class RinGui extends JFrame implements ActionListener {

    private final JLabel jLabel;

    private final JLabel jBackground;

    private final JButton jButton;

    private final JTextArea jTextArea;

    private final JPanel jPanel;

    @SneakyThrows
    public RinGui(final String name) {
        super(name);

       final URL url = new URL(Rin.getLink());
       final URLConnection urlConnection = url.openConnection();

       urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

       final ImageIcon imageIcon = new ImageIcon(ImageIO.read(urlConnection.getInputStream()));

        this.jPanel = new JPanel();

        this.jLabel = new JLabel("Enter the amount of images to download.");
        this.jButton = new JButton("Click to Download.");
        this.jBackground = new JLabel(imageIcon);
        this.jBackground.setPreferredSize(new Dimension(1000,1000));
        this.jTextArea = new JTextArea();
        this.jTextArea.setPreferredSize(new Dimension(100,15));
        this.jPanel.add(this.jLabel);
        this.jPanel.add(this.jTextArea);
        this.jPanel.add(this.jButton);
        this.jPanel.add(this.jBackground);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.jButton.addActionListener(this);
        this.setSize(new Dimension(1000,1000));
        this.setContentPane(this.jPanel);

    }

    @Override
    public void actionPerformed(final ActionEvent e) {

        if (this.jTextArea.getText().isEmpty()) {
            JOptionPane.showConfirmDialog(null, "Invalid integer amount to download.");
            return;
        }

        try {
            Rin.setAmount(Integer.parseInt(this.jTextArea.getText()));
            Rin.writeImages();
            JOptionPane.showConfirmDialog(null, "Download started.");
        } catch (NumberFormatException exception) {
            JOptionPane.showConfirmDialog(null, "Invalid integer amount to download.");
        }
    }
}
