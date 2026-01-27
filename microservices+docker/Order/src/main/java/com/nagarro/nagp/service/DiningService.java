package com.nagarro.nagp.service;

import com.nagarro.nagp.dto.RestaurantDto;
import com.nagarro.nagp.dto.UserDto;
import com.nagarro.nagp.exception.GenericException;
import com.nagarro.nagp.model.Dining;
import com.nagarro.nagp.proxy.ApiProxy;
import com.nagarro.nagp.repo.DiningRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DiningService {

    @Autowired
    private DiningRepo diningRepo;

    @Autowired
    private NotificationService notificationService;
    private final JavaMailSender emailSender;

    @Autowired
    private ApiProxy apiProxy;

    @Autowired
    public DiningService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public Dining createDiningOrder(Dining dining) {
        dining.setStatus("Pending");
        sendNotification(dining,"Order Booked","order Booked: "+LocalDateTime.now());
        return diningRepo.save(dining);
    }

    public List<Dining> getAllDining() {
        return diningRepo.findAll();
    }

    public Dining getDiningById(Long id) {
        return diningRepo.findById(id)
                .orElseThrow(() -> new GenericException("400","Dining not found"));
    }

    public Dining updateDiningStatus(Long id, String status) {
        Dining dining = getDiningById(id);
        dining.setStatus(status);
        sendNotification(dining,"Dining "+status,"Dining "+status+" :"+LocalDateTime.now());
        return diningRepo.save(dining);
    }

    public String deleteDiningOrder(Long id) {
       Optional<Dining> order= Optional.ofNullable(getDiningById(id));
        sendNotification(order.get(),"Dining Cancelled","Dining Cancelled: "+LocalDateTime.now());
        diningRepo.deleteById(id);
        return "Dining Order Deleted SuccessFully";
    }

    private void sendEmail(String userMail,String retroMail,String sub,String body) {
        notificationService.sendNotification(userMail,sub,body,retroMail);
        notificationService.sendNotification(retroMail,sub,body,userMail);
    }

    private void sendNotification(Dining dining,String sub, String body) {
        UserDto user=apiProxy.getUserDto(dining.getUserId());
        RestaurantDto restaurantDto=apiProxy.getRestaurantDto(dining.getRestaurantId());
        if(null==user){
            throw  new GenericException("400","User Not Register");
        }
        if(null==restaurantDto){
            throw  new GenericException("400","Restaurant Not Register");
        }
        sendEmail(user.getMailId(), restaurantDto.getMailId(),sub,body);
    }

}

