package com.sct.meiye;

import com.sct.meiye.util.RandomIdUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

@SpringBootTest
public class OrderIdTest {

    @Test
    public void getOrderId(){
        String uuid= UUID.randomUUID().toString().replaceAll("-", "");
        System.out.println(uuid);
        String number = String.format("%05d",1);
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        String dateStr = df.format(date );
        String seq="SF"+dateStr+number;
        System.out.println(seq);
        System.out.println(RandomIdUtil.generateUniqueKey());

    }

}
