package de.eliyo.entity;

public class Wanted  {

	private String url;
	private String seek;
	private String email;
	private Boolean found;


    public Wanted(String url, String seek, String email) {
        this.url = url;
        this.seek = seek;
        this.email = email;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSeek() {
        return seek;
    }

    public void setSeek(String seek) {
        this.seek = seek;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getFound() {
        return found;
    }

    public void setFound(Boolean found) {
        this.found = found;
    }
    
}
