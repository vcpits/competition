package competetion.src.main.java.ru.pits.keywords;

import ru.pits.restClient.RestClient;
import ru.pits.restClient.RestRequest;

import javax.ws.rs.core.MultivaluedMap;
import java.util.HashMap;
import java.util.Map;

public class GettingToken {

    /**Входные параметры*/
    private String hash = 'U1NPX0hBUzpTU09fSEFT';;
    private String login = 'TEST_SBMS';
    private String password = '*********';

    /**Выходные параметры*/
    String token;

    /**параметры для http запроса*/
    Map<String, String > headersMap = new HashMap<>();
    MultivaluedMap requestBody;);

    public GettingToken() {
        //Заполняем хэдеры
        headersMap.put("url", "/ps/auth/api/token");
        headersMap.put("Content-Type", "application/x-www-form-urlencoded");
        headersMap.put("Accept-Encoding", "gzip, deflate");
        headersMap.put("Host", "vlg-sso-lb1a.megafon.ru:47132");
        headersMap.put("Accept", "application/json");
        headersMap.put("Content-Length", "56");
        headersMap.put("Authorization", "Basic " + this.hash);

        //Зполняем тело
        requestBody.add("grant_type", "password&");
        requestBody.add("username", this.login);
        requestBody.add("password", this.password);

    }


    public String getToken() {
        RestClient rc = new RestClient();
        RestRequest rr = new RestRequest();
        rr.setHeadersMap(this.headersMap);
        rr.setRequest(this.requestBody);
        token = (String) rc.execute(rr);
        return token;
    }

/*
    Собсвенно в теле должна отлететь такая вот строка:
    String requestBody = "grant_type=password&" +
            "username=" + this.login +
            "&password=" + this.password;
*/

}
