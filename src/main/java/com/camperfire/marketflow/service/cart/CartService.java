package com.camperfire.marketflow.service.cart;

import com.camperfire.marketflow.dto.crud.cart.CartRequest;
import com.camperfire.marketflow.model.Cart;

public interface CartService {

    Cart getAuthenticatedCart();

    Cart addProductToCart(Long productId, Long quantity);

    Cart createCart(CartRequest request);

    Cart readCart(Long id);

    Cart updateCart(CartRequest request);

    void deleteCart(Long id);

}
