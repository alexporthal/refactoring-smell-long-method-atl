package br.ufscar.dc.tms.detection;

import java.util.ArrayList;

import javax.xml.xquery.XQException;
import javax.xml.xquery.XQResultSequence;

import br.ufscar.dc.tms.xmi.Connection;

/**
 *
 * LongMethod Class
 *
 * @author Alex Malmann Becker
 * @since 2016-10-13
 * @version 1.0
 *
 */
public class LongMethod {

    private ArrayList<String> methodsWithSmell;
    private final String DATABASE_XMI;
    private final int NUMBER_ACTION_ELEMENT;
    private Connection connection;

    /**
     *
     * Class constructor. Get the number of ActionElement to consider a method
     * to smell.
     *
     * @param DATABASE_XMI
     * @param NUMBER_ACTION_ELEMENT
     */
    public LongMethod(String DATABASE_XMI, int NUMBER_ACTION_ELEMENT) {
        this.DATABASE_XMI = DATABASE_XMI;
        this.NUMBER_ACTION_ELEMENT = NUMBER_ACTION_ELEMENT;
        this.methodsWithSmell = new ArrayList<>();
    }

    /**
     *
     * Method that performs the verification of the Smell Long Method and
     * returns the list with them.
     *
     * @return
     * @throws XQException
     */
    public ArrayList<String> executeVerification() throws XQException {
        this.connection = new Connection(DATABASE_XMI);
        this.searchElementsMethod();
        return this.getMethodsWithSmell();
    }

    /**
     *
     * Find all elements that are methods.
     *
     * @throws XQException
     */
    private void searchElementsMethod() throws XQException {
        XQResultSequence results = this.connection.executeQuery("//codeElement[@xsi:type='code:MethodUnit']");
        ArrayList<String> methods = new ArrayList<>();
        while (results.next()) {
            methods.add(results.getItem().getNode().getAttributes().getNamedItem("name").getChildNodes().item(0)
                    .getNodeValue());
        }
        verifyMethodsWithSmell(methods);
    }

    /**
     *
     * Receive a list of all the methods and checks which methods have smell.
     *
     * @param methods
     * @throws XQException
     */
    private void verifyMethodsWithSmell(ArrayList<String> methods) throws XQException {
        for (String method : methods) {
            if (!method.isEmpty()) {
                XQResultSequence results = this.connection
                        .executeQuery("/count(//codeElement[@xsi:type='code:MethodUnit'][@name='" + method + "']"
                                + "/codeElement[@xsi:type='action:BlockUnit']"
                                + "/codeElement[@xsi:type='action:ActionElement'])");
                while (results.next()) {
                    if (Integer.parseInt(results.getItemAsString(null)) > NUMBER_ACTION_ELEMENT) {
                        methodsWithSmell.add(method);
                    }
                }
            }
        }
    }

    /**
     *
     * Returns the list of methods with smell.
     *
     * @return ArrayList<String>
     */
    public ArrayList<String> getMethodsWithSmell() {
        return methodsWithSmell;
    }
    
    public void verifyContentMethod(String nameMethod){
        
    }

}
