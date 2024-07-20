package slack_automation;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;

public class SlackAPIUtils {

    private static final String API_URL = "https://slack.com/api/";

    private String token;

    public SlackAPIUtils(String token) {
        this.token = token;
    }

    public JSONObject sendPostRequest(String endpoint, JSONObject params) throws IOException {
        String url = API_URL + endpoint;
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(url);
            post.setHeader("Authorization", "Bearer " + token);
            post.setHeader("Content-Type", "application/json");
            post.setEntity(new org.apache.http.entity.StringEntity(params.toString()));

            try (CloseableHttpResponse response = httpClient.execute(post)) {
                HttpEntity entity = response.getEntity();
                String responseBody = EntityUtils.toString(entity);
                return new JSONObject(responseBody);
            }
        }
    }
}