package org.lpw.ranch.audit;

import org.lpw.tephra.ctrl.execute.Execute;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;

/**
 * @author lpw
 */
@Controller(TestAuditModel.NAME + ".ctrl")
@Execute(name = "/audit/", key = TestAuditModel.NAME, code = "99")
public class TestAuditCtrl extends AuditCtrlSupport {
    @Inject
    private TestAuditService auditService;

    @Override
    protected AuditService getAuditService() {
        return auditService;
    }
}
