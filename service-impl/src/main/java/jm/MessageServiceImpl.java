package jm;

import jm.api.dao.MessageDAO;
import jm.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional
public class MessageServiceImpl implements MessageService {

    private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    private MessageDAO messageDAO;

    @Autowired
    public void setMessageDAO(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    @Override
    public List<Message> getAllMessages() {
        return messageDAO.getAll();
    }

    @Override
    public List<Message> getMessagesByChannelId(Long id) {
        return messageDAO.getMessagesByChannelId(id);
    }

    @Override
    public List<Message> getMessagesByContent(String word) {
        return messageDAO.getMessageByContent(word);
    }

    @Override
    public Message getMessageById(Long id) {
        return messageDAO.getById(id);
    }

    @Override
    public void createMessage(Message message) {
        messageDAO.persist(message);
    }

    @Override
    public void deleteMessage(Long id) {
        messageDAO.deleteById(id);

    }

    @Override
    public void updateMessage(Message message) {
        messageDAO.merge(message);

    }

    @Override
    public List<Message> getMessagesByChannelIdForPeriod(Long id, String startDate, String endDate) {
        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-d"));
        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-d"));

        return messageDAO.getMessagesByChannelIdForPeriod(id, start.atStartOfDay(), end.atStartOfDay());
    }

    @Override
    public List<Message> getMessagesByBotIdByChannelIdForPeriod(Long botId, Long channelId, String startDate, String endDate) {
        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-d"));
        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-d"));
        return messageDAO.getMessagesByBotIdByChannelIdForPeriod(botId, channelId, start.atStartOfDay(), end.atStartOfDay());
    }

}
