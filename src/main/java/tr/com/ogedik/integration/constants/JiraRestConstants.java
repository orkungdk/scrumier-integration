package tr.com.ogedik.integration.constants;

/**
 * @author orkun.gedik
 */
public class JiraRestConstants {

    public static class EndPoint {
        public static String USER = "/rest/api/2/user";
        public static final String SEARCH = "/rest/api/2/search";
        public static String SESSION = "/rest/auth/1/session";

        public static String CREATE(String issueKey){
            return "/rest/api/2/issue/" + issueKey + "/worklog";
        }
    }

    public static class Headers {
        public static String AUTHORIZATION = "Authorization";
    }
}
