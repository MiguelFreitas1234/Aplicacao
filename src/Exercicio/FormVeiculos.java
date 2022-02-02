package Exercicio;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;

public class FormVeiculos {
    private JTabbedPane TabbedPane1;
    private JTextField textFieldModelo;
    private JTextField textFieldPreço;
    private JTextField textFieldQuantidade;
    private JTextField textFieldMarca;
    private JButton guardarVeiculoButton;
    private JTextField textFieldAltPesquisa;
    private JButton altPesquisarButton;
    private JTextField textFieldAltModelo;
    private JTextField textFieldAltQuantidade;
    private JTextField textFieldAltMarca;
    private JButton altAlterarButton;
    private JFormattedTextField textFieldAltPreço;
    private JTextArea textAreaConsultarTudo;
    private JButton encontrarVeiculoButton;
    private JTextField textFieldEliModelo;
    private JTextField textFieldEliPreço;
    private JTextField textFieldEliQuantidade;
    private JButton dadosParaApagarVeiculoButton;
    private JTextField textFieldEliMarca;
    private JTextField textFieldNomeJogadorAApagar;
    private JTextField textFieldConsultarVeiculo;
    private JButton consultarVeiculoButton;
    private JTextArea textAreaConsultarMarca;
    private JPanel PanelVeiculos;
    private JButton consultarTodosOsModelosButton;
    private JButton procurarButton;
    private JLabel labelImagem;
    private JLabel altLabelImagem;
    private JLabel eliLabelImagem;
    private JButton buttonProcurar;
    private JButton consultarButton;
    private JTextField textFieldAltIDMarca;
    private JTextField textFieldAltIDQuantidade;
    private JTextField textFieldAltIDModelo;
    private JTextField textFieldAltIDPesquisa;
    private JTextField textFieldAltIDPreço;
    private JLabel altIdLabelImagem;

    String path=null;
    Connection con;
    PreparedStatement pst;
    private ResultSet rs;
    private byte[] userImage;

    public void setVisible(boolean b)
    {
        JFrame frame=new JFrame("Escolha");
        frame.setContentPane(new FormVeiculos().PanelVeiculos);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(650,700);
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

    public FormVeiculos() {
        Connect();

        guardarVeiculoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String modelo, preco, quantidade, marca;

                modelo = textFieldModelo.getText();
                preco = textFieldPreço.getText();
                quantidade = textFieldQuantidade.getText();
                marca = textFieldMarca.getText();


                if(textFieldModelo.getText().trim().isEmpty())
                {
                    JOptionPane.showMessageDialog(null, "Adicione um Nome!");
                }
                if(textFieldPreço.getText().trim().isEmpty())
                {
                    JOptionPane.showMessageDialog(null, "Adicione um Preço!");

                }
                if(textFieldQuantidade.getText().trim().isEmpty())
                {
                    JOptionPane.showMessageDialog(null, "Adicione uma Quantidade!");

                }
                if(textFieldMarca.getText().trim().isEmpty())
                {
                    JOptionPane.showMessageDialog(null, "Adicione uma Marca!");

                }
                if(labelImagem.getIcon() == null)
                {
                    JOptionPane.showMessageDialog(null, "Adicione uma Imagem!");

                }

                if(textFieldModelo.getText().trim().isEmpty() || textFieldPreço.getText().trim().isEmpty() || textFieldQuantidade.getText().trim().isEmpty() || textFieldMarca.getText().trim().isEmpty() || labelImagem.getIcon() == null)
                {

                }
                else
                {
                    try
                    {
                        pst = con.prepareStatement("insert into produtos(NomeProd,Preço,Quantidade,categorias_NomeCat,Imagem)values(?,?,?,?,?)");
                        pst.setString(1, modelo);
                        pst.setString(2, preco);
                        pst.setString(3, quantidade);
                        pst.setString(4, marca);
                        pst.setBytes(5, userImage);
                        pst.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Veiculo adicionado!");
                        textFieldModelo.setText("");
                        textFieldPreço.setText("");
                        textFieldQuantidade.setText("");
                        textFieldMarca.setText("");
                        labelImagem.setIcon(null);

                    }
                    catch (SQLException e1)
                    {
                        e1.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Esta marca não existe!");
                    }
                }
            }
        });

        altPesquisarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {

                    String id = textFieldAltPesquisa.getText();
                    pst = con.prepareStatement("select NomeProd,Preço,Quantidade,categorias_NomeCat,Imagem from produtos where id = ?");
                    pst.setString(1, id);
                    ResultSet rs = pst.executeQuery();

                    if(rs.next()==true)
                    {
                        String modelo = rs.getString(1);
                        String preço = rs.getString(2);
                        String quantidade = rs.getString(3);
                        String marca = rs.getString(4);
                        Blob blob = rs.getBlob(5);

                        byte[] imageBytes = blob.getBytes(1, (int) blob.length());
                        ImageIcon imgIcon = new ImageIcon(new ImageIcon(imageBytes).getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT));
                        altLabelImagem.setIcon(imgIcon);

                        textFieldAltModelo.setText(modelo);
                        textFieldAltPreço.setText(preço);
                        textFieldAltQuantidade.setText(quantidade);
                        textFieldAltMarca.setText(marca);

                    }
                    else
                    {
                        textFieldAltModelo.setText("");
                        textFieldAltPreço.setText("");
                        textFieldAltQuantidade.setText("");
                        textFieldAltMarca.setText("");
                        altLabelImagem.setIcon(null);
                        JOptionPane.showMessageDialog(null,"ID inválido");

                    }
                }
                catch (SQLException ex)
                {
                    ex.printStackTrace();
                }
            }
        });
        altAlterarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String modelo,preco,quantidade, marca, id;

                modelo = textFieldAltModelo.getText();
                preco = textFieldAltPreço.getText();
                quantidade = textFieldAltQuantidade.getText();
                marca = textFieldAltMarca.getText();
                id = textFieldAltPesquisa.getText();

                if(textFieldAltModelo.getText().trim().isEmpty())
                {
                    JOptionPane.showMessageDialog(null, "Adicione um Nome!");
                }
                if(textFieldAltPreço.getText().trim().isEmpty())
                {
                    JOptionPane.showMessageDialog(null, "Adicione um Preço!");

                }
                if(textFieldAltQuantidade.getText().trim().isEmpty())
                {
                    JOptionPane.showMessageDialog(null, "Adicione uma Quantidade!");

                }
                if(textFieldAltMarca.getText().trim().trim().isEmpty())
                {
                    JOptionPane.showMessageDialog(null, "Adicione uma Marca!");

                }
                if(altLabelImagem.getIcon() == null)
                {
                    JOptionPane.showMessageDialog(null, "Adicione uma Imagem!");

                }

                if(textFieldAltModelo.getText().trim().isEmpty() || textFieldAltPreço.getText().trim().isEmpty() || textFieldAltQuantidade.getText().trim().isEmpty() || textFieldAltMarca.getText().trim().isEmpty() || altLabelImagem.getIcon() == null)
                {

                }
                else
                {
                    try
                    {

                        pst = con.prepareStatement("update produtos set NomeProd = ?,Preço = ?,Quantidade = ?,categorias_NomeCat = ?, Imagem = ? where id = ?");
                        pst.setString(1, modelo);
                        pst.setString(2, preco);
                        pst.setString(3, quantidade);
                        pst.setString(4, marca);
                        pst.setBytes(5, userImage);
                        pst.setString(6,id);

                        pst.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Veiculo atualizado!");

                        textFieldAltModelo.setText("");
                        textFieldAltPreço.setText("");
                        textFieldAltQuantidade.setText("");
                        textFieldAltPesquisa.requestFocus();
                        textFieldAltMarca.setText("");
                        altLabelImagem.setIcon(null);
                    }

                    catch (SQLException e1)
                    {
                        JOptionPane.showMessageDialog(null, "Erro! Insira uma marca existente/Insira um número");
                        e1.printStackTrace();
                    }
                }
            }
        });

        consultarTodosOsModelosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    pst = con.prepareStatement("select id, NomeProd, Preço, Quantidade, categorias_NomeCat, Imagem from produtos");
                    ResultSet rs = pst.executeQuery();
                    textAreaConsultarTudo.setText("");
                    while(rs.next()==true)
                    {
                        String modelo = rs.getString(1) + ", " + rs.getString(2)  + ", " + rs.getString(3) + ", " + rs.getString(4) + ", " + rs.getString(5) + ", " + rs.getBlob(6);
                        textAreaConsultarTudo.append(modelo + "\n");
                    }
                }
                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });
        consultarVeiculoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String id;
                    id = textFieldConsultarVeiculo.getText();

                    pst = con.prepareStatement("select id, NomeProd, Preço, Quantidade, categorias_NomeCat, Imagem from produtos WHERE categorias_NomeCat = ?");
                    pst.setString(1, id);
                    ResultSet rs = pst.executeQuery();
                    textAreaConsultarMarca.setText("");

                    while(rs.next()==true)
                    {
                        String modelo = rs.getString(1) + ", " + rs.getString(2)  + ", " + rs.getString(3) + ", " + rs.getString(4) + ", " + rs.getString(5) + ", " + rs.getBlob(6);
                        textAreaConsultarMarca.append(modelo + "\n");
                    }
                }
                catch (SQLException e1)
                {
                    JOptionPane.showMessageDialog(null, "Erro! Insira uma marca existente");
                    e1.printStackTrace();
                }
            }
        });
        encontrarVeiculoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                    String id = textFieldNomeJogadorAApagar.getText();
                    pst = con.prepareStatement("select NomeProd,Preço,Quantidade,categorias_NomeCat, Imagem from produtos where id = ?");
                    pst.setString(1, id);
                    ResultSet rs = pst.executeQuery();

                    if(rs.next()==true)
                    {
                        String modelo = rs.getString(1);
                        String preço = rs.getString(2);
                        String quantidade = rs.getString(3);
                        String marca = rs.getString(4);
                        Blob blob = rs.getBlob(5);

                        byte[] imageBytes = blob.getBytes(1, (int) blob.length());
                        ImageIcon imgIcon = new ImageIcon(new ImageIcon(imageBytes).getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT));
                        eliLabelImagem.setIcon(imgIcon);


                        textFieldEliModelo.setText(modelo);
                        textFieldEliPreço.setText(preço);
                        textFieldEliQuantidade.setText(quantidade);
                        textFieldEliMarca.setText(marca);
                    }
                    else
                    {
                        textFieldEliModelo.setText("");
                        textFieldEliPreço.setText("");
                        textFieldEliQuantidade.setText("");
                        textFieldEliMarca.setText("");

                        JOptionPane.showMessageDialog(null,"ID inválido");
                    }
                }
                catch (SQLException ex)
                {
                    ex.printStackTrace();
                }
            }
        });

        dadosParaApagarVeiculoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id;


                id = textFieldNomeJogadorAApagar.getText();


                try {
                    pst = con.prepareStatement("delete from produtos where id = ?");
                    pst.setString(1, id);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Veiculo deletado!");

                    textFieldEliModelo.setText("");
                    textFieldEliPreço.setText("");
                    textFieldEliQuantidade.setText("");
                    textFieldEliMarca.setText("");
                    eliLabelImagem.setIcon(null);
                }
                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });
        procurarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser imgChoose=new JFileChooser();
                imgChoose.showOpenDialog(null);
                File img=imgChoose.getSelectedFile();

                path=img.getAbsolutePath();
                BufferedImage image;
                try
                {
                    image= ImageIO.read(imgChoose.getSelectedFile());
                    ImageIcon imgIcon= new ImageIcon(new ImageIcon(image).getImage().getScaledInstance(250,250, Image.SCALE_DEFAULT));
                    labelImagem.setIcon(imgIcon);

                    File imgg=new File(path);
                    FileInputStream fs=new FileInputStream(imgg);
                    ByteArrayOutputStream bos=new ByteArrayOutputStream();
                    byte[] buff=new byte[1024];
                    int nBytes_read=0;
                    while((nBytes_read=fs.read(buff)) !=-1)
                    {
                        bos.write(buff, 0, nBytes_read);
                    }
                    userImage=bos.toByteArray();
                }
                catch (FileNotFoundException e1)
                {
                    e1.printStackTrace();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
        });
        buttonProcurar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser imgChoose=new JFileChooser();
                imgChoose.showOpenDialog(null);
                File img=imgChoose.getSelectedFile();

                path=img.getAbsolutePath();
                BufferedImage image;
                try
                {
                    image= ImageIO.read(imgChoose.getSelectedFile());
                    ImageIcon imgIcon= new ImageIcon(new ImageIcon(image).getImage().getScaledInstance(250,250, Image.SCALE_DEFAULT));
                    altLabelImagem.setIcon(imgIcon);

                    File imgg=new File(path);
                    FileInputStream fs=new FileInputStream(imgg);
                    ByteArrayOutputStream bos=new ByteArrayOutputStream();
                    byte[] buff=new byte[1024];
                    int nBytes_read=0;
                    while((nBytes_read=fs.read(buff)) !=-1)
                    {
                        bos.write(buff, 0, nBytes_read);
                    }
                    userImage=bos.toByteArray();
                }
                catch (FileNotFoundException e1)
                {
                    e1.printStackTrace();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
        });
        consultarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                    textFieldAltIDModelo.setText("");
                    textFieldAltIDPreço.setText("");
                    textFieldAltIDQuantidade.setText("");
                    textFieldAltIDMarca.setText("");
                    altIdLabelImagem.setIcon(null);

                    String id = textFieldAltIDPesquisa.getText();
                    pst = con.prepareStatement("select NomeProd,Preço,Quantidade,categorias_NomeCat,Imagem from produtos where id = ?");
                    pst.setString(1, id);
                    ResultSet rs = pst.executeQuery();

                    if(rs.next()==true)
                    {
                        String modelo = rs.getString(1);
                        String preço = rs.getString(2);
                        String quantidade = rs.getString(3);
                        String marca = rs.getString(4);
                        Blob blob = rs.getBlob(5);

                        byte[] imageBytes = blob.getBytes(1, (int) blob.length());
                        ImageIcon imgIcon = new ImageIcon(new ImageIcon(imageBytes).getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT));
                        altIdLabelImagem.setIcon(imgIcon);

                        textFieldAltIDModelo.setText(modelo);
                        textFieldAltIDPreço.setText(preço);
                        textFieldAltIDQuantidade.setText(quantidade);
                        textFieldAltIDMarca.setText(marca);

                    }
                    else
                    {
                        textFieldAltIDModelo.setText("");
                        textFieldAltIDPreço.setText("");
                        textFieldAltIDQuantidade.setText("");
                        textFieldAltIDMarca.setText("");
                        altIdLabelImagem.setIcon(null);
                        JOptionPane.showMessageDialog(null,"ID inválido");

                    }
                }
                catch (SQLException ex)
                {
                    ex.printStackTrace();
                }
            }
        });
    }
}
