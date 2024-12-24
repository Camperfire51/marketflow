package com.camperfire.marketflow.service.cart;

import com.camperfire.marketflow.dto.crud.cart.CartRequest;
import com.camperfire.marketflow.model.Cart;

public interface CartService {

    Cart createCart(CartRequest request);

    Cart readCart(CartReadRequest request);

    Cart updateCart(CartUpdateRequest request);

    void deleteCart(CartDeleteRequest request);

}
