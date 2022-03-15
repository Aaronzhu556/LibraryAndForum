package org.com.Service.Interface;

import org.com.Entity.Appointment;
import org.com.Redis.RedisServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AppointmentService {
    public List<Appointment> getAppointmentInfo();
}
