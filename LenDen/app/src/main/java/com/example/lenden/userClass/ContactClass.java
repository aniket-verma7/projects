package com.example.lenden.userClass;

public class ContactClass
{
    private String name,mob;

    public ContactClass()
    {

    }

    public ContactClass(String name, String mob)
    {
        this.name = name;
        this.mob = mob;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getMob()
    {
        return mob;
    }

    public void setMob(String mob)
    {
        this.mob = mob;
    }
}
