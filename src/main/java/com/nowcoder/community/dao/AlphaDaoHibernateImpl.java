package com.nowcoder.community.dao;

import org.springframework.stereotype.Repository;

/**
 * @Author WH
 * @create 2021/11/2 20:15
 */
@Repository("alphaHibernate")
public class AlphaDaoHibernateImpl implements AlphaDao {
    @Override
    public String select() {
        return "Hibernate";
    }
}
