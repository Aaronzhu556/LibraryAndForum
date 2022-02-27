package org.com.Service.Interface;

import org.springframework.stereotype.Service;

@Service
public interface ManagerService {
    public String managerLogin(String manager_name,String manager_password);
}
