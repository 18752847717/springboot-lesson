package com.spring.api.swagger.service;

import com.spring.api.swagger.exception.CustomException;
import com.spring.api.swagger.pojo.Message;

import java.util.List;

/**
 * @author WU, Homer
 * @version 1.0
 * @date 2020/3/26
 * @description
 * */


public interface MessageService {
    List<Message> findAll();
    Message findOne(Long id);
    Message save(Message message)throws CustomException;
    void delete(Long id);
    Message update(Message message)throws CustomException;
    Message updateText(Message message)throws CustomException;
}
