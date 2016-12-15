package org.lpw.ranch.user;

import net.sf.json.JSONObject;

/**
 * @author lpw
 */
public interface MockUser {
    /**
     * 注册用户服务。
     */
    void register();

    /**
     * 验证。
     *
     * @param user 用户信息。
     * @param id   目标ID值。
     */
    void verify(JSONObject user, String id);
}
