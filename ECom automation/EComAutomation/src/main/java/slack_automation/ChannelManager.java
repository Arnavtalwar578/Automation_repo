package slack_automation;

import org.json.JSONObject;

import io.jsonwebtoken.io.IOException;

public class ChannelManager {

    private static final String CREATE_CHANNEL = "conversations.create";
    private static final String RENAME_CHANNEL = "conversations.rename";
    private static final String LIST_CHANNELS = "conversations.list";
    private static final String ARCHIVE_CHANNEL = "conversations.archive";

    private SlackAPIUtils apiUtils;

    public ChannelManager(SlackAPIUtils apiUtils) {
        this.apiUtils = apiUtils;
    }

    public JSONObject createChannel(String channelName) throws IOException, java.io.IOException {
        JSONObject params = new JSONObject();
        params.put("name", channelName);
        return apiUtils.sendPostRequest(CREATE_CHANNEL, params);
    }

    public JSONObject renameChannel(String channelId, String newName) throws IOException, java.io.IOException {
        JSONObject params = new JSONObject();
        params.put("channel", channelId);
        params.put("name", newName);
        return apiUtils.sendPostRequest(RENAME_CHANNEL, params);
    }

    public JSONObject listChannels() throws IOException, java.io.IOException {
        return apiUtils.sendPostRequest(LIST_CHANNELS, new JSONObject());
    }

    public JSONObject archiveChannel(String channelId) throws IOException, java.io.IOException {
        JSONObject params = new JSONObject();
        params.put("channel", channelId);
        try {
			return apiUtils.sendPostRequest(ARCHIVE_CHANNEL, params);
		} catch (java.io.IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return params;
    }
}
