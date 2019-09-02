package cn.zl.sm.upload.web.servlet;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.zl.sm.upload.domain.Music;
import cn.zl.sm.upload.service.MusicService;
import util.uuid.UUIDTool;

public class UploadMusicServlet extends HttpServlet {
	private MusicService service = new MusicService();
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		DiskFileItemFactory factory=new DiskFileItemFactory();
		ServletFileUpload sfu=new ServletFileUpload(factory);
		Map<String,String> errors=new HashMap<String,String>();
		
		try {
			List<FileItem> list=sfu.parseRequest(request);
			FileItem f1=list.get(0);
			FileItem f2=list.get(1);
			FileItem f3=list.get(2);
			FileItem f4=list.get(3);
			FileItem f5=list.get(4);
			FileItem f6=list.get(5);
			String musicname=f1.getString("utf-8");
			String producername=f2.getString("utf-8");
			String description=f3.getString("utf-8");
			String upemail=f4.getString("utf-8");
			String musictype=f5.getString("utf-8");
			long musiclength=f6.getSize();
			String fileName=f6.getName();
			int t=fileName.lastIndexOf(".");
			String mimetype=fileName.substring(t+1);
			//输入校验
			if(musicname==null||musicname.trim().isEmpty()) {
				errors.put("musicname", "音乐名不能为空");
			}else if(musicname.length()>50) {
				errors.put("musicname", "音乐名长度不能超过50");
			}
			
			if(producername==null||producername.trim().isEmpty()) {
				errors.put("producername", "作者名不能为空");
			}else if(producername.length()>50) {
				errors.put("producername", "作者名过长，不能超过50");
			}
			
			if(description==null||description.trim().isEmpty()) {
				errors.put("description", "简介不能为空");
			}else if(description.length()>50) {
				errors.put("description", "简介长度不能超过50");
			}
			
			if(musiclength==0) {
				errors.put("mimetype", "您没有选择上传的文件");
			}else if(!"mp3".equalsIgnoreCase(mimetype)) {
				errors.put("mimetype", "您上传的不是mp3格式的文件");
			}
			
			if(errors.size()>0&&errors!=null) {
				request.setAttribute("errors",errors);
				request.setAttribute("musicname",musicname);
				request.setAttribute("producername",producername);
				request.setAttribute("description",description);
				request.setAttribute("musictype",musictype);
				request.getRequestDispatcher("/upload.jsp").forward(request, response);
				return;
			}
			
			Music music = new Music();
			music.setMusicid(UUIDTool.getUUID());
			music.setMusicname(musicname);
			music.setProducername(producername);
			music.setDescription(description);
			music.setUpemail(upemail);
			music.setMusictype(musictype);
			music.setDownloadtime(0);//设置初始下载次数
			
			
			//music.setAbsolutePath
			//music.setMusicSize
			String pre=(double)musiclength/(1024*1024)+"";
			int end=pre.lastIndexOf(".");
			String musicsize=pre.substring(0,end+3)+"M";
			music.setMusicsize(musicsize);
			int index=fileName.lastIndexOf("\\");
			if(index!=-1) {
				fileName=fileName.substring(index+1);
			}
			String saveName=UUIDTool.getUUID()+"_"+fileName;
			String root=this.getServletContext().getRealPath("/WEB-INF/files");
			int hCode=fileName.hashCode();
			String hex=Integer.toHexString(hCode);
			File dirFile=new File(root,"/"+hex.charAt(0)+"/"+hex.charAt(1));
			dirFile.mkdirs();
			File destFile=new File(dirFile,saveName);
			String absolutePath=destFile.getAbsolutePath();
			music.setAbsolutePath(absolutePath);
			f6.write(destFile);
			
			service.uplaodMusic(music);
			request.setAttribute("msg","添加成功");
			request.getRequestDispatcher("/user/msg.jsp").forward(request, response);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}

}
