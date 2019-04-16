package com.example.demoappmusic7;

public class RowLyric {
    private String stringCau;
    private boolean checkShow;

    public RowLyric(String stringCau, boolean checkShow) {
        this.stringCau = stringCau;
        this.checkShow = checkShow;
    }

    public String getStringCau() {
        return stringCau;
    }

    public void setStringCau(String stringCau) {
        this.stringCau = stringCau;
    }

    public boolean isCheckShow() {
        return checkShow;
    }

    public void setCheckShow(boolean checkShow) {
        this.checkShow = checkShow;
    }
}
