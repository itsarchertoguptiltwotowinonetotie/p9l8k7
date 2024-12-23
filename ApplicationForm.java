import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.*;
public class ApplicationForm extends JFrame {
        public ApplicationForm() {
                setSize(300, 500);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setLayout(new GridLayout(2,1));
                /*JScrollBar sb=new JScrollBar();
                sb.setBounds(280,0,20,500);
                sb.setOrientation(Scrollbar.VERTICAL);
                add(sb);*/
                JPanel p = new JPanel();
                p.setLayout(null);
                p.setBackground(Color.magenta);
                JPanel p1 = new JPanel();
                p1.setLayout(null);
                p1.setBackground(Color.pink);
                JLabel head = new JLabel("Student Registration Form");
                head.setForeground(Color.RED);
                head.setFont(new Font("Times New Roman", Font.BOLD, 20));
                head.setBounds(650, 50, 300, 30);
                p.add(head);
                JLabel name = new JLabel("Name");
                name.setBounds(500, 150, 100, 30);
                p.add(name);
                JTextField name_Field = new JTextField();
                p.add(name_Field);
                name_Field.setFont(new Font("Times New Roman", Font.PLAIN, 15));
                name_Field.setBounds(750, 150, 100, 30);
                JLabel admn_no = new JLabel("Admission no");
                admn_no.setBounds(500, 200, 100, 30);
                p.add(admn_no);
                JTextField admn_field = new JTextField();
                admn_field.setFont(new Font("Times New Roman", Font.PLAIN, 15));
                admn_field.setBounds(750, 200, 100, 30);
                p.add(admn_field);
                JLabel roll_no = new JLabel("Roll no");
                roll_no.setBounds(500, 250, 100, 30);
                p.add(roll_no);
                JTextField roll_field = new JTextField();
                roll_field.setFont(new Font("Times New Roman", Font.PLAIN, 15));
                roll_field.setBounds(750, 250, 100, 30);
                p.add(roll_field);
                JLabel degree = new JLabel("Degree");
                degree.setBounds(500, 300, 100, 30);
                p.add(degree);
                JComboBox cb = new JComboBox();
                cb.addItem("B.E");
                cb.addItem("B.Tech");
                cb.addItem("B.Sc");
                cb.setBounds(750, 300, 100, 30);
                p.add(cb);
                JLabel course = new JLabel("course");
                course.setBounds(500, 350, 100, 30);
                p.add(course);
                JComboBox cb2 = new JComboBox();
                cb2.addItem("CSE");
                cb2.addItem("IT");
                cb2.addItem("AIDS");
                cb2.setBounds(750, 350, 100, 30);
                p.add(cb2);
                JLabel gender = new JLabel("Gender");
                gender.setBounds(500, 0, 100, 30);
                p1.add(gender);
                ButtonGroup bg = new ButtonGroup();
                JRadioButton rb = new JRadioButton("Male");
                rb.setBounds(750, 0, 100, 30);
                bg.add(rb);
                p1.add(rb);
                JRadioButton rb2 = new JRadioButton("Female");
                rb2.setBounds(850, 0, 100, 30);
                bg.add(rb2);
                p1.add(rb2);
                ButtonGroup bg2 = new ButtonGroup();
                JLabel Mstatus = new JLabel("Martial Status");
                Mstatus.setBounds(500, 50, 100, 30);
                p1.add(Mstatus);
                JRadioButton rb3 = new JRadioButton("Married");
                rb3.setBounds(750, 50, 100, 30);
                bg2.add(rb3);
                p1.add(rb3);
                JRadioButton rb4 = new JRadioButton("UnMarried");
                rb4.setBounds(850, 50, 100, 30);
                bg2.add(rb4);
                p1.add(rb4);
                JLabel mail = new JLabel("Mail id");
                mail.setBounds(500, 100, 100, 30);
                p1.add(mail);
                JTextField mail_field = new JTextField();
                mail_field.setFont(new Font("Times New Roman", Font.PLAIN, 15));
                mail_field.setBounds(750, 100, 250, 30);
                p1.add(mail_field);
                JLabel plang = new JLabel("Programing Languages");
                plang.setBounds(500, 150, 150, 30);
                p1.add(plang);
                JCheckBox cbox1 = new JCheckBox("C");
                cbox1.setBounds(750, 150, 100, 30);
                p1.add(cbox1);
                JCheckBox cbox2 = new JCheckBox("Python");
                cbox2.setBounds(750, 175, 100, 30);
                p1.add(cbox2);
                JCheckBox cbox3 = new JCheckBox("Java");
                cbox3.setBounds(750, 200, 100, 30);
                p1.add(cbox3);
                JLabel self = new JLabel("Picture yourself");
                self.setBounds(500, 250, 100, 30);
                p1.add(self);
                 JTextArea area = new JTextArea();  
 
 area.setVisible(true);  

 
 JScrollPane scroller = new JScrollPane(area);  
 JScrollBar bar = new JScrollBar();  
 scroller.setBounds(750, 250, 300, 100); 
 scroller.add(bar);  
 p1.add(scroller);  
 setVisible(true);

                JButton b = new JButton("Register");
                b.setBounds(625, 375, 150, 30);
                p1.add(b);
                add(p);
                add(p1);
                setVisible(true);
        }
        public static void main(String[] args) {
                new ApplicationForm();
        }
}
