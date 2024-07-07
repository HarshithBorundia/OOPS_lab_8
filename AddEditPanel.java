import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddEditPanel extends JFrame {
    JTextField nameField;
    JTextField idField;
    JComboBox<Major> majorField;
    JTextField sfField;
    JTextField gradField;
    JTextField stipendField;

    void dismissPanel() {
        this.dispose();
    }

    void addNewStudent(DefaultTableModel model, boolean isFDStudent) throws InvalidIDException {
        if (idField.getText().length() < 6) {
            throw new InvalidIDException(this);
        } else {
            BITSStudent student;
            if (isFDStudent) {
                student = new FDStudent(nameField.getText(), idField.getText(),
                        (Major) majorField.getSelectedItem(), sfField.getText(), stipendField.getText());
            } else {
                student = new HDStudent(nameField.getText(), idField.getText(),
                        (Major) majorField.getSelectedItem(), sfField.getText() + "," + gradField.getText(), stipendField.getText());
            }

            Global.myMap.put(idField.getText(), student);
            updateTableModel(model);
        }
    }

    void updateTableModel(DefaultTableModel model) {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        for (String id : Global.myMap.keySet()) {
            model.addRow(new Object[]{id, Global.myMap.get(id).name});
        }
    }

    public AddEditPanel(boolean addStudent, String key, DefaultTableModel model, int row) {
        this.setTitle(addStudent ? "Add" : "Edit");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(null);

        JLabel nameLabel = new JLabel("Name");
        JLabel idLabel = new JLabel("ID");
        JLabel majorLabel = new JLabel("Major");
        JLabel sfLabel = new JLabel("12th Marks");
        JLabel gradLabel = new JLabel("Graduation Marks");
        JLabel stipendLabel = new JLabel("Stipend");

        nameField = new JTextField();
        idField = new JTextField();
        majorField = new JComboBox<>(Major.values());
        sfField = new JTextField();
        gradField = new JTextField();
        stipendField = new JTextField();

        nameLabel.setBounds(20, 20, 200, 30);
        nameField.setBounds(230, 20, 200, 30);
        this.add(nameField);
        this.add(nameLabel);

        idLabel.setBounds(20, 60, 200, 30);
        idField.setBounds(230, 60, 200, 30);
        this.add(idField);
        this.add(idLabel);

        majorLabel.setBounds(20, 100, 200, 30);
        majorField.setBounds(230, 100, 200, 30);
        this.add(majorField);
        this.add(majorLabel);

        sfLabel.setBounds(20, 140, 200, 30);
        sfField.setBounds(230, 140, 200, 30);
        this.add(sfField);
        this.add(sfLabel);

        gradLabel.setBounds(20, 180, 200, 30);
        gradField.setBounds(230, 180, 200, 30);
        this.add(gradField);
        this.add(gradLabel);

        stipendLabel.setBounds(20, 220, 200, 30);
        stipendField.setBounds(230, 220, 200, 30);
        this.add(stipendField);
        this.add(stipendLabel);

        JButton addEditButton = new JButton();
        if (addStudent) {
            addEditButton.setText("Add Student");
            addEditButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        boolean isFDStudent = !sfField.getText().contains(","); // Default to FDStudent if no comma
                        addNewStudent(model, isFDStudent);
                        dismissPanel();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

        } else {
            addEditButton.setText("Edit Student");
            BITSStudent student = Global.myMap.get(key);
            nameField.setText(student.name);
            idField.setText(student.ID);
            idField.setEditable(false);
            majorField.setSelectedItem(student.major);
            stipendField.setText(""+student.stipend);
            if (student instanceof HDStudent) {
                String qualifications = ((HDStudent) student).schoolFinalPercentage + "," + ((HDStudent) student).graduationCGPA;
                String[] qualificationsArr = qualifications.split(",");
                sfField.setText(qualificationsArr[0]);
                if (qualificationsArr.length > 1) {
                    gradField.setText(qualificationsArr[1]);
                }
            } else if (student instanceof FDStudent) {
                sfField.setText(String.valueOf(((FDStudent) student).schoolFinalPercentage));
                gradField.setText(""); // Clear gradField for FDStudent
            }

            addEditButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    BITSStudent updatedStudent;
                    boolean isFDStudent = !sfField.getText().contains(","); // Default to FDStudent if no comma
                    if (isFDStudent) {
                        updatedStudent = new FDStudent(nameField.getText(), idField.getText(),
                                (Major) majorField.getSelectedItem(), sfField.getText(), stipendField.getText());
                    } else {
                        updatedStudent = new HDStudent(nameField.getText(), idField.getText(),
                                (Major) majorField.getSelectedItem(), sfField.getText() + "," + gradField.getText(), stipendField.getText());
                    }
                    Global.myMap.put(idField.getText(), updatedStudent);
                    updateTableModel(model);
                    dismissPanel();
                }
            });
        }
        addEditButton.setBounds(20, 260, 410, 30);
        this.add(addEditButton);

        this.setBounds(40, 40, 460, 400);
        this.setVisible(true);
    }
}
