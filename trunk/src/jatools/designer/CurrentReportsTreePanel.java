package jatools.designer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.TreeMap;

import jatools.ReportDocument;
import jatools.util.Util;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;


public class CurrentReportsTreePanel extends JScrollPane {
	private JPopupMenu jpopupMenu;
	private JTree tree;
	public CurrentReportsTreePanel() {
		this.setViewportView(this.createCurrentReportsTree());
	}
	public JTree createCurrentReportsTree(){
		JMenuItem jmenuItem = new JMenuItem("��");
        jmenuItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String path = getSelectedPath();

                    if (path != null) {
                        openReport(path);
                    }
                }
            });
        jpopupMenu = new JPopupMenu();
        jpopupMenu.add(jmenuItem);
		
		final ToolTipTreeNode root = new ToolTipTreeNode("root","root");
		
		int size = App.getMruManager().getSize();
		for (int i = 0; i < size; i++) {
			String mru = App.getMruManager().get(i);
			String nodeName = substring(mru, "\\");
			ToolTipTreeNode newChild = new ToolTipTreeNode(nodeName,mru);
			root.add(newChild);
		}
		
		App.getMruManager().addChangeListener(new ChangeListener(){

			public void stateChanged(ChangeEvent e) {
				setNodeChilds(root);
				TreeModel model = tree.getModel();
				((DefaultTreeModel) model).reload();
			}
			
		});
		
		
		tree = new JTree(root){
		      public String getToolTipText(MouseEvent evt) {
		          if (getRowForLocation(evt.getX(), evt.getY()) == -1)
		            return null;
		          TreePath curPath = getPathForLocation(evt.getX(), evt.getY());
		          return ((ToolTipTreeNode) curPath.getLastPathComponent())
		              .getToolTipText();
		        }
		      };
		tree.setToolTipText("");
		tree.setRootVisible(false);
		DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) tree.getCellRenderer();
        renderer.setLeafIcon(Util.getIcon("/jatools/icons/tmp.gif"));
        tree.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int mods = e.getModifiers();

                String path = getSelectedPath();

                if (path != null) {
                    if (e.getClickCount() == 2) {
                        openReport(path);
                    } else if ((mods & InputEvent.BUTTON3_MASK) != 0) {
                        jpopupMenu.show(tree, e.getX(), e.getY());
                    }
                }
            }
        });
		return tree;
	}
	
	private void setNodeChilds(ToolTipTreeNode node){
		node.removeAllChildren();
		int size = App.getMruManager().getSize();
		for (int i = 0; i < size; i++) {
			String mru = App.getMruManager().get(i);
			String nodeName = substring(mru, "\\");
			ToolTipTreeNode newChild = new ToolTipTreeNode(nodeName,mru);
			node.add(newChild);
		}
	}
	
	private String getSelectedPath() {
		ToolTipTreeNode node = (ToolTipTreeNode) tree.getLastSelectedPathComponent();

        if ((node != null) && node.isLeaf()) {
            return node.getToolTipText();
        } else {
            return null;
        }
    }
	
	private void openReport(String path) {
        try {
            ReportDocument doc = ReportDocument.load(new File(path));
            Main.getInstance()
                .createEditor(doc, ReportDocument.getCachedFile(doc).getName(),
                ReportDocument.getCachedFile(doc).getAbsolutePath());
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
	
	private String substring(String str,String s){
		return str.substring(str.lastIndexOf(s)+1, str.length());
	}
	
	class ToolTipTreeNode extends DefaultMutableTreeNode {
		  private String toolTipText;

		  public ToolTipTreeNode(String str, String toolTipText) {
		    super(str);
		    this.toolTipText = toolTipText;
		  }

		  public String getToolTipText() {
		    return toolTipText;
		  }
		}
}
