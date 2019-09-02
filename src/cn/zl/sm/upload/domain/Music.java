package cn.zl.sm.upload.domain;

public class Music {
	private String musicid;
	private String musicname;
	private String producername;
	private String description;
	private String upemail;
	private String musictype;
	private String absolutePath;
	private int downloadtime;
	private String musicsize;
	public String getMusicsize() {
		return musicsize;
	}
	public void setMusicsize(String musicsize) {
		this.musicsize = musicsize;
	}
	public String getMusicid() {
		return musicid;
	}
	public void setMusicid(String musicid) {
		this.musicid = musicid;
	}
	public String getMusicname() {
		return musicname;
	}
	public void setMusicname(String musicname) {
		this.musicname = musicname;
	}
	public String getProducername() {
		return producername;
	}
	public void setProducername(String producername) {
		this.producername = producername;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUpemail() {
		return upemail;
	}
	public void setUpemail(String upemail) {
		this.upemail = upemail;
	}
	public String getMusictype() {
		return musictype;
	}
	public void setMusictype(String musictype) {
		this.musictype = musictype;
	}
	public String getAbsolutePath() {
		return absolutePath;
	}
	public void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
	}
	public int getDownloadtime() {
		return downloadtime;
	}
	public void setDownloadtime(int downloadtime) {
		this.downloadtime = downloadtime;
	}
	
}
