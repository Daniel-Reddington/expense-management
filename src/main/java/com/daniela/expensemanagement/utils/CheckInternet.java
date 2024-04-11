package com.daniela.expensemanagement.utils;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetAddress;

@Service
public class CheckInternet {
    public boolean isInternetAvailable() {
        try {
            InetAddress address = InetAddress.getByName("www.google.com");
            return address.isReachable(5000); // 5 second timeout
        } catch (IOException e) {
            return false;
        }
    }
}
