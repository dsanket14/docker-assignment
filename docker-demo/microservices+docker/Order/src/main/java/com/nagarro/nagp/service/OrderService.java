package com.nagarro.nagp.service;

import com.nagarro.nagp.dto.RestaurantDto;
import com.nagarro.nagp.dto.UserDto;
import com.nagarro.nagp.exception.GenericException;
import com.nagarro.nagp.model.Order;
import com.nagarro.nagp.proxy.ApiProxy;
import com.nagarro.nagp.repo.OrderRepository;
import com.nagarro.nagp.util.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private NotificationService notificationService;
    private final JavaMailSender emailSender;

    @Autowired
    private ApiProxy apiProxy;

    @Autowired
    public OrderService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public Order createOrder(Order order) {
        order.setStatus(OrderStatus.PENDING);
        BigDecimal totalAmount = order.getItems().stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalAmount(totalAmount);
        sendNotification(order,"Order Placed","order placed: "+LocalDateTime.now());
        return orderRepository.save(order);
    }


    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new GenericException("400","Order not found"));
    }

    public Order updateOrderStatus(Long id, OrderStatus status) {
        Order order = getOrderById(id);
        if(null==order){
            throw  new GenericException("400","Order Not found");
        }
        order.setStatus(status);
        sendNotification(order,"Order "+status,"order "+status+" :"+LocalDateTime.now());
        return orderRepository.save(order);
    }

    public String deleteOrder(Long id) {
       Optional<Order> order=orderRepository.findById(id);
       if(order.isEmpty()){
           throw new GenericException("400", "Order Not Found");
       }
        sendNotification(order.get(),"Order Cancelled","Order Cancelled: "+LocalDateTime.now());
        orderRepository.deleteById(id);
        return "Order Deleted SuccessFully";
    }

    private void sendEmail(String userMail,String retroMail,String sub,String body) {
        notificationService.sendNotification(userMail,sub,body,retroMail);
        notificationService.sendNotification(retroMail,sub,body,userMail);
    }

    private void sendNotification(Order order,String sub, String body) {
        UserDto user=apiProxy.getUserDto(order.getUserId());
        RestaurantDto restaurantDto=apiProxy.getRestaurantDto(order.getRestaurantId());
        if(null==user){
            throw  new GenericException("400","User Not Register");
        }
        if(null==restaurantDto){
            throw  new GenericException("400","Restaurant Not Register");
        }
        sendEmail(user.getMailId(), restaurantDto.getMailId(),sub,body);
    }


}

