package br.ufscar.dc.tms.xmi;

import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQPreparedExpression;
import javax.xml.xquery.XQResultSequence;

import net.sf.saxon.xqj.SaxonXQDataSource;

/**
 *
 * Connection Class
 *
 * @author Alex Malmann Becker
 * @since 2016-10-13
 * @version 1.0
 *
 */
public class Connection {

    private final String DATABASE_XMI;
    private XQConnection con;
    private XQDataSource ds;

    /**
     *
     * Class constructor.
     *
     * @param DATABASE_XMI
     * @throws XQException
     */
    public Connection(String DATABASE_XMI) throws XQException {
        this.DATABASE_XMI = DATABASE_XMI;

        this.ds = new SaxonXQDataSource();
        this.con = ds.getConnection();
    }

    /**
     *
     * Method to perform the search on XMI.
     *
     * @param expression
     * @return
     * @throws XQException
     */
    public XQResultSequence executeQuery(String expression) throws XQException {
        try {
            XQPreparedExpression exp = this.getCon().prepareExpression("fn:doc('" + DATABASE_XMI + "')" + expression);
            XQResultSequence resultSequence = exp.executeQuery();

            return resultSequence;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     *
     * Returns the connection to the XMI.
     *
     * @return
     */
    public XQConnection getCon() {
        return con;
    }

}
