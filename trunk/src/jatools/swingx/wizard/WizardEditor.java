package jatools.swingx.wizard;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: EZSoft.</p>
 * @author ���ľ�
 * @version 1.0
 */
import java.awt.Component;


/**
 * �༭����
 *
 * <p>���ڶ���༭����������ԣ���ȡ�༭��������������</p>
 * @version $Revision: 1.1 $
 * @author $author$
 */
public interface WizardEditor {
    /**
     * �õ��༭�����
     *
     * @return �༭�����
     */
    public Component getEditorComponent();

    /**
     * ֪ͨ�༭������Ҫ�뿪��
     *
     * @throws Exception ������༭����û��׼��������
     */
    public void leave() throws Exception;

    /**
     * ֪ͨ�༭������Ҫ������
     *
     * @throws Exception ������༭����û׼���ý���
     */
    public void enter() throws Exception;
}