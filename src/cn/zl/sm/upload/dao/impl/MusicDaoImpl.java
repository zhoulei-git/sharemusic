package cn.zl.sm.upload.dao.impl;
import java.sql.SQLException;
import org.apache.commons.dbutils.QueryRunner;
import cn.zl.jdbcutil.TxQueryRunner;
import cn.zl.sm.upload.dao.MusicDao;
import cn.zl.sm.upload.domain.Music;

public class MusicDaoImpl implements MusicDao {

	private QueryRunner qr=new TxQueryRunner();
	
	@Override
	public void addMusic(Music music) {
		String sql="insert into music values(?,?,?,?,?,?,?,?,?)";
		Object[] params= {music.getMusicid(),music.getMusicname(),music.getProducername(),music.getDescription(),music.getUpemail(),music.getMusictype(),music.getAbsolutePath(),music.getDownloadtime(),music.getMusicsize()};
		try {
			qr.update(sql, params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
