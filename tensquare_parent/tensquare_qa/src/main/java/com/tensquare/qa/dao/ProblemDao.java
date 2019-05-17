package com.tensquare.qa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.qa.pojo.Problem;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 *
 * @author Administrator
 */
public interface ProblemDao extends JpaRepository<Problem, String>, JpaSpecificationExecutor<Problem> {

    // SELECT * FROM tb_problem p WHERE p.id IN(SELECT pl.`problemid` FROM tb_pl pl WHERE pl.`labelid`=?)
    // ORDER BY replytime DESC ;

    /**
     * 根据标签ID查询最新问题列表
     *
     * @param labelid
     * @param pageable
     * @return
     */
    @Query("select p from Problem p where p.id in(select pl.problemid from PL pl where pl.labelid=?1 ) order by p.replytime desc")
    public Page<Problem> findNewListByLabelId(String labelid, Pageable pageable);


    /**
     * 根据标签ID查询热门问题列表
     *
     * @param labelId
     * @param pageable
     * @return
     */
    @Query("select p from Problem p where id in( select problemid from PL where labelid=?1 ) order by reply desc")
    public Page<Problem> findHotListByLabelId(String labelId, Pageable pageable);


    /**
     * 根据标签ID查询等待回答列表
     *
     * @param labelId
     * @param pageable
     * @return
     */
    @Query("select p from Problem p where id in( select problemid from PL where labelid=?1 ) and reply=0 order by createtime desc")
    public Page<Problem> findWaitListByLabelId(String labelId, Pageable pageable);

}
