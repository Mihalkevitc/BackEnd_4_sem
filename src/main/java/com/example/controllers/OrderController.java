package com.example.controllers;

import com.example.entities.Cours;
import com.example.entities.NumberOfItems;
import com.example.entities.Order;
import com.example.entities.OrderItem;
import com.example.security.CustomUserDetails;
import com.example.services.CoursService;
import com.example.services.EmailService;
import com.example.services.OrderService;
import com.example.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private CoursService coursService;
    @Autowired
    private UserService userService;

    private final EmailService emailService;

    public OrderController(OrderService orderService, CoursService coursService, EmailService emailService)
    {
        this.orderService = orderService;
        this.coursService = coursService;
        this.emailService = emailService;
    }
    private NumberOfItems numberOfItems = new NumberOfItems(1);

    @GetMapping("/")
    public String getStart()
    {
        return "redirect:person";
    }

    @GetMapping("/courses")
    public String getCatalog(Model model)
    {
        model.addAttribute("numberOfItems", numberOfItems);
        model.addAttribute("coursList", coursService.findAll());
        return "courses";
    }

    @GetMapping("/person")
    public String getAccount(Model model , @AuthenticationPrincipal CustomUserDetails customUserDetails)
    {
        Long id = customUserDetails.getUser().getId();
        model.addAttribute("numberOfItems", numberOfItems);
        model.addAttribute("user", userService.findUserById(id));
        model.addAttribute("orders", orderService.getOrdersByUserId(id));
        return "person";
    }

    @PostMapping("/korzinaPost")
    public String getNumberOfItems(@ModelAttribute NumberOfItems numberOfItems)
    {
        this.numberOfItems = numberOfItems;
        return "redirect:/korzina";
    }

    @GetMapping("/korzina")
    public String getKorzina(Model model)
    {
        Order order = new Order();
        List<OrderItem> orderItems = new ArrayList<>();
        order.setOrderItems(orderItems);
        for (int i = 0; i < numberOfItems.getNumber(); i++)
        {
            OrderItem orderItem = new OrderItem();
            orderItem.setCours(new Cours());
            order.addOrderItem(orderItem);
        }
        model.addAttribute("numberOfItems", numberOfItems);
        model.addAttribute("order", order);
        return "korzina";
    }

    @PostMapping("/add-order")
    public String addNewOrder(@ModelAttribute Order order, @ModelAttribute OrderItem orderItems,
                              @AuthenticationPrincipal CustomUserDetails customUserDetails)
    {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        order.setOrderDate(currentDate.format(formatter));
        order.setUser(customUserDetails.getUser());
        ArrayList<String> arrayList = new ArrayList<>();
        for (OrderItem orderItem : order.getOrderItems())
        {
            orderService.saveOrderItem(orderItem);
            arrayList.add(orderItem.getCours().getName());
        }
        orderService.saveOrder(order);
        emailService.sendEmail(order.getUser().getUsername(), "Vlang", "Здравствуйте, " + order.getUser().getSurName() + " " +order.getUser().getName() + "!" + "\n\nЭто школа иностранных языков Vlang! Вы записались к нам на курсы! \n\nВот ваш список курсов:\n" + String.join("\n", arrayList) + "\n\n Общая стоимость ваших курсов: " + order.getTotalCost() + "₽"+ "\n\nСпасибо, что выбрали нас!" + "\nУдачного Вам дня!"+ "\n\nC уважением, Vlang");
        return "redirect:person";
    }

}
