package org.condabu.orderservice.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.condabu.orderservice.entity.Cart;
import org.condabu.orderservice.entity.CartItem;
import org.condabu.orderservice.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class CartService {
    @Autowired
    private CartRepository cartRepository;

//    public Cart getCartByUserId(Long userId) {
//        return cartRepository.findByUserId(userId);
//    }

//    public BigDecimal calculateTotal(Cart cart){
//        return cart.getCartItems().stream()
//                .map(item -> item.get)
//    }
@Transactional
    public Cart addItemToCart(Long cartId, Long productId, int quantity, double price) {
        Cart cart = cartRepository.findById(cartId).orElseGet(Cart::new);

        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item-> item.getProductId().equals(productId)).findFirst();

        if(existingItem.isPresent()){
            log.warn("Product with id  {} already exists in the cart updating", productId);
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity );
            item.setPrice(item.getPrice() +(quantity * price));
        }else {
            CartItem item = new CartItem();
            item.setProductId(productId);
            item.setQuantity(quantity);
            item.setPrice(price);
            item.setCart(cart);
            cart.getItems().add(item);
        }

        int totalQty = cart.getItems().stream()
                        .mapToInt(CartItem::getQuantity).sum();
        double totalPrice = cart.getItems().stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();


        cart.setTotalPrice(totalPrice);
        cart.setQuantity(totalQty);
        return cartRepository.save(cart);

    }


    public void removeItemFromCart(Long cartId, Long productId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        cart.getItems().removeIf(item -> item.getProductId().equals(productId));
        cart.setTotalPrice(cart.getTotalPrice());
        cartRepository.save(cart);

    }

    public Cart getCart(Long cartId) {
        return cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("cart not found"));
    }

    public void clearCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(()-> new RuntimeException("cart not found"));
        cart.getItems().clear();
        log.error("Clearing cart with id {}", cart);
        cart.setTotalPrice(0.0);
        cartRepository.save(cart);
    }

    public Cart updateCartStatus(Long cartId, boolean checkedOut){
        Cart cart = getCart(cartId);
        cart.setCheckedOut(checkedOut);
        return  cartRepository.save(cart);
    }
}
