package com.camperfire.marketflow.service.email;

import com.camperfire.marketflow.dto.crud.email.EmailMessageRequest;
import com.camperfire.marketflow.dto.mapper.EmailMapper;
import com.camperfire.marketflow.model.Category;
import com.camperfire.marketflow.model.EmailMessage;
import com.camperfire.marketflow.repository.EmailMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final EmailMessageRepository emailMessageRepository;
    private final EmailMapper emailMapper;
    private final JavaMailSender emailSender;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String TOPIC;

    @Autowired
    public EmailServiceImpl(EmailMessageRepository emailMessageRepository, EmailMapper emailMapper, JavaMailSender emailSender, KafkaTemplate<String, Object> kafkaTemplate, @Value("${kafka.topics.email-topic}") String topic) {
        this.emailMessageRepository = emailMessageRepository;
        this.emailMapper = emailMapper;
        this.emailSender = emailSender;
        this.kafkaTemplate = kafkaTemplate;
        TOPIC = topic;
    }

    @KafkaListener(topics = "${kafka.topics.email-topic}", groupId = "${kafka.groups.email-group}")
    public void listen(EmailMessage emailMessage) {
        SimpleMailMessage msg = new SimpleMailMessage();

        msg.setFrom(emailMessage.getSourceEmail());
        msg.setTo(emailMessage.getDestinationEmail());
        msg.setSubject(emailMessage.getSubject());
        msg.setText(emailMessage.getText());
        emailSender.send(msg);

        emailMessageRepository.save(emailMessage);
    }

    public void submit(EmailMessage emailMessage) {
        kafkaTemplate.send(TOPIC, emailMessage);
    }

    @Override
    public EmailMessage createEmailMessage(EmailMessageRequest request) {
        EmailMessage emailMessage = emailMapper.toEntity(request);

        return emailMessageRepository.save(emailMessage);
    }

    @Override
    public EmailMessage readEmailMessage(Long id) {
        return emailMessageRepository.findById(id).orElseThrow();
    }

    @Override
    public EmailMessage updateEmailMessage(EmailMessageRequest request) {
        EmailMessage emailMessage = emailMessageRepository.findById(request.getId()).orElseThrow();

        //TODO: Implement update logic.

        return emailMessageRepository.save(emailMessage);
    }

    @Override
    public void deleteEmailMessage(Long id) {
        EmailMessage emailMessage = emailMessageRepository.findById(id).orElseThrow();

        emailMessageRepository.delete(emailMessage);
    }
}
