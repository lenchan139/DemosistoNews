package news.demosisto.demosistonews;

/**
 * Created by len on 20/05/16.
 */
public class formatHTML {
    public static String changeHTMLFormat(String htmlObject){
        String result = "", CSSCoding = "";

        CSSCoding = htmlObject;
        CSSCoding = CSSCoding.replaceAll("<[aA].*?>", "<u>");
        CSSCoding = CSSCoding.replaceAll("</[aA]>", "</u>");
        return CSSCoding;
    }
}