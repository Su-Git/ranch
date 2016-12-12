package org.lpw.ranch.comment;

import org.lpw.ranch.audit.Audit;
import org.lpw.ranch.audit.AuditTesterDao;
import org.lpw.tephra.crypto.Sign;
import org.lpw.tephra.dao.orm.lite.LiteOrm;
import org.lpw.tephra.test.TephraTestSupport;
import org.lpw.tephra.test.mock.MockCarousel;
import org.lpw.tephra.test.mock.MockHelper;
import org.lpw.tephra.util.Converter;
import org.lpw.tephra.util.Generator;
import org.lpw.tephra.util.Message;
import org.lpw.tephra.util.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;

/**
 * @author lpw
 */
public class TestSupport extends TephraTestSupport implements AuditTesterDao<CommentModel> {
    @Autowired
    protected Message message;
    @Autowired
    protected Generator generator;
    @Autowired
    protected Converter converter;
    @Autowired
    protected LiteOrm liteOrm;
    @Autowired
    protected Sign sign;
    @Autowired
    protected MockHelper mockHelper;
    @Autowired
    protected MockCarousel mockCarousel;

    public CommentModel create(int i, Audit audit) {
        return create(i, "owner " + i, "author " + i, audit);
    }

    protected CommentModel create(int i, String owner, String author, Audit audit) {
        CommentModel comment = new CommentModel();
        comment.setKey("key " + i);
        comment.setOwner(owner);
        comment.setAuthor(author);
        comment.setSubject("subject " + i);
        comment.setLabel("label " + i);
        comment.setContent("content " + i);
        comment.setScore(i);
        comment.setPraise(10 + i);
        comment.setTime(new Timestamp(System.currentTimeMillis() - i * TimeUnit.Hour.getTime()));
        comment.setAudit(audit.getValue());
        liteOrm.save(comment);

        return comment;
    }

    public CommentModel findById(String id) {
        return liteOrm.findById(CommentModel.class, id);
    }
}
