package br.ufscar.dc.tms.bean;

import br.ufscar.dc.tms.detection.LongMethod;
import java.util.ArrayList;
import javax.servlet.http.Part;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.xml.xquery.XQException;

@ManagedBean(name = "refactoringLongMethodMBean")
@SessionScoped
public class RefactoringLongMethodMBean {

    private Part file1;
    private ArrayList<String> methodsWithSmell;
    private int numberLines = 10;

    private static String getFilename(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE
                // fix.
            }
        }
        return null;
    }

    public void executeVerification() throws XQException {
        LongMethod longMethod = new LongMethod(getFilename(getFile1()), numberLines);
        this.methodsWithSmell = longMethod.executeVerification();
    }

    /**
     * @return the file1
     */
    public Part getFile1() {
        return file1;
    }

    /**
     * @param file1 the file1 to set
     */
    public void setFile1(Part file1) throws XQException {
        this.file1 = file1;
        this.executeVerification();
    }

    /**
     * @return the methodsWithSmell
     */
    public ArrayList<String> getMethodsWithSmell() {
        return methodsWithSmell;
    }

    /**
     * @param methodsWithSmell the methodsWithSmell to set
     */
    public void setMethodsWithSmell(ArrayList<String> methodsWithSmell) {
        this.methodsWithSmell = methodsWithSmell;
    }

    /**
     * @return the numberLines
     */
    public int getNumberLines() {
        return numberLines;
    }

    /**
     * @param numberLines the numberLines to set
     */
    public void setNumberLines(int numberLines) {
        this.numberLines = numberLines;
    }

}
