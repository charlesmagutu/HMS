package org.condabu.orderservice.controller;


import org.condabu.orderservice.entity.Cart;
import org.condabu.orderservice.entity.CartItem;
import org.condabu.orderservice.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @PostMapping("/{cartId}/add")
    public ResponseEntity<Cart> addItem(@PathVariable Long cartId, @RequestParam Long productId, @RequestParam int quantity, @RequestParam double price) {
        Cart updatedCart = cartService.addItemToCart(cartId, productId, quantity, price);
        return ResponseEntity.ok(updatedCart);
    }
    @DeleteMapping("/{cartId}/remove/{productId}")
    public ResponseEntity<Void> removeItem(@PathVariable Long cartId, @PathVariable Long productId) {
        cartService.removeItemFromCart(cartId, productId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<Cart> getCart(@PathVariable Long cartId) {
        Cart cart = cartService.getCart(cartId);
        return ResponseEntity.ok(cart);
    }
}

