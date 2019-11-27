package jm.dao;

import jm.api.dao.MessageDAO;
import jm.model.Channel;
import jm.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class MessageDAOImpl extends AbstractDao<Message> implements MessageDAO {
    private static final Logger logger = LoggerFactory.getLogger(MessageDAOImpl.class);

    @Override
    public List<Message> getMessageByContent(String word) {
        try {
            return (List<Message>) entityManager.createNativeQuery("select * from messages where content=?", Message.class)
                    .setParameter(1, word);
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Message> getMessagesByChannelId(Long id) {
        try {
            return (List<Message>) entityManager.createNativeQuery("select * from messages where channel_id=?", Message.class)
                    .setParameter(1, id).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Message> getMessagesByChannelIdForPeriod(Long id, String startDate, String endDate) {
        try {
            List<Message> resultList = entityManager.createNativeQuery("select * from messages where channel_id = ? and date_create between ? and ? order by date_create", Message.class)
                    .setParameter(1, id)
                    .setParameter(2, startDate)
                    .setParameter(3, endDate)
                    .getResultList();
            return resultList;
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Message> getStarredMessagesForUser(Long id) {
        try {
            return entityManager.createQuery(
                    "select m from Message m join m.starredByWhom as sm where sm.id = :id",
                    Message.class
            )
                    .setParameter("id", id)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
