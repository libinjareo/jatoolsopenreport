package jatools.designer.wizard.crosstab;

import jatools.data.reader.DatasetReader;
import jatools.designer.App;
import jatools.designer.DataTreeUtil;
import jatools.designer.data.ReaderSelector;
import jatools.designer.wizard.BuilderContext;
import jatools.swingx.CommandPanel;
import jatools.swingx.SwingUtil;
import jatools.util.Util;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultSingleSelectionModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 * <p>Title: CrossSqlBuilder</p>
 * <p>Description: ���汨����</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: </p>
 * @author Jiang Dehua
 * @version 1.0
 */
public class CrossTableBuilder
    extends JDialog  implements ChangeListener {
  JTabbedPane steps;
  ReaderSelector readerSelector;
  CrossHeaderSelector crossHeaderSelector;
  CrossPrintSelector2 crossPrintSelector;

  static final String PREVIEW = App.messages.getString("res.197");
  static final String NEXT = App.messages.getString("res.198");
  static final String FINISHED = App.messages.getString("res.199");
  static final String CANCEL = App.messages.getString("res.4");
  JButton nextCommand = new JButton(NEXT);
  JButton prevCommand = new JButton(PREVIEW);
  JButton cancelCommand = new JButton(CANCEL);
  JButton finishCommand = new JButton(FINISHED);
  private JLabel infoLabel;

  public boolean isExitOk = false;
  private BuilderContext context;
  private DatasetReader selectedReader;

  static final String[] PROMPT_STRINGS = {
      " �������ݼ�ѡ��. ������ֻ��һ�����ݼ� (��ѡ). ", //���ݼ���ʾ��Ϣ
      " ѡ���б���,�б���,��ͳ�ƺ��� (��ѡ)", //��ʾ����ʾ��Ϣ
      App.messages.getString("res.223")
  };

  public CrossTableBuilder() {
    this(null, null);
  }

  public CrossTableBuilder(Frame owner, BuilderContext context) {
    super(owner, App.messages.getString("res.224"), true);
    isExitOk = false;
    setSize(600, 500);
    this.context = context;
    readerSelector = new ReaderSelector(DataTreeUtil.asTree(App.
        getConfiguration()));
    readerSelector.addChangeListener(this);
    crossHeaderSelector = new CrossHeaderSelector();
    crossPrintSelector=new CrossPrintSelector2();
    steps = new JTabbedPane();
    steps.addTab(App.messages.getString("res.80"), readerSelector);
    steps.addTab(App.messages.getString("res.225"), crossHeaderSelector);
    steps.addTab(App.messages.getString("res.226"),crossPrintSelector);

    infoLabel = new JLabel(PROMPT_STRINGS[0]);
    infoLabel.setHorizontalAlignment(JLabel.LEFT);
    infoLabel.setPreferredSize(new Dimension(10, 50));
    infoLabel.setIcon(Util.getIcon("/jatools/icons/help.gif"));
    infoLabel.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));
    getContentPane().add(infoLabel, BorderLayout.NORTH);

    this.getContentPane().add(steps, BorderLayout.CENTER);
    steps.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
    SwingUtil.setBorder6( (JComponent) getContentPane());
    steps.setModel(new DefaultSingleSelectionModel() {
      public void setSelectedIndex(int index) {
        if ( (selectedReader != null) || (index < 1)) {
          super.setSelectedIndex(index);
          activatePanel(index);
        }
        else {
          JOptionPane.showMessageDialog(CrossTableBuilder.this,
                                        new JLabel(App.messages.getString("res.204")));
        }
      }
    });
    steps.setSelectedIndex(0);

    //south command panel
    CommandPanel control = CommandPanel.createPanel(false);
    control.addComponent(prevCommand);
    control.addComponent(nextCommand);
    control.addComponent(finishCommand);
    control.addComponent(cancelCommand);

    prevCommand.setEnabled(false);
    getContentPane().add(control, BorderLayout.SOUTH);

    setAction();
  }

  public void apply() {
    readerSelector.apply(context);
    crossHeaderSelector.apply(context);
    crossPrintSelector.apply(context);
  }

  /**
   * �����Ӧ
   */
  void setAction() {
    prevCommand.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        previous();
      }
    });

    nextCommand.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        next();
      }
    });

    cancelCommand.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cancel();
      }
    });

    finishCommand.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        finish();
      }
    });

    finishCommand.setEnabled(false);
  }

  void activatePanel(int index) {
    switch (index) {
      case 0:
        break;

      case 1:
        crossHeaderSelector.setReader(selectedReader);
        break;
      case 2:
        break;

    }
    infoLabel.setText(PROMPT_STRINGS[index]);
    prevCommand.setEnabled(index > 0);
    nextCommand.setEnabled(index < (steps.getTabCount() - 1));
  }

  /**
   * ���
   */
  protected void finish() {
    apply();
    isExitOk = true;
    dispose();
  }

  /**
   * ȡ��
   */
  private void cancel() {
    dispose();
  }

  /**
   * ��һ��
   */
  private void next() {
    steps.setSelectedIndex(steps.getSelectedIndex() + 1);
  }

  /**
   * ��һ��
   */
  private void previous() {
    steps.setSelectedIndex(steps.getSelectedIndex() - 1);
  }

  public boolean isExitOk() {
    return isExitOk;
  }
  public void stateChanged(ChangeEvent e) {
     if (e.getSource() == readerSelector) {
       selectedReader = readerSelector.getSelectedReader();
       finishCommand.setEnabled(selectedReader != null);
     }

  }
}
