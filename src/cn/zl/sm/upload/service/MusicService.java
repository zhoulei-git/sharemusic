package cn.zl.sm.upload.service;

import cn.zl.sm.upload.dao.MusicDao;
import cn.zl.sm.upload.dao.MusicDaoFactory;
import cn.zl.sm.upload.domain.Music;

public class MusicService {
	private MusicDao dao=MusicDaoFactory.getMusicDao();

	public void uplaodMusic(Music music) {
		dao.addMusic(music);
	}
	
}
