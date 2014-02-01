package cs320.pattern;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cs320.model.PledgesI;
import cs320.model.ProjectsI;
import cs320.model.RewardsC;
import cs320.model.UsersC;

public abstract class FactoryF {

    static private final String fstrCfgPath = "conf/conf.xml";
    static private final String fstrDataBase = "database";
    static private final String fstrType = "type";
    static private final String fstrSql = "sql";
    static private final String fstrXml = "xml";

    static private FactoryF instance = null;
    static private ProjectsI prjs = null;
    static private UsersC usrs = null;

    protected FactoryF() {

    }

    public static Document load(String filename) {
        Document document = null;
        try {
            SAXReader saxReader = new SAXReader();
            document = saxReader.read(new File(filename));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return document;
    }

    private static FactoryF CreateInstanceByCfg(String path) {
        FactoryF f = null;

        Document dom = load(path);
        if (dom != null) {
            Element root = dom.getRootElement();
            Element eDatabase = root.element(fstrDataBase);
            if (eDatabase != null) {
                if (eDatabase.attributeValue(fstrType).equals(fstrSql)) {
                    f = new FactorySqlF(eDatabase);
                } else if (eDatabase.attributeValue(fstrType).equals(fstrXml)) {
                    f = new FactoryXmlF(eDatabase);
                }
            }
        }

        dom.clearContent();

        return f;

    }

    protected static String getWebRoot() {
        String path = ProjectsI.class.getClassLoader().getResource("/").getPath();
        for (int i = 0; i < 3; i++) {
            path = path.substring(0, path.lastIndexOf("/"));
        }
        return path;
    }

    public static FactoryF getFactory() {
        if (instance == null) {
            String path = getWebRoot() + "/" + fstrCfgPath;
            instance = CreateInstanceByCfg(path);
        }

        return instance;
    }

    public static ProjectsI getProjects() {
        if (prjs == null) {
            prjs = getFactory().CreateProjects();
        }

        return prjs;
    }

    public static UsersC getUsers() {
        if (usrs == null) {
            usrs = getFactory().CreateUsers();
        }

        return usrs;
    }

    abstract protected ProjectsI CreateProjects();

    abstract protected UsersC CreateUsers();

    abstract public RewardsC CreateRewards(long id);

    abstract public PledgesI CreatePledges(long id);
}
