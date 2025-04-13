package com.wwc.project.controller;

import java.util.List;

import com.wwc.project.bean.ReponseBodyMapper;
import com.wwc.project.bean.po.JpaUserPo;
import com.wwc.project.bean.po.UserPo;
import com.wwc.project.bean.vo.PageVo;
import com.wwc.project.service.CbsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class CbsController {

    Logger logger = LoggerFactory.getLogger(CbsController.class);

    @Autowired
    private CbsService cbsServiceImp;

    @PostMapping("/queryUsers")
    public @ResponseBody ReponseBodyMapper queryUsers(){
        ReponseBodyMapper<Object> reponseBodyMapper =new ReponseBodyMapper<>();
        try {
            List<UserPo> userPoList = cbsServiceImp.queryUsers();
            reponseBodyMapper.setData(userPoList);
            logger.info(reponseBodyMapper.getData().toString());
        }catch (Exception e){
            reponseBodyMapper.setData(e.getMessage());
            logger.error(e.getMessage());
        }
        return reponseBodyMapper;
    }

    @PostMapping("/queryUserJpa")
    public @ResponseBody ReponseBodyMapper queryUserJpa(@RequestBody JpaUserPo jpaUserPo){
        ReponseBodyMapper<Object> reponseBodyMapper =new ReponseBodyMapper<>();
        try {
            jpaUserPo = cbsServiceImp.queryUserJpa(jpaUserPo);
            reponseBodyMapper.setData(jpaUserPo);
            logger.info(reponseBodyMapper.getData().toString());
        }catch (Exception e){
            reponseBodyMapper.setData(e.getMessage());
            logger.error(e.getMessage());
        }
        return reponseBodyMapper;
    }


    @PostMapping("/queryUserOrderByVerJpa")
    public @ResponseBody ReponseBodyMapper queryUserOrderByVerJpa(@RequestBody PageVo pageVo){
        ReponseBodyMapper<Object> reponseBodyMapper =new ReponseBodyMapper<>();
        try {
            List<JpaUserPo> jpaUserPoList = cbsServiceImp.queryUserOrderByVerJpa(pageVo);
            reponseBodyMapper.setData(jpaUserPoList);
            logger.info(reponseBodyMapper.getData().toString());
        }catch (Exception e){
            reponseBodyMapper.setData(e.getMessage());
            logger.error(e.getMessage());
        }
        return reponseBodyMapper;
    }

    @PostMapping("/queryUser")
    public @ResponseBody ReponseBodyMapper queryUser(@RequestBody UserPo userPo){
        ReponseBodyMapper<Object> reponseBodyMapper =new ReponseBodyMapper<>();
        try {
            userPo = cbsServiceImp.queryUser(userPo);
            reponseBodyMapper.setData(userPo);
            logger.info(reponseBodyMapper.getData().toString());
        }catch (Exception e){
            reponseBodyMapper.setData(e.getMessage());
            logger.error(e.getMessage());
        }
        return reponseBodyMapper;
    }

    @PostMapping("/updateUser")
    public @ResponseBody ReponseBodyMapper updateUser(@RequestBody UserPo userPo){
        ReponseBodyMapper reponseBodyMapper =new ReponseBodyMapper<>();
        try {
            userPo = cbsServiceImp.updateUser(userPo);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return reponseBodyMapper;
    }

    @PostMapping("/insertUser")
    public @ResponseBody ReponseBodyMapper insertUser(@RequestBody UserPo userPo){
        ReponseBodyMapper reponseBodyMapper =new ReponseBodyMapper<>();
        try {
            userPo = cbsServiceImp.insertUser(userPo);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return reponseBodyMapper;
    }

    @PostMapping("/deleteUser")
    public @ResponseBody ReponseBodyMapper deleteUser(@RequestBody UserPo userPo){
        ReponseBodyMapper reponseBodyMapper =new ReponseBodyMapper<>();
        try {
            userPo = cbsServiceImp.deleteUser(userPo);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return reponseBodyMapper;
    }
}
