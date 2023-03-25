package com.smile.source.code;

import com.smile.source.code.register.MapperAutoConfigureRegister;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(MapperAutoConfigureRegister.class)
@SpringBootApplication
public class SourceCodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SourceCodeApplication.class, args);
    }

}
