package com.berk2s.omsapi;

import com.berk2s.omsapi.infra.OmsApiApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(classes = {OmsApiApplication.class})
class OmsApiApplicationTests {

    @Test
    void contextLoads() {
    }

}
