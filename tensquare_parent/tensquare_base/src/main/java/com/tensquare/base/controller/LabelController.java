package com.tensquare.base.controller;

import com.tensquare.base.pojo.Label;
import com.tensquare.base.service.LabelService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/label")
@CrossOrigin //设置允许跨域访问11
public class LabelController {

    @Autowired
    private LabelService labelService;

    /**
     * 多条件查询
     * @param map
     * @return
     */
    @RequestMapping(value = "/search",method = RequestMethod.POST)
    public Result search(@RequestBody Map map){
        List<Label> list = labelService.findSearch(map);
        return new Result(true, StatusCode.OK, "查询成功", list);
    }

    /**
     * 多条件加分页查询
     * @param map
     * @return
     */
    @RequestMapping(value = "/search/{page}/{size}",method = RequestMethod.POST)
    public Result search(@RequestBody Map map,@PathVariable int page,@PathVariable int size){
        Page<Label> labelPage = labelService.findSearch(map,page,size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult(labelPage.getTotalElements(),labelPage.getContent()));
    }

    /**
     * 查询所有
     * @return
     */
    @RequestMapping(value = "",method = RequestMethod.GET)
    public Result findAll(){
        List<Label> list = labelService.findAll();
        return new Result(true, StatusCode.OK, "查询成功",list);
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable String id){
        int num= 1/0;
        Label label = labelService.findById(id);
        return new Result(true, StatusCode.OK, "查询成功", label);
    }

    /**
     * 添加
     * @param label
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Label label){
        labelService.add(label);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    /**
     * 修改
     * @param id
     * @param label
     * @return
     */
    @RequestMapping(value ="/{id}",method = RequestMethod.PUT)
    public Result update(@PathVariable String id,@RequestBody Label label){
        label.setId(id);
        labelService.update(label);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value ="/{id}",method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String id){
        labelService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }
}
