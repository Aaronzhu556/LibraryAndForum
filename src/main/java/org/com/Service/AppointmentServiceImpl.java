package org.com.Service;

import org.com.Entity.Appointment;
import org.com.Redis.RedisServer;
import org.com.Service.Interface.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    @Autowired
    private RedisServer redisServer;
    @Override
    public List<Appointment> getAppointmentInfo(){
        List<Appointment> appointmentList = new LinkedList<>();
        int count = 1;
        Set<String> allKeys = redisServer.getAllKeys();
        Iterator<String> it1 = allKeys.iterator();
        List<String> keys = new LinkedList<>();
        while(it1.hasNext()){
            keys.add(it1.next());
        }

        for (int i = 0;i<keys.size();i++){
            String appointment_user_name = redisServer.getValue(keys.get(i));
            String appointment_expire_time = String.valueOf(redisServer.getExpireTime(keys.get(i)));
            appointmentList.add(new Appointment(count,keys.get(i),appointment_user_name,appointment_expire_time,"0"));
            count++;
        }

        return appointmentList;

    }
}
