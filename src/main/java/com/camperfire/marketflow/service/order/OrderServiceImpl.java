package com.camperfire.marketflow.service.order;

import com.camperfire.marketflow.dto.crud.invoice.InvoiceRequest;
import com.camperfire.marketflow.dto.crud.notification.NotificationRequest;
import com.camperfire.marketflow.dto.crud.order.OrderRequest;
import com.camperfire.marketflow.dto.crud.payment.PaymentRequest;
import com.camperfire.marketflow.dto.mapper.OrderMapper;
import com.camperfire.marketflow.exception.NotEnoughProductQuantityException;
import com.camperfire.marketflow.model.*;
import com.camperfire.marketflow.model.user.Customer;
import com.camperfire.marketflow.model.user.User;
import com.camperfire.marketflow.repository.OrderRepository;
import com.camperfire.marketflow.service.cart.CartService;
import com.camperfire.marketflow.service.inventory.InventoryService;
import com.camperfire.marketflow.service.invoice.InvoiceService;
import com.camperfire.marketflow.service.notification.NotificationService;
import com.camperfire.marketflow.service.product.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final InvoiceService invoiceService;
    private final ProductService productService;
    private final InventoryService inventoryService;
    private final NotificationService notificationService;
    private final CartService cartService;

    @Transactional
    @Override
    public Order submitOrder() {
        //TODO: This approach where services simply grab user object (in that case Customer customer) from user principle is not right.
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Customer customer = (Customer) userPrincipal.getAuthUser().getUser();

        Cart cart = customer.getCart();

        for (Map.Entry<Long, Long> productEntry : cart.getProducts().entrySet()) {
            Long productId = productEntry.getKey();
            Long requestedQuantity = productEntry.getValue();

            Product product = productService.readProduct(productId);
            Long stockQuantity = product.getInventory().getStock();

            if (stockQuantity < requestedQuantity)
                throw new NotEnoughProductQuantityException("Not enough stock for productId: " + product.getId() + "\n Only have: " + stockQuantity);
        }

        PaymentRequest paymentRequest = PaymentRequest.builder()
                // TODO: Implement payment request.
                .build();

//        if (paymentService.processPayment(paymentRequest).getStatus() != PaymentStatus.COMPLETED)
//            throw new PaymentException("Payment failed");

        OrderRequest orderRequest = OrderRequest.builder()
                .customerId(customer.getId())
                .products(cart.getProducts())
                .shippingAddress(customer.getAddress())
                .build();

        Order order = createOrder(orderRequest); //TODO: Simulate successful order submission.

        for (Map.Entry<Long, Long> productEntry : cart.getProducts().entrySet()) {
            Long productId = productEntry.getKey();
            Long requestedQuantity = productEntry.getValue();

            Product product = productService.readProduct(productId);
            Long stockQuantity = product.getInventory().getStock();

            Long newQuantity = stockQuantity - requestedQuantity;

            Inventory inventory = product.getInventory();

            inventoryService.setStock(inventory, newQuantity);

            User vendor = product.getVendor();

            if (newQuantity < inventory.getRestockAlarmQuantity()){
                NotificationRequest notificationRequest = NotificationRequest.builder()
                        .message("Quantity of your product " + product.getName() + "is below the restock level (" + inventory.getRestockAlarmQuantity() + ")")
                        .type(NotificationType.PRODUCT_RESTOCK_ALARM)
                        .userId(vendor.getId())
                        .build();

                notificationService.createNotification(notificationRequest);
            }
        }

        cartService.resetCart();

        return order;
    }

    @Override
    public Order createOrder(OrderRequest request) {
        Order order = orderMapper.toEntity(request);

        InvoiceRequest invoiceRequest = InvoiceRequest.builder().build();

        invoiceService.createInvoice(invoiceRequest);

        return orderRepository.save(order);
    }

    @Override
    public Order readOrder(Long id) {
        return orderRepository.findById(id).orElseThrow();
    }

    @Override
    public Order updateOrder(OrderRequest request) {
        Order order = orderRepository.findById(request.getId()).orElseThrow();

        //TODO: Implement update logic.

        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow();

        orderRepository.delete(order);
    }
}
