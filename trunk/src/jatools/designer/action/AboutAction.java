package jatools.designer.action;

import jatools.designer.Main;

import jatools.swingx.CommandPanel;

import jatools.util.Util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * DOCUMENT ME!
 * 
 * @author $author$
 * @version $Revision$
 */
public class AboutAction extends ReportAction {
	/**
	 * Creates a new AboutAction object.
	 */
	public AboutAction() {
		super("I8N:���������...");
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param e
	 *            DOCUMENT ME!
	 */
	public void actionPerformed(ActionEvent e) {
		new _AboutDialog();
	}

	class _AboutDialog extends JDialog {
		_AboutDialog() {
			super(Main.getInstance(), "���������", true);

			JPanel panel = new JPanel(new GridBagLayout());
			JLabel label = new JLabel(Util.getIcon("/jatools/icons/about.jpg"));
			label.setHorizontalAlignment(SwingConstants.LEFT);
			label.setBackground(Color.white);
			label.setOpaque(true);

			GridBagConstraints gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.BOTH;
			gbc.anchor = GridBagConstraints.WEST;

			gbc.gridwidth = GridBagConstraints.REMAINDER;
			panel.add(label, gbc);

			gbc.gridwidth = 1;
			panel.add(Box.createHorizontalStrut(10), gbc);

			gbc.weightx = 100.0;

			String html = "<html>���(JOR)<br>��Ȩ�û�:DEMO";

			html += "<br><br>���ݽܴ�������޹�˾,��Ȩ����  <a href='http://www.jatools.com'>www.jatools.com</a><br>������ѯ:0571-88254255/57-18<br>����֧��:0571-88254255/57-13&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;MSN: <a href='#' >jatools0571@hotmail.com</a><br>�������´�:010-51297501";
			panel.add(new JLabel(html), gbc);

			CommandPanel commandPanel = CommandPanel.createPanel();
			JButton doneCommand = new JButton("ȷ��");
			doneCommand.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					hide();
					
				}
			});

			commandPanel.addComponent(doneCommand);

			getContentPane().add(panel, BorderLayout.CENTER);
			getContentPane().add(commandPanel, BorderLayout.SOUTH);
			setSize(new Dimension(440, 370));

			setLocationRelativeTo(Main.getInstance());
			show();
		}
	}
}
