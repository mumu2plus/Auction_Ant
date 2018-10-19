package org.crazyit.auction.domain;

import java.util.*;

import javax.persistence.*;

@Entity
@Table(name = "kind")
public class Kind {
    // 标识属性
    @Id
    @Column(name = "kind_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 种类名
    @Column(name = "kind_name")
    private String kindName;

    // 种类描述
    @Column(name = "kind_desc")
    private String kindDesc;

    // 该种类下的所有物品
    @OneToMany(targetEntity = Item.class,
        mappedBy = "kind")
    private Set<Item> items = new HashSet<Item>();

    public Kind() {}

    public Kind(Integer id, String kindName, String kindDesc) {
        this.id = id;
        this.kindName = kindName;
        this.kindDesc = kindDesc;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKindName() {
        return kindName;
    }

    public void setKindName(String kindName) {
        this.kindName = kindName;
    }

    public String getKindDesc() {
        return kindDesc;
    }

    public void setKindDesc(String kindDesc) {
        this.kindDesc = kindDesc;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }
}
