package org.crazyit.auction.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "state")
public class State {
    @Id
    @Column(name = "state_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 物品的状态名
    @Column(name = "state_name")
    private String stateName;

    // 该状态下的所有物品
    @OneToMany(targetEntity = Item.class,
        mappedBy = "itemState")
    private Set<Item> items = new HashSet<Item>();

    // 无参数的构造器
    public State() {}

    // 初始化全部基本属性的构造器
    public State(Integer id, String stateName) {
        this.id = id;
        this.stateName = stateName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }
}
