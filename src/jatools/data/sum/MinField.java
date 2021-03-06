package jatools.data.sum;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision: 1.1 $
 */
public class MinField extends Sum {
    /**
     * Creates a new ZGroupMin object.
     *
     * @param groupField DOCUMENT ME!
     * @param calcField DOCUMENT ME!
     */
    public MinField(String[] groupField, String calcField) {
        super(groupField, calcField);
    }

    /**
     * Creates a new ZGroupMin object.
     */
    public MinField() {
    }

    /**
     * DOCUMENT ME!
     *
     * @param values
     *            DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Object getValue(Object[] values) {
        return (calculate(values));
    }

    /**
     * DOCUMENT ME!
     *
     * @param values DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static Object calculate(Object[] values) {
        Object val = null;

        if (values != null) {
            if (values.length == 1) {
                val = values[0];
            } else {
                Comparable comparableMin = (Comparable) values[0];

                for (int i = 1; i < values.length; i++) {
                    Comparable comparableCurrent = (Comparable) values[i];

                    if (comparableCurrent.compareTo(comparableMin) < 0) {
                        comparableMin = comparableCurrent;
                    }
                }

                val = comparableMin;
            }
        }

        return val;
    }
}
