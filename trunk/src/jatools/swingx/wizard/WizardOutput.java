package jatools.swingx.wizard;

import javax.swing.event.ChangeListener;


/**
 * �������
 * ��Ҫ���ڼ������Ա༭������ı仯����������ȷ�����������������Եı仯��֪ͨ��������
 * @version $Revision: 1.1 $
 * @author $author$
 */
public interface WizardOutput {
    /**
     * ȡ��Ψһ���������
     *
     * @return �������
     */
    public int getOutputType();

    public void checkAvailable() throws Exception;

    /**
     * ȡ�����
     *
     * @return �������
     */
    public Object output() throws Exception;

    /**
     * ע��仯������
     *
     * @param lst ������
     */
    public void addChangeListener(ChangeListener lst);

    /**
     * ע���仯������
     *
     * @param lst ������
     */
    public void removeChangeListener(ChangeListener lst);
}
