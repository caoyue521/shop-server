package com.github.caoyue521.shopserver.controller;

import com.github.caoyue521.shopserver.entity.Good;
import com.github.caoyue521.shopserver.model.ApiResult;
import com.github.caoyue521.shopserver.service.GoodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
@Api(tags = "商品相关api")
@RestController
@RequestMapping("/api/good")
@Slf4j
public class GoodController implements BaseController{
    @Resource
    GoodService goodService;
    @ApiOperation("获取商品列表")
    @GetMapping("/list")
    public ApiResult list(){
        return goodService.list();
    }
    @ApiOperation("根据id查找商品")
    @GetMapping("/id/{id}")
    public ApiResult getByPathId(@PathVariable("id") Long id){
        return  goodService.getByid(id);
    }
    @ApiOperation("根据类型选择商品")
    @GetMapping("/type/{type}")
    public ApiResult getListByPathType(@PathVariable("type") String type){
        return  goodService.getListByType(type);
    }
    @ApiOperation("根据类型查找商品")
    @GetMapping("/getByType")
    public ApiResult getListByType(@ApiParam(name="type",value = "商品类型",example ="pop") @RequestParam(required = true) String type,@RequestParam(required = false) Integer page){
        return goodService.getListByType(type,page);
    }
    @ApiOperation("添加一个商品")
    @PostMapping("/add")
    public ApiResult add(@RequestBody Good good){
        return  goodService.add(good);
    }
    @ApiOperation("删除一个商品")
    @PostMapping("/del")
    public ApiResult del(@ApiParam(name = "id",value = "商品id")@RequestParam Long id){
        return  goodService.del(id);
    }
    @ApiOperation("根据id查找商品")
    @GetMapping("/getByid")
    public ApiResult getById(@RequestParam("id") Long id){
        return  goodService.getByid(id);
    }



}
