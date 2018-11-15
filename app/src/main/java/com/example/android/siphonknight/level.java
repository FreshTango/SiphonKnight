package com.example.android.siphonknight;

public class level {
    private String name;
    public Boss b;

    public level(String name, Boss boss) {
        this.name = name;
        this.b = boss;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boss getBoss() {
        return b;
    }

    public void setSong(Boss boss) {
        this.b = boss;
    }
}
