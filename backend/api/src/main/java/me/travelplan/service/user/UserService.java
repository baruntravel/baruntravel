package me.travelplan.service.user;

import lombok.RequiredArgsConstructor;
import me.travelplan.service.cart.Cart;
import me.travelplan.service.cart.CartRepository;
import me.travelplan.service.exception.ResponsibleClientException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    public User create(User user) {
        if (userRepository.findUserByEmail(user.getEmail()).isPresent()) {
            throw new ResponsibleClientException("중복된 이메일입니다");
        }
        User savedUser = userRepository.save(user);
        cartRepository.save(Cart.createEmpty(user));

        return savedUser;
    }
}