package org.lpw.ranch.gps;

import net.sf.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.lpw.tephra.ctrl.validate.Validators;
import org.lpw.tephra.test.TephraTestSupport;
import org.lpw.tephra.test.mock.MockHelper;
import org.lpw.tephra.util.HttpImpl;
import org.lpw.tephra.util.Message;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @author lpw
 */
public class GpsCtrlTest extends TephraTestSupport {
    @Autowired
    protected Message message;
    @Autowired
    protected MockHelper mockHelper;
    @Autowired
    protected GpsService gpsService;

    @Test
    public void address() throws Exception {
        mockHelper.reset();
        mockHelper.mock("/gps/address");
        JSONObject object = mockHelper.getResponse().asJson();
        Assert.assertEquals(1101, object.getInt("code"));
        Assert.assertEquals(message.get(Validators.PREFIX + "not-match-regex", message.get(GpsModel.NAME + ".lat"), "^-?\\d{1,3}\\.\\d+$"),
                object.getString("message"));

        mockHelper.reset();
        mockHelper.getRequest().addParameter("lat", "123");
        mockHelper.mock("/gps/address");
        object = mockHelper.getResponse().asJson();
        Assert.assertEquals(1101, object.getInt("code"));
        Assert.assertEquals(message.get(Validators.PREFIX + "not-match-regex", message.get(GpsModel.NAME + ".lat"), "^-?\\d{1,3}\\.\\d+$"),
                object.getString("message"));

        mockHelper.reset();
        mockHelper.getRequest().addParameter("lat", "39.917266");
        mockHelper.mock("/gps/address");
        object = mockHelper.getResponse().asJson();
        Assert.assertEquals(1102, object.getInt("code"));
        Assert.assertEquals(message.get(Validators.PREFIX + "not-match-regex", message.get(GpsModel.NAME + ".lng"), "^\\d{1,3}\\.\\d+$"),
                object.getString("message"));

        mockHelper.reset();
        mockHelper.getRequest().addParameter("lat", "39.917266");
        mockHelper.getRequest().addParameter("lng", "123");
        mockHelper.mock("/gps/address");
        object = mockHelper.getResponse().asJson();
        Assert.assertEquals(1102, object.getInt("code"));
        Assert.assertEquals(message.get(Validators.PREFIX + "not-match-regex", message.get(GpsModel.NAME + ".lng"), "^\\d{1,3}\\.\\d+$"),
                object.getString("message"));

        mockHelper.reset();
        mockHelper.getRequest().addParameter("lat", "39.917266");
        mockHelper.getRequest().addParameter("lng", "-116.397140");
        mockHelper.mock("/gps/address");
        object = mockHelper.getResponse().asJson();
        Assert.assertEquals(1102, object.getInt("code"));
        Assert.assertEquals(message.get(Validators.PREFIX + "not-match-regex", message.get(GpsModel.NAME + ".lng"), "^\\d{1,3}\\.\\d+$"),
                object.getString("message"));

        mockHelper.reset();
        mockHelper.getRequest().addParameter("lat", "39.917266");
        mockHelper.getRequest().addParameter("lng", "116.397140");
        mockHelper.mock("/gps/address");
        object = mockHelper.getResponse().asJson();
        Assert.assertEquals(0, object.getInt("code"));
        JSONObject data = object.getJSONObject("data");
        Assert.assertEquals("北京市东城区东华门大街", data.getString("address"));
        JSONObject component = data.getJSONObject("component");
        Assert.assertEquals("中国", component.getString("nation"));
        Assert.assertEquals("北京市", component.getString("province"));
        Assert.assertEquals("北京市", component.getString("city"));
        Assert.assertEquals("东城区", component.getString("district"));
        Assert.assertEquals("东华门大街", component.getString("street"));
        Assert.assertEquals("", component.getString("street_number"));
        Assert.assertEquals("110101", data.getString("adcode"));

        Field field = GpsServiceImpl.class.getDeclaredField("qqlbsKey");
        field.setAccessible(true);
        field.set(gpsService, "");
        mockHelper.reset();
        mockHelper.getRequest().addParameter("lat", "39.917266");
        mockHelper.getRequest().addParameter("lng", "116.397140");
        mockHelper.mock("/gps/address");
        object = mockHelper.getResponse().asJson();
        Assert.assertEquals(0, object.getInt("code"));
        Assert.assertTrue(object.getJSONObject("data").isEmpty());

        field.set(gpsService, "qqlbs-key");
        mockHelper.reset();
        mockHelper.getRequest().addParameter("lat", "39.917266");
        mockHelper.getRequest().addParameter("lng", "116.397140");
        mockHelper.mock("/gps/address");
        object = mockHelper.getResponse().asJson();
        Assert.assertEquals(0, object.getInt("code"));
        Assert.assertTrue(object.getJSONObject("data").isEmpty());

        field = GpsServiceImpl.class.getDeclaredField("http");
        field.setAccessible(true);
        field.set(gpsService, new HttpImpl() {
            @Override
            public String get(String url, Map<String, String> headers, String parameters) {
                return null;
            }
        });
        mockHelper.reset();
        mockHelper.getRequest().addParameter("lat", "39.917266");
        mockHelper.getRequest().addParameter("lng", "116.397140");
        mockHelper.mock("/gps/address");
        object = mockHelper.getResponse().asJson();
        Assert.assertEquals(0, object.getInt("code"));
        Assert.assertTrue(object.getJSONObject("data").isEmpty());
    }
}
