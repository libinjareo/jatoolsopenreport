package jatools.swingx.wizard;

import javax.swing.event.ChangeListener;





/**
 * ���뾫��
 *
 * ���ڶ��徫����������ԣ����뾫�����������Ķ���������Ҫ�������������������Ա仯
 *
 * @version $Revision: 1.1 $
 * @author $author$
 */
public interface WizardInput extends ChangeListener {
    /**
     * ȡ�ÿ��Խ��ܵ���������
     *
     * @return �ɽ�������
     */
    public int[] getInputTypes();

    /**
     * ���ӵ�������飬�Ա������仯
     *
     * @param output �������
     *
     * @return true/false �ɹ�/ʧ��
     */
    public boolean connect(WizardOutput output);
}
