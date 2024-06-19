package de_ait_tr.supplier.service;

import de_ait_tr.supplier.service.interfaces.SupplyRequestService;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.util.*;


@Service
public class SupplyRequestServiceImpl implements SupplyRequestService {

    private final JavaMailSender sender;
    private final Configuration mailConfig;

    public SupplyRequestServiceImpl(JavaMailSender sender, Configuration mailConfig) {
        this.sender = sender;
        this.mailConfig = mailConfig;

        mailConfig.setDefaultEncoding("UTF-8");
        mailConfig.setTemplateLoader(new ClassTemplateLoader(SupplyRequestServiceImpl.class, "/mail/"));
    }

    @Override
    public void sendSupplyRequest(Map<String, Integer> supplyRequest) {

        String supplierEmail = "d83935353@gmail.com";

        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
        String text = generateEmail(supplyRequest);

        try{

            helper.setFrom("d83935353@gmail.com");
            helper.setTo(supplierEmail);
            helper.setSubject("Daily Supply Request");
            helper.setText(text, true);
            sender.send(message);
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    private String generateEmail(Map<String, Integer> supplyRequest){
        try {
            Template template = mailConfig.getTemplate("supply_request.ftlh");
            Map<String, Object> templateMap = new HashMap<>();
            List<Map<String, Object>> supplyList = new ArrayList<>();

            for(Map.Entry<String, Integer> entry : supplyRequest.entrySet()){
                Map<String, Object> product = new HashMap<>();
                product.put("title", entry.getKey());
                product.put("quantity", entry.getValue());
                supplyList.add(product);
            }

            templateMap.put("supplyList", supplyList);

            return FreeMarkerTemplateUtils.processTemplateIntoString(template, templateMap);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
