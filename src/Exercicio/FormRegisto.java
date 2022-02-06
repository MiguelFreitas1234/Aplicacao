package Exercicio;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FormRegisto {
    private JButton registarButton;
    private JPasswordField passwordField1;
    private JTextField textFieldNomeUtilizador;
    private JTextField textFieldTelefone;
    private JTextField textFieldNome;
    private JPanel panelRegisto;

    Connection con;
    PreparedStatement pst;

    public void Connect()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/bdlojamiguelfreitas", "root","1234");
            System.out.println("Success");
        }
        catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    public FormRegisto() {
        Connect();
        registarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome, telefone, nomeUtilizador, password;

                nome = textFieldNome.getText();
                telefone = textFieldTelefone.getText();
                nomeUtilizador = textFieldNomeUtilizador.getText();
                password = String.valueOf(passwordField1.getPassword());

                if(textFieldNome.getText().trim().isEmpty())
                {
                    JOptionPane.showMessageDialog(null, "Adicione um Nome!");
                }
                if(textFieldTelefone.getText().trim().isEmpty())
                {
                    JOptionPane.showMessageDialog(null, "Adicione um Número de Telefone!");

                }
                if(textFieldNomeUtilizador.getText().trim().isEmpty())
                {
                    JOptionPane.showMessageDialog(null, "Adicione um Nome de Utilizador!");

                }
                if(passwordField1.getPassword().length == 0)
                {
                    JOptionPane.showMessageDialog(null, "Adicione uma Password!");
                }
                if(textFieldNome.getText().trim().isEmpty() || textFieldTelefone.getText().trim().isEmpty() || textFieldNomeUtilizador.getText().trim().isEmpty() || passwordField1.getPassword().length == 0)
                {

                }
                else
                {
                    try
                    {
                        pst = con.prepareStatement("insert into empregados(Nome,Telefone,NomeUtilizador,PalavraPasse)values(?,?,?,?)");
                        pst.setString(1, nome);
                        pst.setString(2, telefone);
                        pst.setString(3, nomeUtilizador);
                        pst.setString(4, password);
                        pst.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Utilizador adicionado!");

                        textFieldNome.setText("");
                        textFieldTelefone.setText("");
                        textFieldNomeUtilizador.setText("");
                        passwordField1.setText("");

                    }
                    catch (SQLException e1)
                    {
                        e1.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Erro!");
                    }
                }
            }
        });
    }
    public static void setVisible(boolean b)
    {
        JFrame frame=new JFrame("Registo");
        frame.setContentPane(new FormRegisto().panelRegisto);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
