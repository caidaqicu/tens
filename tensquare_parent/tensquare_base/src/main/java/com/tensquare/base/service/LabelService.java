package com.tensquare.base.service;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class LabelService {

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 多条件查询
     * @param map
     * @return
     */
    public List<Label> findSearch(Map map){
        Specification spec = createSpecification(map);
        return labelDao.findAll(spec);
    }

    /**
     * 多条件加分页查询
     * @param map
     * @param page
     * @param size
     * @return
     */
    public Page<Label> findSearch(Map map, int page, int size){
        Specification spec = createSpecification(map);
        Pageable pageable = PageRequest.of(page-1, size);
        return labelDao.findAll(spec, pageable);
    }

    private Specification createSpecification(Map map) {
        Specification spec = new Specification() {
            @Override
            /**
             * cb:提供了追加条件的方法
             * cq:用于生成sql语句的
             * root:代表一个javaBean对象
             */
            public Predicate toPredicate(Root root, CriteriaQuery cq, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                if(map.get("labelname")!=null&&!"".equals(map.get("labelname"))){
                    Predicate p1 = cb.like(root.get("labelname").as(String.class), "%"+map.get("labelname")+"%");
                    list.add(p1);
                }
                if(map.get("state")!=null&&!"".equals(map.get("state"))){
                    Predicate p2 = cb.equal(root.get("state"), map.get("state"));
                    list.add(p2);
                }
                if(map.get("recommend")!=null&&!"".equals(map.get("recommend"))){
                    Predicate p3 = cb.equal(root.get("recommend"), map.get("recommend"));
                    list.add(p3);
                }
                return cb.and(list.toArray(new Predicate[list.size()]));
            }
        };
        return spec;
    }

    public List<Label> findAll(){
        return labelDao.findAll();
    }

    public Label findById(String id){
        return labelDao.findById(id).get();
    }

    public void add(Label label){
        label.setId(idWorker.nextId()+"");
        labelDao.save(label);
    }

    public void update(Label label){
        labelDao.save(label);
    }

    public void deleteById(String id){
        labelDao.deleteById(id);
    }

}
