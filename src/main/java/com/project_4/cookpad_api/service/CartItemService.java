package com.project_4.cookpad_api.service;

import com.project_4.cookpad_api.entity.CartItem;
import com.project_4.cookpad_api.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CartItemService {
    @Autowired
    CartItemRepository cartItemRepository;

}
