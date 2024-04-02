package com.carboncell.assessment.Service.Auth;


import com.carboncell.assessment.Model.Role;

public interface AuthService {
    String login(String username, String password);

    String register(String name, String username, String password, Role role);
}