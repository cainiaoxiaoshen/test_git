package com.gooseeker.fss.entity;

import java.sql.Timestamp;
import java.util.List;

/**
 * 用户实体类
 * 
 * @author tianju
 *
 */
public class MyUser
{
    private Long id;
    
    // 用户名
    private String name;
    
    // 密码
    private String password;
    
    // 邮箱
    private String email;
    
    // 是否被锁定 0没有，1被锁定
    private int locked;
    
    // 是否可用1可用，0不可以
    private int enabled;
    
    // 过期时间
    private Timestamp expiration;
    
    // 创建时间
    private Timestamp createDate;
    
    private List<Authority> authorities;
    
    public Long getId()
    {
        return id;
    }
    
    public void setId(Long id)
    {
        this.id = id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getPassword()
    {
        return password;
    }
    
    public void setPassword(String password)
    {
        this.password = password;
    }
    
    public String getEmail()
    {
        return email;
    }
    
    public void setEmail(String email)
    {
        this.email = email;
    }
    

    public int getLocked()
    {
        return locked;
    }

    public void setLocked(int locked)
    {
        this.locked = locked;
    }

    public int getEnabled()
    {
        return enabled;
    }

    public void setEnabled(int enabled)
    {
        this.enabled = enabled;
    }

    public Timestamp getExpiration()
    {
        return expiration;
    }
    
    public void setExpiration(Timestamp expiration)
    {
        this.expiration = expiration;
    }
    
    public Timestamp getCreateDate()
    {
        return createDate;
    }
    
    public void setCreateDate(Timestamp createDate)
    {
        this.createDate = createDate;
    }
    
    
    public List<Authority> getAuthorities()
    {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities)
    {
        this.authorities = authorities;
    }

    @Override
    public String toString()
    {
        return "StandardsUser [id=" + id + ", name=" + name + ", password=" + password + ", email=" + email
            + ", locked=" + locked + ", enabled=" + enabled + ", expiration=" + expiration + ", createDate="
            + createDate + "]";
    }
    
}