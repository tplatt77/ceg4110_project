
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import Biometrics.CFingerPrint;
import Biometrics.CFingerPrintGraphics;
import java.awt.image.*;
import javax.imageio.*;
import java.io.File;
import java.lang.Exception;
import java.awt.Color;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CEntityForm extends JFrame {

    class BJPanel extends JPanel {

        public BufferedImage bi;

        public BJPanel() {
            this.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent m) {
                    JOptionPane.showMessageDialog(null, "(" + Integer.toString(m.getPoint().x) + ";" + Integer.toString(m.getPoint().y) + ")", "Point", JOptionPane.PLAIN_MESSAGE);
                }
            });
        }

        public BJPanel(BufferedImage bi) {
            this.bi = bi;
            setPreferredSize(new Dimension(bi.getWidth(), bi.getHeight()));
        }

        public void setBufferedImage(BufferedImage bi) {
            this.bi = bi;
            setPreferredSize(new Dimension(bi.getWidth(), bi.getHeight()));
            this.repaint();
        }

        public void paintComponent(Graphics g) {
            g.drawImage(bi, 0, 0, this);
        }
    }

    String directory = "C:\\Users\\Gator King\\Desktop\\Spring 2016\\Software Engineering\\PROJECT\\JavaBiometrics\\";

    private JToolBar jtool = new JToolBar();
    private JPanel jimage = new JPanel();
    private JButton jButtonStep1 = new JButton("Calculation");
    private JButton jButtonStep2 = new JButton("Image Processing");
    private JButton jButtonStep3 = new JButton("1 to 1 Match");
    private JButton jButtonStep4 = new JButton("1 to m Match");
    private JTextField jTextField1 = new JTextField();
    private JTextField jTextField2 = new JTextField();
    private Scanner in = new Scanner(System.in);

    //uses our finger print libery
    private CFingerPrint m_finger1 = new CFingerPrint();
    private CFingerPrint m_finger2 = new CFingerPrint();
    private CFingerPrintGraphics m_fingergfx = new CFingerPrintGraphics();
    private BJPanel m_panel1 = new BJPanel();
    private BJPanel m_panel2 = new BJPanel();
    private BufferedImage m_bimage1 = new BufferedImage(m_finger1.FP_IMAGE_WIDTH, m_finger1.FP_IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
    private BufferedImage m_bimage2 = new BufferedImage(m_finger1.FP_IMAGE_WIDTH, m_finger1.FP_IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
    private double finger1[] = new double[m_finger1.FP_TEMPLATE_MAX_SIZE];
    private double finger2[] = new double[m_finger2.FP_TEMPLATE_MAX_SIZE];
    ArrayList<Doctor> imagePaths = new ArrayList<>();
    ArrayList<Double> matches = new ArrayList<>();
    static double bestMatch = 0.0;

    public CEntityForm() {
        jButtonStep1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    jButtonStep1_actionPerformed(e);
                } catch (IOException ex) {
                    Logger.getLogger(CEntityForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        jButtonStep2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButtonStep2_actionPerformed(e);
            }
        });
        jButtonStep3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButtonStep3_actionPerformed(e);
            }
        });
        jButtonStep4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButtonStep4_actionPerformed(e);
            }
        });

        jtool.setLayout(new GridLayout(4, 1));
        jtool.add(jButtonStep1);
        jtool.add(jButtonStep2);
        jtool.add(jButtonStep3);
        jtool.add(jButtonStep4);
        jtool.add(jTextField1);
        jtool.add(jTextField2);

        try {
            //picture1C:\Users\Admin\Desktop\inclass\JavaBiometrics\src\Finger1Orig.jpg
            //Set picture new
            System.out.println(new java.io.File("").getAbsolutePath());
//        System.out.println("Test")Test;
            m_bimage1 = ImageIO.read(new File(directory + "Finger1Var.bmp"));
            m_panel1.setBufferedImage(m_bimage1);
            //Send image for skeletinization
            m_finger1.setFingerPrintImage(m_bimage1);
            finger1 = m_finger1.getFingerPrintTemplate();
            //See what skeletinized image looks like
            m_bimage1 = m_finger1.getFingerPrintImageDetail();
            m_panel1.setBufferedImage(m_bimage1);
            jTextField1.setText(m_finger1.ConvertFingerPrintTemplateDoubleToString(finger1));

            //picture2
            //Set picture new
            //  m_bimage2=ImageIO.read(new File(new java.io.File("").getAbsolutePath()+"\\ProcessedSample2.bmp")) ;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.PLAIN_MESSAGE);
        }
        this.getContentPane().setLayout(new GridLayout(2, 2));
        this.getContentPane().add(m_panel1);
        this.getContentPane().add(m_panel2);
        this.getContentPane().add(jtool);

        this.setTitle("Entity");
        this.setSize(new Dimension(900, 700));
    }

    private void jButtonStep1_actionPerformed(ActionEvent e) throws IOException {
        Doctor newDoctor;
        //ArrayList<Doctor> imagePaths = new ArrayList();
        for (int i = 0; i < 10; i++) {
            if (i < 5) {
                newDoctor = new Doctor(directory + "ProcessedSample" + (i + 1) + ".bmp", imagePaths.size());
                imagePaths.add(newDoctor);
                //System.out.println(imagePaths.get(i).getFilePath());
            } else {
                newDoctor = new Doctor(directory + "Finger1VarHQ.bmp", imagePaths.size());
                imagePaths.add(newDoctor);
                //imagePaths.add(new Doctor(directory + "ProcessedSample"Finger1VarHQ.bmp"" + (i+1) + ".bmp", imagePaths.size()));
                //System.out.println(imagePaths.get(i).getFilePath());
            }
        }
//        for (int j=0; j < imagePaths.size(); j++)
//        {
//            System.out.println("Doctor " + j + ": " + imagePaths.get(j).getFilePath());
//        }

        //for(int i = 0; i <imagePaths.size()-1 ;i++){
        //System.out.println("Enroll fingerprint or press q to quit");
        //str = in.next();
        //System.out.println("str =" + str );
        // if(!"q".equals(str)){
        //m_bimage2[i]=ImageIO.read(new File(imagePaths.get(i).getFilePath())) ;
        // m_panel2.setBufferedImage(m_bimage2[i]);
        //Send image for skeletinization
        // m_finger2.setFingerPrintImage(m_bimage2[i]) ;
        // finger2=m_finger2.getFingerPrintTemplate();
        //See what skeletinized image looks like
        // }  else{
//          System.out.println("Quitting");
//          break;
//      }  
        // }
        //m_finger.ChaneLinkAlgorithm() ;
        //m_panel1.setBufferedImage(m_finger.getFingerPrintImage());
    }

    private void jButtonStep2_actionPerformed(ActionEvent e) {
        try {
            // m_bimage2=ImageIO.read(new File("/home/scott/JavaBiometrics/FingerPrintPic/Sample2.bmp")) ;
            //show original image
            //m_panel1.setBufferedImage(m_bimage2);

            // m_bimage2 = m_fingergfx.getGreyFingerPrintImage(m_bimage2); 
            //m_bimage2 = m_fingergfx.BinerizeImage(m_bimage2, 250,180 ); 
            //m_panel2.setBufferedImage(m_bimage2);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error Message", JOptionPane.PLAIN_MESSAGE);
        }
    }

    private void jButtonStep3_actionPerformed(ActionEvent e) {
        //match one print
        try {
            //   for(int i = 0;i < m_bimage2.length;i++){
            //See what skeletinized image looks like
            //m_bimage2[0] = m_finger2.getFingerPrintImageDetail();
            //m_panel2.setBufferedImage(m_bimage2[0]);
            //jTextField2.setText(m_finger2.ConvertFingerPrintTemplateDoubleToString(finger2));
            for (int i = 0; i < imagePaths.size() - 1; i++) {

                //System.out.println("Enroll fingerprint or press q to quit");
                //str = in.next();
                //System.out.println("str =" + str );
                // if(!"q".equals(str)){
                m_bimage2 = ImageIO.read(new File(imagePaths.get(i).getFilePath()));
                m_panel2.setBufferedImage(m_bimage2);
                //Send image for skeletinization
                m_finger2.setFingerPrintImage(m_bimage2);
                finger2 = m_finger2.getFingerPrintTemplate();
                //See what skeletinized image looks like
                double match = m_finger1.Match(finger1, finger2, 65, false);
                matches.add(match);
                JOptionPane.showMessageDialog(null, Double.toString(match), "Match %", JOptionPane.PLAIN_MESSAGE);
                // }  else{
//          System.out.println("Quitting");
//          break;
//      }  
            }
            
            bestMatch = getBestMatch();
            // JOptionPane.showMessageDialog (null,Double.toString(m_finger1.Match(finger1 , finger2,65,false)),"Match %",JOptionPane.PLAIN_MESSAGE);
        }//}
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error Message", JOptionPane.PLAIN_MESSAGE);
        }
    }

    private void jButtonStep4_actionPerformed(ActionEvent e) {
        //match many finger prints
        //used to test matching speed
        //wors at about 1 match every 0,01 seconds needs to become a lot faster
        //the propriety software dose 1 match every 0,0001 a seconds
        try {
            long res = System.currentTimeMillis();
            for (int i = 0; i <= 500; i++) {
                m_finger1.Match(finger1, finger2, 55, true);
                if (i == 500) {
                    res = (System.currentTimeMillis() - res) / 1000;
                    JOptionPane.showMessageDialog(null, Long.toString(res), "Time to do 500 matches", JOptionPane.PLAIN_MESSAGE);
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error Message", javax.swing.JOptionPane.PLAIN_MESSAGE);
        }
    }

    @Override
    public void setDefaultCloseOperation(int operation) {
        System.out.println("Best match: " + bestMatch);

        super.setDefaultCloseOperation(operation); //To change body of generated methods, choose Tools | Templates.
    }

    public double getBestMatch() {
        
        for (int i = 0; i < matches.size(); i++) {
            double thisMatch = matches.get(i);
            if (thisMatch > bestMatch) {
                bestMatch = thisMatch;
            }
        }
        return bestMatch;
    }

}//End Class entity
