package org.com.Controller;

import org.com.Entity.Appointment;
import org.com.Entity.QueryInfo;
import org.com.MyResponse.MyResponse;
import org.com.Service.Interface.AppointmentService;
import org.com.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.channels.MulticastChannel;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/appointment")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    @ResponseBody
    @RequestMapping("/queryallappointment")
    public MyResponse QueryAllAppointment(@RequestBody QueryInfo queryInfo, @RequestHeader("Authorization") String token) {
        if (JwtUtil.VerifyToken(token)) {
            List<Appointment> appointments = appointmentService.getAppointmentInfo();
            List<Appointment> appointmentList = new LinkedList<>();
            int page = 0;
            if (!appointments.isEmpty()) {
                int count = 0;
                if (!queryInfo.getQuerydata().equals(queryInfo.getQuerytext()) && queryInfo.getPagenum() != 1) { // 说明这是第一次查询
                    for (int i = 0; count < queryInfo.getPagesize(); i++) {
                        try {
                            appointmentList.add(appointments.get(i));
                            count++;
                        } catch (Exception e) {
                            break;
                        }
                    }
                    page = 1;
                } else {
                    for (int i = ((queryInfo.getPagenum() - 1) * queryInfo.getPagesize()); count < queryInfo.getPagesize(); i++) {
                        try {
                            System.out.println("caocao coaocoao");
                            appointmentList.add(appointments.get(i));
                            count++;
                        } catch (Exception e) {
                            break;
                        }
                    }
                }
                System.out.println(appointmentList.toString());
                return new MyResponse("200", "查询成功", String.valueOf(appointments.size()), appointmentList, String.valueOf(page));
            } else return new MyResponse("201", "没有这些信息", "", null, "");

        } else return new MyResponse("202", "jwt验证失败", "", null, "");

    }
}
