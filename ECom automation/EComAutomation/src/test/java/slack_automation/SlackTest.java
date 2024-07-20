package slack_automation;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.jsonwebtoken.io.IOException;

import org.json.JSONObject;

public class SlackTest {

    private SlackAPIUtils apiUtils;
    private ChannelManager channelManager;
    private String testChannelId;

    @BeforeClass
    public void setUp() {
        String token = "slack-api-token"; 
        apiUtils = new SlackAPIUtils(token);
        channelManager = new ChannelManager(apiUtils);
    }

    @Test
    public void testCreateAndRenameChannel() throws IOException, java.io.IOException {
        JSONObject createResponse = channelManager.createChannel("test-channel");
        Assert.assertTrue(createResponse.getBoolean("ok"), "Channel creation failed");
        testChannelId = createResponse.getJSONObject("channel").getString("id");

        JSONObject renameResponse = channelManager.renameChannel(testChannelId, "new-test-channel");
        Assert.assertTrue(renameResponse.getBoolean("ok"), "Channel renaming failed");

        JSONObject listResponse = channelManager.listChannels();
        Assert.assertTrue(listResponse.getBoolean("ok"), "Listing channels failed");
        boolean nameChanged = false;
        for (Object channel : listResponse.getJSONArray("channels")) {
            JSONObject jsonChannel = (JSONObject) channel;
            if (jsonChannel.getString("id").equals(testChannelId)) {
                if (jsonChannel.getString("name").equals("new-test-channel")) {
                    nameChanged = true;
                }
                break;
            }
        }
        Assert.assertTrue(nameChanged, "Channel name change not reflected");
    }

    @Test
    public void testArchiveChannel() throws IOException, java.io.IOException {
        JSONObject archiveResponse = channelManager.archiveChannel(testChannelId);
        Assert.assertTrue(archiveResponse.getBoolean("ok"), "Channel archiving failed");

        JSONObject listResponse = channelManager.listChannels();
        Assert.assertTrue(listResponse.getBoolean("ok"), "Listing channels failed");
        boolean channelArchived = true;
        for (Object channel : listResponse.getJSONArray("channels")) {
            JSONObject jsonChannel = (JSONObject) channel;
            if (jsonChannel.getString("id").equals(testChannelId)) {
                if (!jsonChannel.getBoolean("is_archived")) {
                    channelArchived = false;
                }
                break;
            }
        }
        Assert.assertTrue(channelArchived, "Channel not archived");
    }
}
