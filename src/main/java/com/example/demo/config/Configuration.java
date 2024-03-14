package com.example.demo.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;

//@org.springframework.context.annotation.Configuration
//public class Configuration {
//    @Bean
//    public ModelMapper modelMapperBean() {
//        return new ModelMapper();
//    }
//
//}
@org.springframework.context.annotation.Configuration
public class Configuration {
    @Bean
    public ModelMapper modelMapperBean() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); // ya da başka bir MatchingStrategy kullanabilirsiniz
        return modelMapper;
    }
}

