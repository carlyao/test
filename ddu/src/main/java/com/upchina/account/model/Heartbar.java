
package com.upchina.account.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * File: Heartbar.java 
 * Description: the HeartbarModel Created By CodeSmith.
 * Copyright: @ 2015
 */
public class Heartbar
{  
    //
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer heartbarid;

    public Integer getheartbarid(){ 
        return heartbarid;
    }

    public void setheartbarid(Integer heartbarid){ 
        this.heartbarid=heartbarid;
    }
    
    //扩展开始---------------自己的扩展统一写在这里-----------------start

    //扩展结束-----------------------------------------------------end
}

