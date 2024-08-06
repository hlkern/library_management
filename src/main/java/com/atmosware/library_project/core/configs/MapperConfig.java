package com.atmosware.library_project.core.configs;

import com.atmosware.library_project.core.utilities.mapping.BookMapper;
import com.atmosware.library_project.core.utilities.mapping.CategoryMapper;
import com.atmosware.library_project.core.utilities.mapping.TransactionMapper;
import com.atmosware.library_project.core.utilities.mapping.UserMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public BookMapper bookMapper() {
        return BookMapper.INSTANCE;
    }

    @Bean
    public UserMapper userMapper() {
        return UserMapper.INSTANCE;
    }

    @Bean
    public TransactionMapper transactionMapper() {
        return TransactionMapper.INSTANCE;
    }

    @Bean
    public CategoryMapper categoryMapper() {
        return CategoryMapper.INSTANCE;
    }
}
