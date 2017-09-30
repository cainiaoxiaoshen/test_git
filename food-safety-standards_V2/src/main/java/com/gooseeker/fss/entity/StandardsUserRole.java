package com.gooseeker.fss.entity;

/**
 * 
 * 
 * @author Fuller
 * @version 3.0.0, 11/18/2008
 *
 */
public class StandardsUserRole extends StandardsUser
{
	private String role;
	
	private String roleLv;
	
	public StandardsUserRole() {
		super();
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

    public String getRoleLv()
    {
        return roleLv;
    }

    public void setRoleLv(String roleLv)
    {
        this.roleLv = roleLv;
    }
}