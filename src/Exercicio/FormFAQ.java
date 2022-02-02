package Exercicio;

import javax.swing.*;

public class FormFAQ {
    private JTextPane rParaAdicionarAlterarTextPane;
    private JPanel PanelFAQ;

    public void setVisible(boolean b)
    {
        JFrame frame=new JFrame("Escolha");
        frame.setContentPane(new FormFAQ().PanelFAQ);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
