package com.example.skbazaar.service;

import com.example.skbazaar.model.entity.Address;
import com.example.skbazaar.model.entity.User;
import com.example.skbazaar.repository.AddressRepository;
import com.example.skbazaar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).orElseThrow();
    }

    public List<Address> getMyAddresses() {
        return addressRepository.findByUser(getCurrentUser());
    }

    public Address addAddress(Address address) {
        User user = getCurrentUser();
        address.setUser(user);
        return addressRepository.save(address);
    }
}
