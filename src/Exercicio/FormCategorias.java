package Exercicio;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class FormCategorias {
    private JButton adicionarMarcaButton;
    private JButton removerMarcaButton;
    private JTextField textFieldMarca;
    private JTextArea textAreaConsulta;
    private JPanel panelCategoria;
    private JButton consultarMarcasButton;


    Connection con;
    PreparedStatement pst;

    public static void setVisible(boolean b)
    {
        JFrame frame=new JFrame("Escolha");
        frame.setContentPane(new FormCategorias().panelCategoria);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600,500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

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

    public FormCategorias() {
        Connect();

        adicionarMarcaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String marca;
                marca = textFieldMarca.getText();
                textAreaConsulta.setText("");

                try
                {
                    pst = con.prepareStatement("insert into categorias(NomeCat)values(?)");
                    pst.setString(1, marca);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Marca adicionada!");
                    textFieldMarca.setText("");
                }
                catch (SQLException e1)
                {
                    JOptionPane.showMessageDialog(null, "Esta marca já existe!");
                    e1.printStackTrace();
                }
            }
        });

        removerMarcaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String marca;
                marca = textFieldMarca.getText();
                textAreaConsulta.setText("");
                try {
                    PreparedStatement ps = con.prepareStatement("SELECT NomeCat FROM categorias WHERE NomeCat = ?");
                    ps.setString (1, marca);
                    ResultSet rs = ps.executeQuery();
                    if(rs.next())
                    {
                        pst = con.prepareStatement("delete from categorias where NomeCat = ?");
                        pst.setString(1, marca);
                        pst.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Marca removida!");
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Esta marca não existe!");
                    }
                    textFieldMarca.setText("");
                }
                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });

        consultarMarcasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    pst = con.prepareStatement("select NomeCat from categorias");
                    ResultSet rs = pst.executeQuery();
                    textAreaConsulta.setText("");
                    while(rs.next()==true)
                    {
                        String name = rs.getString(1);
                        textAreaConsulta.append(name + "\n");
                    }
                }
                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });
    }
}
