package jatools.designer.action;

import jatools.ReportDocument;
import jatools.designer.Main;
import jatools.designer.chooser.ReportChooser;
import jatools.swingx.MessageBox;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;



/**
 * @author   java9
 */
public class NewAction extends ReportAction {


    /**
     * Creates a new NewAction object.
     *
     * @param owner DOCUMENT ME!
     */
    public NewAction() {
        super("�½�...   ", getIcon("/jatools/icons/new.gif")); // //$NON-NLS-2$
        setStroke(ctrl(KeyEvent.VK_N));
    }

    /**
     * DOCUMENT ME!
     *
     * @param e
     *            DOCUMENT ME!
     */
    public void actionPerformed(ActionEvent e) {
        ReportChooser chooser = ReportChooser.getInstance();

        if (chooser.showDialog("�½�", ReportChooser.SHOW_NEW)) { //

            ReportDocument doc = chooser.getDocument();

            if (doc != null) {
           
                Main.getInstance().createEditor(doc, "δ����", null);
            }
        }
    }

    protected boolean saveAs() {
        int answer = MessageBox.show(getEditor(), "��ʾ...",
                "�Ƿ񱣴�Ե�ǰ������޸�?", MessageBox.YES_NO_CANCEL); // //$NON-NLS-2$

        if (answer == MessageBox.YES) {
            ReportDocument doc = getEditor().getDocument();

            if (doc != null) {
                ReportChooser chooser = ReportChooser.getInstance();
                File docFile = ReportDocument.getCachedFile(doc);

                if (docFile != null) {
                    chooser.setSelectedFile(docFile);
                }

                chooser.setDocument(doc);
                chooser.showSaveDialog();
            }
        }

        return answer != MessageBox.CANCEL;
    }

    /**
         * @param newWindow   The newWindow to set.
         * @uml.property   name="newWindow"
         */
    public void setNewWindow(boolean newWindow) {
     //   this.newWindow = newWindow;
    }
}
