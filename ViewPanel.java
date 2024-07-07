import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.GridLayout;

public class ViewPanel extends JFrame {

    public ViewPanel(BITSStudent student) {
        this.setTitle("View Student Details");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(300, 200);
        this.setLayout(new GridLayout(0, 2));

        // Create labels for each student detail
        JLabel nameLabel = new JLabel("Name:");
        JLabel nameValue = new JLabel(student.name);

        JLabel idLabel = new JLabel("ID:");
        JLabel idValue = new JLabel(student.ID);

        JLabel majorLabel = new JLabel("Major:");
        JLabel majorValue = new JLabel(student.major.toString());

        JLabel sfLabel = new JLabel("12th Marks:");
        JLabel sfValue = new JLabel(String.valueOf(student.schoolFinalPercentage));

        JLabel stipendLabel = new JLabel("Stipend:");
        JLabel stipendValue = new JLabel(String.valueOf(student.stipend));

        // Add labels to the frame
        this.add(nameLabel);
        this.add(nameValue);

        this.add(idLabel);
        this.add(idValue);

        this.add(majorLabel);
        this.add(majorValue);

        this.add(sfLabel);
        this.add(sfValue);

        this.add(stipendLabel);
        this.add(stipendValue);

        // Check if the student is an instance of HDStudent and add graduation marks if it is
        if (student instanceof HDStudent) {
            JLabel gradLabel = new JLabel("Graduation Marks:");
            JLabel gradValue = new JLabel(""+((HDStudent) student).graduationCGPA);

            this.add(gradLabel);
            this.add(gradValue);
        }

        this.setVisible(true);
    }
}
