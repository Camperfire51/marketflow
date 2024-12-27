package com.camperfire.marketflow.service.cart;

import com.camperfire.marketflow.dto.crud.cart.CartRequest;
import com.camperfire.marketflow.dto.crud.inventory.InventoryRequest;
import com.camperfire.marketflow.dto.mapper.CartMapper;
import com.camperfire.marketflow.exception.NotEnoughProductQuantityException;
import com.camperfire.marketflow.exception.ProductNotFoundException;
import com.camperfire.marketflow.exception.ProductOutOfStocksException;
import com.camperfire.marketflow.model.Cart;
import com.camperfire.marketflow.model.Product;
import com.camperfire.marketflow.model.UserPrincipal;
import com.camperfire.marketflow.model.user.Customer;
import com.camperfire.marketflow.repository.CartRepository;
import com.camperfire.marketflow.service.inventory.InventoryService;
import com.camperfire.marketflow.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductService productService;
    private final InventoryService inventoryService;
    private final CartMapper cartMapper;

    @Override
    public void resetCart() {
        Cart cart = getAuthenticatedCart();

        cart.setProducts(new HashMap<>());

        cartRepository.save(cart);
    }

    @Override
    public Cart getAuthenticatedCart(){
        //TODO: This approach where services simply grab user object (in that case Customer customer) from user principle is not right.
        //TODO: Add product stock validation when getting user cart.

        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        Customer customer = (Customer) userPrincipal.getAuthUser().getUser();

        return customer.getCart();
    }

    @Override
    public Cart addProductToCart(Long productId, Long requestedQuantity) {
        Product product = productService.readProduct(productId);

        Long stockQuantity = product.getInventory().getStock();

        if (stockQuantity <= 0)
            throw new ProductOutOfStocksException("No stocks for product with id: " + productId);
        else if (stockQuantity < requestedQuantity)
            throw new NotEnoughProductQuantityException("Not enough quantity to satisfy requested quantity to add to cart.\n" +
                    " Remaining quantity: " + stockQuantity);

        Cart cart = getAuthenticatedCart();

        Map<Long, Long> products = cart.getProducts();

        Long currentQuantity = products.containsKey(product.getId()) ? products.get(productId) : 0L;
        Long newQuantity = currentQuantity + requestedQuantity;

        products.put(product.getId(), newQuantity);

        InventoryRequest inventoryRequest = InventoryRequest.builder()
                //TODO: set the new stocks quantity in here.
                //.stock(newQuantity)
                .build();
        //TODO: Update inventory by: inventoryService.updateInventory(inventoryRequest);

        inventoryService.updateInventory(inventoryRequest);

        return cartRepository.save(cart);
    }

    @Override
    public Cart removeProductFromCart(Long productId, Long removeQuantity) {
        Product product = productService.readProduct(productId);
        Cart cart = getAuthenticatedCart();

        Map<Long, Long> products = cart.getProducts();

        if (!products.containsKey(product.getId()))
            throw new ProductNotFoundException("Product with id (" + productId + ") was not found in the cart.");

        Long cartQuantity = products.get(product.getId());
        Long newQuantity = cartQuantity - removeQuantity > 0 ? cartQuantity : 0;

        products.put(product.getId(), newQuantity);
        return cartRepository.save(cart);
    }

    @Override
    public Cart createCart(CartRequest request) {
        Cart cart = cartMapper.toEntity(request);
        return cartRepository.save(cart);
    }

    @Override
    public Cart readCart(Long id) {
        return cartRepository.findById(id).orElse(null);
    }

    @Override
    public Cart updateCart(CartRequest request) {
        Cart cart = cartRepository.findById(request.getId()).orElseThrow();

        //TODO: Implement update logic.

        return cartRepository.save(cart);
    }

    @Override
    public void deleteCart(Long id) {
        Cart cart = cartRepository.findById(id).orElseThrow();

        cartRepository.delete(cart);
    }
}
