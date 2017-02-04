package org.lpw.ranch.audit;

import org.lpw.ranch.recycle.RecycleModelSupport;
import org.lpw.tephra.dao.model.Jsonable;

import javax.persistence.Column;

/**
 * @author lpw
 */
public class AuditModelSupport extends RecycleModelSupport implements AuditModel {
    private int audit; // 审核状态：0-待审核；1-审核通过；2-审核不通过
    private String auditRemark; // 审核备注

    @Jsonable
    @Column(name = "c_audit")
    @Override
    public int getAudit() {
        return audit;
    }

    @Override
    public void setAudit(int audit) {
        this.audit = audit;
    }

    @Jsonable
    @Column(name = "c_audit_remark")
    @Override
    public String getAuditRemark() {
        return auditRemark;
    }

    @Override
    public void setAuditRemark(String auditRemark) {
        this.auditRemark = auditRemark;
    }
}
