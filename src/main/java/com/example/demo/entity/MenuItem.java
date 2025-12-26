package com.example.demo.entity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
public class MenuItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private String description;
    private BigDecimal sellingPrice;
    private Boolean active = false;

    @ManyToMany
    @JoinTable(name = "menu_item_category",
               joinColumns = @JoinColumn(name = "menu_item_id"),
               inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    public MenuItem() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public BigDecimal getSellingPrice() { return sellingPrice; }
    public void setSellingPrice(BigDecimal sellingPrice) { this.sellingPrice = sellingPrice; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
    public Set<Category> getCategories() { return categories; }
    public void setCategories(Set<Category> categories) { this.categories = categories; }
}