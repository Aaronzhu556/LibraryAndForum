package org.com;

import org.com.Entity.SeatInfo;
import org.com.Mapper.SeatInfoMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest
class graduateApplicationTests {

    @Autowired
    private SeatInfoMapper seatInfoMapper;
    @Test
    void contextLoads() {
    }


}
