package org.lpw.ranch.classify;

import net.sf.json.JSONObject;

/**
 * @author lpw
 */
public interface ClassifyService {
    /**
     * 检索分类信息集。
     *
     * @param code 编码前缀。
     * @return 分类信息集。
     */
    JSONObject query(String code);

    /**
     * 检索分类信息树。
     *
     * @param code 编码前缀。
     * @return 分类信息树。
     */
    JSONObject tree(String code);

    /**
     * 查找分类信息。
     *
     * @param ids ID集。
     * @return 分类信息，如果不存在则返回空JSON。
     */
    JSONObject getJsons(String[] ids);

    /**
     * 创建新分类。
     *
     * @param code  编码。
     * @param name  名称。
     * @param label 标签。
     * @return 分类JSON格式数据。
     */
    JSONObject create(String code, String name, String label);

    /**
     * 修改分类信息。
     *
     * @param id    ID值。
     * @param code  编码。
     * @param name  名称。
     * @param label 标签。
     * @return 分类JSON格式数据。
     */
    JSONObject modify(String id, String code, String name, String label);

    /**
     * 删除分类信息。
     *
     * @param id ID值。
     */
    void delete(String id);
}
