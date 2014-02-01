package cs320.pattern;

import org.dom4j.Element;

import cs320.model.PledgesI;
import cs320.model.PledgesXmlC;
import cs320.model.ProjectsI;
import cs320.model.ProjectsXmlC;
import cs320.model.RewardsC;
import cs320.model.RewardsXmlC;
import cs320.model.UsersC;
import cs320.model.UsersXmlC;

public class FactoryXmlF extends FactoryF {

    static private final String fstrPath = "url";
    static private final String fstrPrjs = "projects";
    static private final String fstrUsrs = "users";

    static private String strPath;
    static private String strPrjs;
    static private String strUsrs;

    static public enum DB {
        PROJECTS,
        USERS
    }

    ;

    public FactoryXmlF(Element el) {
        super();
        parseCfg(el);
    }

    private void parseCfg(Element el) {
        strPath = el.elementTextTrim(fstrPath);
        strPrjs = el.elementTextTrim(fstrPrjs);
        strUsrs = el.elementTextTrim(fstrUsrs);
    }

    public static String getDbConnection(DB db) {

        String ret = null;

        switch (db) {
            case PROJECTS:
                ret = getWebRoot() + "/" + strPath + "/" + strPrjs;
                break;

            case USERS:
                ret = getWebRoot() + "/" + strPath + "/" + strUsrs;
                break;

            default:
                break;
        }

        return ret;
    }

    public ProjectsI CreateProjects() {
        return new ProjectsXmlC();
    }

    public UsersC CreateUsers() {
        return new UsersXmlC();
    }

    public RewardsC CreateRewards(long id) {
        return new RewardsXmlC();
    }

    public PledgesI CreatePledges(long id) {
        return new PledgesXmlC();
    }
}
