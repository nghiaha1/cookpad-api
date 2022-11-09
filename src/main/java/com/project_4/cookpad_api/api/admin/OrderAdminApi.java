package com.project_4.cookpad_api.api.admin;

import com.project_4.cookpad_api.search.OrderSearchBody;
import com.project_4.cookpad_api.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/orders")
@CrossOrigin(origins = "*")
public class OrderAdminApi {
    @Autowired
    OrderService orderService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> findAll(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "limit", defaultValue = "10") int limit,
            @RequestParam(name = "orderId", required = false) String orderId,
            @RequestParam(name = "nameUser", required = false) String nameUser,
            @RequestParam(name = "phone", required = false) String phone,
            @RequestParam(name = "nameProduct", required = false) String nameProduct,
            @RequestParam(name = "email", required = false) String email,
            @RequestParam(name = "start", required = false) String start,
            @RequestParam(name = "end", required = false) String end,
            @RequestParam(name = "status", required = false) String status
    ) {
        OrderSearchBody searchBody = OrderSearchBody.OrderSearchBodyBuilder.anOrderSearchBody()
                .withPage(page)
                .withLimit(limit)
                .withOrderId(orderId)
                .withPhone(phone)
                .withNameUser(nameUser)
                .withNameProduct(nameProduct)
                .withEmail(email)
                .withStart(start)
                .withEnd(end)
                .withStatus(status)
                .build();

        return ResponseEntity.ok(orderService.findOrdersBy(searchBody));
    }

    @RequestMapping(method = RequestMethod.GET, path = "{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        return ResponseEntity.ok(orderService.findById(id));
    }
}
