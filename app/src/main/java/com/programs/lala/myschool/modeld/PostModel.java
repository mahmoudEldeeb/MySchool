package com.programs.lala.myschool.modeld;

import java.io.Serializable;

/**
 * Created by melde on 6/13/2017.
 */

public class PostModel implements Serializable {
    String titile;
    String description;

    public String getTitile() {
        return titile;
    }

    public void setTitile(String titile) {
        this.titile = titile;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
