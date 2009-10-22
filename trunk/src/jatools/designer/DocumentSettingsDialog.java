package jatools.designer;

import jatools.ReportDocument;
import jatools.swingx.CommandPanel;
import jatools.swingx.TemplateTextField;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;



/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class DocumentSettingsDialog extends JDialog {
    TemplateTextField titleField = new TemplateTextField();

    private ReportDocument doc;

    /**
     * Creates a new DocumentSettingsDialog object.
     *
     * @param doc DOCUMENT ME!
     */
    public DocumentSettingsDialog(ReportDocument doc) {
        super(Main.getInstance(), "��������", true);

        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.WEST;
        p.add(new JLabel("�������:"), gbc);
        gbc.weightx = 1.0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        p.add(titleField, gbc);
        
        gbc.weighty = 1.0;
        p.add(Box.createVerticalStrut(20), gbc);

        CommandPanel cp = CommandPanel.createPanel(false);
        JButton cancel = new JButton("ȡ��");
        cancel.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    cancel();
                }
            });

        JButton ok = new JButton("ȷ��");
        ok.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    done();
                }
            });

        cp.addComponent(ok);
        cp.addComponent(cancel);

       
        this.getContentPane().add(p, BorderLayout.CENTER);

        this.getContentPane().add(cp, BorderLayout.SOUTH);
        this.setSize(510, 305);
        this.setLocationRelativeTo(Main.getInstance());

        if (doc != null) {
            this.titleField.setText(doc.getTitle());
           
            this.doc = doc;
        }
    }

    protected void done() {
        if ((this.titleField.getText() != null) && (this.titleField.getText().trim().length() > 0)) {
            this.doc.setTitle(this.titleField.getText().trim());
        } else {
            this.doc.setTitle(null);
        }

       
        hide();
    }

    protected void cancel() {
        hide();
    }

    
}
