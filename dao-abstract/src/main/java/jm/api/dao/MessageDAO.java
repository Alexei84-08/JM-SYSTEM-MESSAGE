package jm.api.dao;

import jm.model.Message;

import java.util.List;

public interface MessageDAO {

    List<Message> getAll();

    List<Message> getMessagesByChannelId(Long id);

    List<Message> getMessageByContent(String word);

    List<Message> getMessagesByChannelIdForPeriod(Long id, String startDate, String endDate);

    void persist(Message message);

    void deleteById(Long id);

    Message merge(Message message);

    Message getById(Long id);

    List<Message> getStarredMessagesForUser(Long id);

}
