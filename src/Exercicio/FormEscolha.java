package Exercicio;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormEscolha {
    private JButton MarcasButton;
    private JButton veiculosButton;
    private JButton perguntasFrequentesButton;
    private JPanel panelEscolha;

    public FormEscolha() {
        veiculosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FormVeiculos().setVisible(true);
            }
        });
        MarcasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FormCategorias().setVisible(true);
            }
        });
        perguntasFrequentesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FormFAQ().setVisible(true);
            }
        });
    }

    public void setVisible(boolean b)
    {
        JFrame frame=new JFrame("Escolha");
        frame.setContentPane(new FormEscolha().panelEscolha);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600,400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}


